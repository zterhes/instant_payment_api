FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace

# Copy the Maven wrapper files and main POM first
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Copy all module POMs to resolve dependencies
COPY apps/payment-api/pom.xml ./apps/payment-api/
COPY apps/notification-api/pom.xml ./apps/notification-api/
COPY apps/user-api/pom.xml ./apps/user-api/
COPY shared-lib/pom.xml ./shared-lib/
COPY shared-lib/src ./shared-lib/src/

# First install the shared library to local Maven repository
RUN ./mvnw clean install -pl shared-lib -DskipTests

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code for the specific module
COPY apps/notification-api/src ./apps/notification-api/src/

# Build the specific module
RUN ./mvnw package -DskipTests -pl apps/notification-api

# Extract the JAR
RUN mkdir -p /workspace/target/extracted && \
    JAR_FILE=$(find apps/notification-api/target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" | head -n 1) && \
    cp "$JAR_FILE" /workspace/target/extracted/app.jar && \
    cd /workspace/target/extracted && jar -xf app.jar

# ───────────────────────────────────────────────────────────────

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

COPY --from=build /workspace/target/extracted/BOOT-INF/lib /app/lib
COPY --from=build /workspace/target/extracted/META-INF /app/META-INF
COPY --from=build /workspace/target/extracted/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.notification_api.NotificationApiApplication"]
