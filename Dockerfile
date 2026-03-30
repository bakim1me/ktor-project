
FROM gradle:9.2.1-jdk21 AS builder

WORKDIR /app

COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .

COPY gradle gradle
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test


FROM eclipse-temurin:21-jre-jammy AS runner

WORKDIR /app

# JAR 복사
COPY --from=builder /app/build/libs/app.jar .

# 서버 포트
EXPOSE 8080

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
