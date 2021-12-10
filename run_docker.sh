#mvn com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=springbootblueprints

docker build . -t springbootblueprints

docker rm -f springbootblueprints

docker run -d --restart unless-stopped \
  --name springbootblueprints -p 8000:8000 \
  -v /docker_data/springbootblueprints:/springbootblueprints \
  springbootblueprints