FROM maven:3-eclipse-temurin-17 as build
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && apt-get install -y nodejs

RUN useradd -m myuser -b /home
RUN mkdir -p /home/myuser/proj
ENV HOME=/home/myuser/proj

ADD pom.xml $HOME
WORKDIR $HOME
RUN chown -R myuser:myuser $HOME
#USER myuser

ADD . $HOME

# This is needed so that Hilla can launch its plugin correctly
ENV MAVEN_CONFIG=

RUN --mount=type=cache,target=/root/.m2,sharing=locked mvn package -Pproduction

FROM eclipse-temurin:17-jre
COPY --from=build /home/myuser/proj/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
