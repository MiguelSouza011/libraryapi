#build

FROM maven:3.10.0-amazoncorretto-25-debian-trixie AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

#run
FROM amazoncorretto:25-al2023-headless
WORKDIR /app

COPY --from=build /build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DB_URL=''
ENV DB_USERNAME=''
ENV DB_PASSWORD=''
ENV DB_DRIVER=''

ENV GOOGLE_CLIENT_ID=''
ENV GOOGLE_CLIENT_SECRET=''

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT ["java", "-jar", "libraryapi.jar"]

