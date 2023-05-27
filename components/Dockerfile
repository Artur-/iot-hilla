FROM maven:3-eclipse-temurin-17 as build
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && apt-get install -y nodejs

RUN useradd -m myuser -b /home
RUN mkdir -p /home/myuser/proj
ENV HOME=/home/myuser/proj

ADD . $HOME
WORKDIR $HOME
RUN chown -R myuser:myuser $HOME
USER myuser
RUN --mount=type=cache,target=/root/.m2,sharing=locked npm install
RUN --mount=type=cache,target=/root/.m2,sharing=locked npx vite build

FROM halverneus/static-file-server
COPY --from=build /home/myuser/proj/dist/assets/* /web/

