# ==========================================
# 1단계: 빌드 스테이지 (Docker CLI 및 관련 파일 다운로드/설치)
# ==========================================
FROM ubuntu:22.04 AS builder

# 1. GPG 키 받기
RUN apt update && \
    apt install -y ca-certificates curl && \
    install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc && \
    chmod a+r /etc/apt/keyrings/docker.asc

# 2. 저장소 파일 복사 (같은 폴더에 docker.sources 파일 필수)
COPY docker.sources /etc/apt/sources.list.d/docker.sources

# 3. 요구된 Docker 패키지 전체 설치
RUN apt update && \
    apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# ==========================================
# 2단계: 실행 스테이지 (멀티 스테이지 빌드)
# ==========================================
FROM ubuntu:22.04

# 설치된 핵심 실행 파일 및 설정 복사
COPY --from=builder /usr/bin/docker /usr/bin/docker
COPY --from=builder /usr/libexec/docker/cli-plugins /usr/libexec/docker/cli-plugins
COPY --from=builder /etc/apt/keyrings/docker.asc /etc/apt/keyrings/docker.asc
COPY --from=builder /etc/apt/sources.list.d/docker.sources /etc/apt/sources.list.d/docker.sources

CMD ["bash"]