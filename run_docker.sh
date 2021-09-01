#mvn com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=springbootblueprints

docker build . -t springbootblueprints

docker rm -f springbootblueprints

#docker volume create --name maven-repo

# without hardware
docker run -d --restart unless-stopped \
  --name springbootblueprints -p 8000:8000 \
  -v maven-repo:/root/.m2 \
  -v /docker_data/living_lab/living_lab \
  springbootblueprints