# ~/eks-terraform/cluster/data.tf

# VPC 폴더의 상태 파일을 읽어오는 데이터 소스 (정보를 읽어오는 것 (vpc에 관련된 정보)
data "terraform_remote_state" "vpc" {
  backend = "s3"

  config = {
    bucket = "sesac-d5-terraform-state-bucket"  # 버킷명
    key    = "dev/vpc/terraform.tfstate"           # VPC의 key
    region = "ap-northeast-2"
  }
}