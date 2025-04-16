FROM eclipse-temurin:17-jre
ENV DB_HOST_IP="localhost"
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY wait-for-it.sh ./wait-for-it.sh
RUN chmod +x wait-for-it.sh
CMD ["java", "-jar", "app.jar"]
