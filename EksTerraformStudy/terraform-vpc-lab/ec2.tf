# 1. Ubuntu 24.04 LTS AMI 정보 조회 (제조사: Canonical (Ubuntu AMI 소유자))

data "aws_ami" "ubuntu_24_04" {
  owners      = ["099720109477"] # Canonical 공식 AWS 계정 ID
  most_recent = true

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*"]
  }
}

# === 1. Bastion Host (첫 번째 Public Subnet에 배치) ===
resource "aws_instance" "bastion" {
  # 1. AMI 설정 (# ami id 가 필요해서 생성하는 것)
  ami = data.aws_ami.ubuntu_24_04.id
  # 2. Instance type
  instance_type = "t3.micro"
  # 3. key pair
  key_name = aws_key_pair.key_pair.key_name
  # 4. 네트워크 설정 (subnet 등록 -> vpc 설정해놓음)
  subnet_id = aws_subnet.public[0].id
  # 5. 퍼블릭 IP 자동 할당
  associate_public_ip_address = true
  # 6. 보안그룹
  vpc_security_group_ids = [aws_security_group.bastion.id]

  # 7. 저장 용량 설정
  root_block_device {
    volume_size           = 8
    volume_type           = "gp3"
    delete_on_termination = true
    encrypted             = true

    tags = {
      Name = "${var.project_name}-bastion-ebs-5"
    }
  }
  tags = {
    Name = "${var.project_name}-bastion-host-5"
  }
}


# 2. === Private EC2 (첫 번째 Private Subnet에 배치) ===
resource "aws_instance" "private" {
  # 1. AMI 설정 (# ami id 가 필요해서 생성하는 것)
  ami = data.aws_ami.ubuntu_24_04.id
  # 2. Instance type
  instance_type = "t3.micro"
  # 3. key pair
  key_name = aws_key_pair.key_pair.key_name
  # 4. 네트워크 설정 (subnet 등록 -> vpc 설정해놓음)
  subnet_id = aws_subnet.private[0].id
  # 5. 퍼블릭 IP 자동 할당
  # associate_public_ip_address = false # default 값: false
  # 6. 보안그룹
  vpc_security_group_ids = [aws_security_group.private_ec2.id]

  # 7. 저장 용량 설정
  root_block_device {
    volume_size           = 8
    volume_type           = "gp3"
    delete_on_termination = true
    encrypted             = true

    tags = {
      Name = "${var.project_name}-private-ec2-ebs-5"
    }
  }

  tags = {
    Name = "${var.project_name}-private-ec2-5"
  }
}