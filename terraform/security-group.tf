# Create a security group so that ms sql studio can connect to the database
resource "aws_security_group" "mssql_security_group" {
  name        = "mssql_security_group"
  description = "Security group for MSSQL"
  vpc_id= aws_vpc.bean_bank_coin_vpc.id

  #Enable internet access to the database
  # Allow inbound traffic from IPv4
  ingress {
      from_port   = 1433
      to_port     = 1433
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"] # Allow inbound traffic from any IPv4 address
  }

  # Allow inbound traffic from IPv6
  ingress {
      from_port   = 1433
      to_port     = 1433
      protocol    = "tcp"
      ipv6_cidr_blocks = ["::/0"] # Allow inbound traffic from any IPv6 address
  }

  tags = var.mandatory_tags
}