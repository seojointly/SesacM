FROM ubuntu:22.04

WORKDIR /scripts
COPY install_docker_engine.sh .

RUN chmod +x install_docker_engine.sh && ./install_docker_engine.sh

CMD ["/bin/bash"]