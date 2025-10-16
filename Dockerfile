# =================================================
# 1. 빌드(Build) 스테이지
# =================================================
FROM eclipse-temurin:17-jdk-jammy as builder

# 작업 디렉토리 설정
WORKDIR /app

# 그래들 래퍼와 빌드 파일 복사 (Docker 캐시 활용)
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle

# 그래들 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 먼저 다운로드 (소스 코드 변경 시에도 캐시된 레이어 사용)
RUN ./gradlew dependencies

# 소스 코드 복사
COPY src ./src

# 애플리케이션 빌드 (테스트는 CI 단계에서 실행하므로 제외)
RUN ./gradlew build -x test


# =================================================
# 2. 실행(Runner) 스테이지
# =================================================
FROM eclipse-temurin:17-jre-jammy

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일만 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 포트 노출
EXPOSE 8080

# 컨테이너 시작 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]