FROM maven:3.8.4-openjdk-17 AS build

RUN curl -o /usr/local/bin/psql https://ftp.postgresql.org/pub/pgadmin/pgadmin4/v6.2/pip/pgadmin4-6.2-py3-none-any.whl
RUN chmod +x /usr/local/bin/psql

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8090
RUN chmod +x /app/run.sh

#CMD ["java", "-jar", "social-0.0.1-SNAPSHOT.jar"]
CMD ["/bin/bash", "./run.sh"]