FROM maven:3.6.3-jdk-11 as build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM openjdk:11 as production
ARG DEPENDENCY=/app/target/dependency

RUN apt update && apt install -y vim htop sysstat
RUN echo "alias ll='ls -alhF'" >> ~/.bashrc

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 11000

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.selab.springbootblueprints.SpringBootBluePrintsWebApplication"]