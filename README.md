# lnu ht19 ass
[![Actions Status](https://github.com/kamko/lnu_ht19_ass/workflows/Java%20CI/badge.svg)](https://github.com/kamko/lnu_ht19_ass/actions)
[![image metadata](https://images.microbadger.com/badges/image/kamko/lnu_ht19_ass.svg)](https://microbadger.com/images/kamko/lnu_ht19_ass "kamko/lnu_ht19_ass image metadata")

## Requirements
- Java 13
- Lombok support in IDE (not required but useful)
- Gradle 6-RC1 (or use Gradle wrapper)
- Eventuate Local environment

## How to build
- `gradle build`

## Configuration
Using environment variables provided to docker container. 
If not using docker you can also use Spring configuration via files.

```
    BASE_URL: <base-url-of-service>
    AUTH_JWT_SECRET: <secret-used-for-signing-tokens>
    GOOGLE_SECRET_CLIENT_ID: <google-client-id>
    GOOGLE_SECRET_SECRET: <google-app-secret>
```

## How to run
The best and easiest way to run application is using docker-compose. Example is in `/dev` folder. 

Before running you need to edit `DOCKER_HOST_IP` in `run-all.sh` script. 
Google app credentials must be in `.env` file next to the docker-compose file.
See [eventuate documentation](http://eventuate.io/docs/usingdocker.html) for more details.
This docker-compose file will build new artifact (so you need to run `gradle build` before).

There are also automatically built images using GitHub Actions and published on docker hub:
- [kamko/lnu_ht19_ass](https://hub.docker.com/r/kamko/lnu_ht19_ass)

## License
[MIT](LICENSE)