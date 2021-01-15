FROM openjdk:8-jdk
RUN apt-get update && apt-get install -y maven
RUN cd /opt && git clone https://github.com/clarabridges/playdb.git
WORKDIR /opt/playdb
RUN mvn package
RUN chmod +x playdb.sh
