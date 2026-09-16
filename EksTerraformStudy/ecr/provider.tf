# ~/eks-terraform/ecr/provider.tf

terraform {
  required_version = ">= 1.6.0"

  backend "s3" {
    bucket         = "sesac-d5-terraform-state-bucket"
    key            = "dev/ecr/terraform.tfstate"            # ECR 전용 상태 경로
    region         = "ap-northeast-2"
    encrypt        = true
    dynamodb_table = "sesac-d5-terraform-lock-table"
  }

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "ap-northeast-2"
}