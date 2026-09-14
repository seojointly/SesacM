# ~/eks-terraform/cluster/provider.tf

terraform {
  required_version = ">= 1.6.0"

  backend "s3" {
    bucket         = "sesac-d5-terraform-state-bucket"  # 버킷명
    key            = "dev/cluster/terraform.tfstate"       # 키 (VPC 키와 다르게 설정)
    region         = "ap-northeast-2"
    encrypt        = true
    dynamodb_table = "terraform-lock-table"
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

# 2. IRSA 

provider "kubernetes" {
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
  exec {
    api_version = "client.authentication.k8s.io/v1beta1"
    command     = "aws"
    args        = ["eks", "get-token", "--cluster-name", module.eks.cluster_name]
  }
}