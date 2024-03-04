
resource "aws_s3_bucket" "backend-bucket" {
  bucket = "bean-bank-coin-backend-bucket"

  tags = var.mandatory_tags
}

resource "aws_vpc" "bean_bank_coin_vpc" {
  cidr_block = "15.0.0.0/16"
  enable_dns_hostnames= true
  tags = var.mandatory_tags
}

resource "aws_subnet" "subnet_a" {
  vpc_id     = aws_vpc.bean_bank_coin_vpc.id
  cidr_block = "15.0.1.0/24"
  tags = var.mandatory_tags
  availability_zone = "eu-west-1a"
}

resource "aws_subnet" "subnet_b" {
  vpc_id     = aws_vpc.bean_bank_coin_vpc.id
  cidr_block = "15.0.2.0/24"
  tags = var.mandatory_tags
  availability_zone = "eu-west-1b"
}

resource "aws_db_subnet_group" "bean_bank_subnet_group" {
  name       = "bean_bank_coin_subnet_group"
  subnet_ids = [
    aws_subnet.subnet_a.id,
    aws_subnet.subnet_b.id,
  ]
  tags = var.mandatory_tags
}

# Internet Gateway
resource "aws_internet_gateway" "bean_bank_coin_gateway" {
  vpc_id = aws_vpc.bean_bank_coin_vpc.id
 
  tags = var.mandatory_tags
}

# Routing table
resource "aws_route_table" "bean_bank_coin_route_table" {
  vpc_id = aws_vpc.bean_bank_coin_vpc.id
 
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.bean_bank_coin_gateway.id
  }
 
  tags = var.mandatory_tags
}

# Resource association table
resource "aws_route_table_association" "association_a" {
  subnet_id      = aws_subnet.subnet_a.id
  route_table_id = aws_route_table.bean_bank_coin_route_table.id
}

resource "aws_route_table_association" "association_b" {
  subnet_id      = aws_subnet.subnet_b.id
  route_table_id = aws_route_table.bean_bank_coin_route_table.id
}

# Create an AWS DB instance resource that requires secrets
resource "aws_db_instance" "bean-bank-coin-db" {
  allocated_storage    = 20
  engine               = "sqlserver-ex"
  engine_version       = "16.00.4095.4.v1"  # 2022 version
  instance_class       = "db.t3.micro"
  identifier           = "bean-bank-coin-db-identifier"
  multi_az             = false # Free tier supports only single AZ
  publicly_accessible  = true  # Enable public access to the database
  username             = "admin"
  manage_master_user_password = true #Fetch password from console
  apply_immediately = true
  copy_tags_to_snapshot = true
  db_subnet_group_name =  aws_db_subnet_group.bean_bank_subnet_group.name
  skip_final_snapshot = true

  vpc_security_group_ids = [
    aws_security_group.mssql_security_group.id
  ]

  tags = var.mandatory_tags
}

# Define EC2 instance resource
resource "aws_instance" "spring_boot_instance" {
  ami           = "ami-0ef9e689241f0bb6e" 
  instance_type = "t2.micro"  
  subnet_id     = aws_subnet.subnet_a.id  

  # Define the user data to install Java and run the Spring Boot application
  # user_data = <<-EOF
  #             #!/bin/bash
  #             yum update -y
  #             yum install -y java-1.8.0-openjdk
  #             # Additional setup and application deployment commands here
  #             EOF

  # Associate the instance with the EC2 security group
  vpc_security_group_ids = [aws_security_group.ec2_security_group.id]

  # Associate the instance with an existing key pair for SSH access
  key_name = "spring_boot_ec2_key_pair"

  tags = var.mandatory_tags
}



