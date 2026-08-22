#!/bin/bash

# 스크립트 실행 중 오류 발생 시 즉시 중지
set -o


# Add Docker's official GPG key: GPG 키 등록
apt update
apt install -y ca-certificates curl
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources: 저장소 추가
tee /etc/apt/sources.list.d/docker.sources <<EOF
Types: deb
URIs: https://download.docker.com/linux/ubuntu
Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
Components: stable
Architectures: $(dpkg --print-architecture)
Signed-By: /etc/apt/keyrings/docker.asc
EOF

# 저장소 추가되었으므로 패키지 목록 업데이트 및 도커 엔진 설치
apt update
apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# apt 캐시 삭제 (용량 절감)
rm -rf /var/lib/apt/lists/*