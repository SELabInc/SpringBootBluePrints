docker build . -t springbootblueprints

docker rm -f springbootblueprints

docker run -d --restart unless-stopped \
  -e SPRING_PROFILES_ACTIVE=staging \
  -e TZ=Asia/Seoul \
  --name springbootblueprints -p 8000:8000 \
  -v /docker_data/springbootblueprints:/springbootblueprints \
  springbootblueprints