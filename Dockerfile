FROM amazoncorretto:17 AS base

# create workdir
WORKDIR /app

# copy source and maven-related files
COPY parking-lot-application/src/src/ src
COPY .mvn/ .mvn
COPY pom.xml mvnw ./
RUN ["./mvnw", "dependency:go-offline"]

FROM base AS test
RUN ["./mvnw", "test"]

FROM base AS build
RUN ["./mvnw", "package", "-Dmaven.test.skip=true"]

FROM amazoncorretto:17-alpine AS development
COPY --from=build /app/target/parking-lot-intive-patronage-1.0.0-RELEASE.jar /application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar", "--spring.profiles.active=default,development"]

FROM amazoncorretto:17-alpine AS production
COPY --from=build /app/target/parking-lot-intive-patronage-1.0.0-RELEASE.jar /application.jar
EXPOSE 8081
CMD ["java", "-jar", "application.jar", "--spring.profiles.active=default -Dserver.port=8081"]
