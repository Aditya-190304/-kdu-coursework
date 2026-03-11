terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    bucket = "aditya-nigam-terraform-state"         
    key            = "terraform.tfstate"
    region         = "ap-southeast-1"
    dynamodb_table = "terraform-locks-aditya-nigam"  # CHANGED: Use your real table
    encrypt        = true
  }
}

provider "aws" {
  region = "ap-southeast-1"
}