FROM eclipse-temurin:17-jre
ENV DB_HOST_IP="localhost"
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY wait-for-it.sh ./wait-for-it.sh
RUN chmod +x wait-for-it.sh
# 轉換換行符號
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./wait-for-it.sh
CMD ["java", "-jar", "app.jar"]
