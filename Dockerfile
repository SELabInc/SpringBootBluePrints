FROM maven:3.6.3-jdk-11 as build
WORKDIR /app

COPY pom.xml .
COPY ./p6spy/pom.xml ./p6spy/pom.xml
COPY ./lib/pom.xml ./lib/pom.xml
COPY ./lib/commonUtil/pom.xml ./lib/commonUtil/pom.xml
COPY ./example/pom.xml ./example/pom.xml
COPY mvnw .
COPY .mvn .mvn

RUN mvn dependency:go-offline

COPY ./p6spy/src ./p6spy/src
COPY ./lib/commonUtil/src ./lib/commonUtil/src
COPY ./example/src ./example/src

RUN mvn package -DskipTests
RUN mkdir -p ./example/target/dependency && (cd ./example/target/dependency; jar -xf ../*.jar)

FROM openjdk:11 as production
ARG DEPENDENCY=/app/example/target/dependency

RUN apt update && apt install -y vim htop sysstat
RUN echo "alias ll='ls -alhF'" >> ~/.bashrc

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 11000

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.selab.webexample.WebApplication"]