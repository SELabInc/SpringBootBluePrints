docker build . -t springbootblueprints

docker rm -f springbootblueprints

docker run -d --restart unless-stopped \
  -e SPRING_PROFILES_ACTIVE=h2 \
  -e TZ=Asia/Seoul \
  --name springbootblueprints -p 11000:11000 \
  -v /docker_data/springbootblueprints:/springbootblueprints \
  springbootblueprints