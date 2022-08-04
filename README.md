[![CircleCI](https://dl.circleci.com/status-badge/img/gh/vlad-nitu/spring5-webflux-rest/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/vlad-nitu/spring5-webflux-rest/tree/master)
# Reactive Spring Application 
- Project created to work on during [Section 26: RESTFul WebServices with Spring WebFlux](https://www.udemy.com/course/spring-framework-5-beginner-to-guru/)

## Important notes
- **Embedded MongoDB** is only used for testing purposes. If you want to **run the application**, **MongoDB** should be run manually (i.e: `docker run -p 27017:27017 --name some-mongo -d mongo` to start a docker image 'mongo' inside a docker container with name 'some-mongo' on port 27017 (default port for MongoDB). Otherwise `Connection refused: localhost/[0:0:0:0:0:0:0:1]:27017` will be thrown  
