FROM centos

RUN yum install -y java

VOLUME /tmp
ADD /build/libs/bialydunajec-backend.jar bialydunajec.jar
RUN sh -c 'touch /bialydunajec.jar'

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /bialydunajec.jar" ]