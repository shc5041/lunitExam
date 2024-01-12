FROM openjdk:17

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/\$TZ /etc/localtime && echo \$TZ > /etc/timezone
RUN mkdir -p /app
WORKDIR /app
ADD target/LunitExam-0.0.1-SNAPSHOT.jar lunit.jar
CMD ["java", "-server", "-Xms1024m","-Xmx1024m", "-jar", "/app/lunit.jar"]