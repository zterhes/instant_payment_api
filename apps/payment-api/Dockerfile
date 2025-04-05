FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

COPY apps/payment-api/pom.xml ./apps/payment-api/
COPY apps/notification-api/pom.xml ./apps/notification-api/
COPY apps/user-api/pom.xml ./apps/user-api/

RUN ./mvnw dependency:go-offline -B

COPY apps/payment-api/src ./apps/payment-api/src/

RUN ./mvnw package -DskipTests -pl apps/payment-api

# Extract the JAR
RUN mkdir -p /extracted && \
    JAR_FILE=$(find apps/payment-api/target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" | head -n 1) && \
    cp "$JAR_FILE" /extracted/app.jar && \
    cd /extracted && jar -xf app.jar

# ───────────────────────────────────────────────────────────────

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

COPY --from=build /extracted/BOOT-INF/lib /app/lib
COPY --from=build /extracted/META-INF /app/META-INF
COPY --from=build /extracted/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.payment_api.PaymentApiApplication"]
