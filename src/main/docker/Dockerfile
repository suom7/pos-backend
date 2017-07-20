FROM java:8
RUN apt-get update && apt-get install netcat-openbsd -y
VOLUME /tmp
ADD target/app.jar app.jar
RUN sh -c 'touch /app.jar'

ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'

ENTRYPOINT ["/bin/bash", "/wrapper.sh"]
