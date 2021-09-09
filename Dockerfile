FROM maven:3.6.3-jdk-8

RUN apt update -y && apt install -y librxtx-java

WORKDIR /springbootblueprints

COPY pom.xml /springbootblueprints
COPY mvnw /springbootblueprints
COPY mvnw.cmd /springbootblueprints

RUN mvn dependency:go-offline

COPY src /springbootblueprints/src
RUN mvn package -DskipTests

RUN mkdir -p /springbootblueprints/target

EXPOSE 8000

ENTRYPOINT ["java", "-Djava.library.path=/usr/lib/jni", "-jar","target/springbootblueprints-0.0.1-SNAPSHOT.jar"]