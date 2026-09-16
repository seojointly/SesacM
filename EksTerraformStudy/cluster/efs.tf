# ~/eks-terraform/cluster/efs.tf

resource "aws_security_group" "efs" {
  name        = "${module.eks.cluster_name}-efs-sg"
  description = "Security group for EFS access from EKS nodes"
  vpc_id      = data.terraform_remote_state.vpc.outputs.vpc_id

  ingress {
    from_port   = 2049
    to_port     = 2049
    protocol    = "tcp"
    cidr_blocks = [data.terraform_remote_state.vpc.outputs.vpc_cidr]
  }
  
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "${module.eks.cluster_name}-efs-sq"
  }
}

resource "aws_efs_file_system" "main" {
  creation_token = "${module.eks.cluster_name}-efs"
  encrypted      = true

  tags = { 
    Name = "eks-shared-storage"
  }
}

resource "aws_efs_mount_target" "main" {
  count           = length(data.terraform_remote_state.vpc.outputs.private_subnets)
  file_system_id  = aws_efs_file_system.main.id
  subnet_id       = data.terraform_remote_state.vpc.outputs.private_subnets[count.index]
  security_groups = [aws_security_group.efs.id]
}

output "efs_file_system_id" {
  description = "생성된 EFS의 ID"
  value       = aws_efs_file_system.main.id
}