# ~/EksTerraformStudy/cluster/ebs-csi-driver.tf


# 1. EBS CSI Driver를 위한 IRSA 설정
module "ebs_csi_irsa_role" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.0"

  role_name = "sesac-d5-ebs-csi-controller-sa"

  # EBS를 생성/삭제할 수 있는 권한
  attach_ebs_csi_policy = true

  # OIDC 설정
  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["kube-system:ebs-csi-controller-sa"]
    }
  }
}


# 2. EKS Add-on으로 EBS 드라이버 설치
# Add-on: AWS에서 관리하는 소프트웨어 패키지 형태
resource "aws_eks_addon" "ebs_csi" {
  cluster_name             = module.eks.cluster_name
  addon_name               = "aws-ebs-csi-driver"
  service_account_role_arn = module.ebs_csi_irsa_role.iam_role_arn
  
  # 이전 버전과의 충돌 방지
  resolve_conflicts_on_create = "OVERWRITE"
  resolve_conflicts_on_update = "OVERWRITE"
}