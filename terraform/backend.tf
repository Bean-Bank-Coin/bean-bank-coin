# State bucket for storing and sharing terraform state
terraform {
    backend "s3" {
        bucket = "bean-bank-coin-backend-bucket"
        key   = "bean-bank-coin-backend-bucket/terraform.tfstate"
        region = "eu-west-1"
        encrypt = true
    }
}