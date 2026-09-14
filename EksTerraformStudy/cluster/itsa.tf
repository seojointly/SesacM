# ~/eks-terraform/cluster/irsa.tf

# 1. IAM Role 생성 (권한을 만든다!)
# EKS 전용 모듈: OIDC와의 복잡한 신뢰 관계(Trust Relationship) 설정을 자동으로 처리
module "s3_read_irsa_role" {

  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"  # 모듈 이름
  version = "~> 5.0"                                                                    # 모듈 버전
  
  role_name = "sesac-d5-s3-read-only-role"                                              # Role 이름
  
  
  # IAM Role을 사용할 EKS 클러스터 OIDC 정보
  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn  # EKS Module 생성 시 함께 만들어 짐
      namespace_service_accounts = ["default:s3-read-sa"]        # [네임스페이스:SA이름]
    }
  }
  
  # 부여할 권한 (S3 읽기 권한)
  role_policy_arns = {
    s3_read = "arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"
  }

  tags = {
    Environment = "dev"
  }
}


# 2. Kubernetes ServiceAccount 생성 (SA가 만든 권한을 가진다!)
resource "kubernetes_service_account_v1" "s3_read_sa" {
  metadata {
    name        = "sesac-d5-s3-read-sa"  # ServiceAccount(SA) 이름
    namespace   = "default"     # 네임스페이스 이름
    annotations = {
      # IAM Role의 ARN을 Annotation 달아주기
      "eks.amazonaws.com/role-arn" = module.s3_read_irsa_role.iam_role_arn
    }
  }
}