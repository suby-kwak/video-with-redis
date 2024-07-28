# Redis 를 활용하여 서비스 속도를 개선하는 틱X/유X브 서비스 개발

## Local 실행 환경
### MySql, Redis, MongoDB start
`./tools` directory에서 \
`docker-compose up -d`

### MySql, Redis, MongoDB stop
`docker-compose down`

### Spring Boot application 실행
`./gradlew bootRun`

## docker 실행 상태에서 DB/Redis 접근
### MySql
`docker exec -it mytv-mysql bash` \
`mysql -u local -p`

### Redis
`docker exec -it mytv-redis sh` \
`redis-cli`

### MongoDB
`docker exec -it mytv-mongodb sh` \
`mongosh -u local -p local`


## 참고
- Redis Command : https://redis.io/docs/latest/commands/
- Spring Data RedisTemplate 지원 Redis Command : https://docs.spring.io/spring-data/redis/reference/appendix.html
