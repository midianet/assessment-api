'# base image to build a JRE
#FROM amazoncorretto:19.0.1-alpine3.16 as corretto-jdk
FROM openjdk:19-jdk-alpine3.16 as main-jdk


# required for strip-debug to work
RUN apk add --no-cache binutils

#ALL-MODULE-PATH \
# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules java.base,java.xml,java.desktop,java.instrument,java.prefs,java.management,java.naming,java.net.http,java.security.jgss,java.security.sasl,java.sql,jdk.httpserver,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

# main app image
FROM alpine:3.16
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=main-jdk /jre $JAVA_HOME

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER \
   && mkdir /app \
   && chown -R $APPLICATION_USER /app

USER 1000

COPY --chown=1000:1000 ./app.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]
