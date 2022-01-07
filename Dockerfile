FROM maven:3.6.3-jdk-11 as build
WORKDIR /app

COPY pom.xml .
COPY ./web/pom.xml ./web/pom.xml
COPY ./api/pom.xml ./api/pom.xml
COPY ./lib/pom.xml ./lib/pom.xml
COPY ./lib/commonUtil/pom.xml ./lib/commonUtil/pom.xml
COPY mvnw .
COPY .mvn .mvn

RUN mvn dependency:go-offline

COPY ./web/src ./web/src
COPY ./api/src ./api/src
COPY ./lib/commonUtil/src ./lib/commonUtil/src

RUN mvn package -DskipTests
RUN mkdir -p ./web/target/dependency && (cd ./web/target/dependency; jar -xf ../*.jar)
RUN ($aa ls; echo $aa) && (cd web/target/dependency; $aa ls; echo $aa)


FROM openjdk:11 as production
ARG DEPENDENCY=/app/web/target/dependency

RUN apt update && apt install -y vim htop sysstat
RUN echo "alias ll='ls -alhF'" >> ~/.bashrc

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 11000

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.selab.springbootblueprints.SpringBootBluePrintsWebApplication"]