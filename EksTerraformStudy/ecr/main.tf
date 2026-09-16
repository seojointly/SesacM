# ~/eks-terraform/ecr/main.tf

locals {
  repository_names = ["msa-gateway-5", "msa-auth-5", "msa-order-5", "nginx-test-5"]
}

resource "aws_ecr_repository" "msa_repos" {
  for_each = toset(local.repository_names)
  
  name                 = each.value
  image_tag_mutability = "MUTABLE"
  
  image_scanning_configuration {
    scan_on_push = true
  }
  
  tags = {
    Environment = "dev"
    Project     = "eks-project"
  }
}

resource "aws_ecr_lifecycle_policy" "cleanup_policy" {
  for_each   = aws_ecr_repository.msa_repos
  repository = each.value.name

  policy = jsonencode({
    rules = [{
      rulePriority = 1
      description  = "Keep last 10 images"
      selection = {
        tagStatus     = "any"
        countType     = "imageCountMoreThan"
        countNumber   = 10
      }
      action = {
        type = "expire"
      }
    }]
  })
}