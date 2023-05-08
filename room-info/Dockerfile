FROM maven:3-eclipse-temurin-17 as build
RUN useradd -m myuser -b /home
RUN mkdir -p /home/myuser/proj
ENV HOME=/home/myuser/proj

ADD pom.xml $HOME
WORKDIR $HOME
RUN chown -R myuser:myuser $HOME
#USER myuser
RUN mvn dependency:3.5.0:go-offline -Pproduction

# Workaround for Hilla not finding node
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && apt-get install -y nodejs
#ENV PATH="$PATH:/root/.vaadin/node/bin:$HOME/.vaadin/node/bin"
#RUN --mount=type=cache,target=$HOME/.m2,sharing=locked mvn -X  dependency:go-offline -Pproduction

# Charts needs a license
ENV LICENSE_FILE=/root/.vaadin/offlineKey
COPY offlineKey.ci $LICENSE_FILE

ADD . $HOME

RUN mvn package -Pproduction

FROM eclipse-temurin:17-jre
COPY --from=build /home/myuser/proj/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
