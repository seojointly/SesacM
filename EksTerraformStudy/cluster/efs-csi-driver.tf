# ~/eks-terraform/cluster/efs-csi-driver.tf


# 1. EFS CSI Driver 전용 IRSA 설정
module "efs_csi_irsa_role" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.0"

  role_name = "efs-csi-controller-sa-5"

  # EKS 모듈에서 제공하는 EFS 전용 정책을 자동으로 연결
  attach_efs_csi_policy = true

  # OIDC 설정
  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["kube-system:efs-csi-controller-sa"]
    }
  }
}


# 2. EKS Add-on으로 EFS 드라이버 설치
# Add-on: AWS에서 관리하는 소프트웨어 패키지 형태
resource "aws_eks_addon" "efs_csi" {
  cluster_name             = module.eks.cluster_name
  addon_name               = "aws-efs-csi-driver"
  service_account_role_arn = module.efs_csi_irsa_role.iam_role_arn
  
  # 이전 버전과의 충돌 방지
  resolve_conflicts_on_create = "OVERWRITE"
  resolve_conflicts_on_update = "OVERWRITE"
}