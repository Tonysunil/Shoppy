# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application (this will generate the WAR file in the target directory)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using Tomcat
# We use Tomcat 10+ because the project uses Jakarta EE 10 (jakarta.servlet.jsp.jstl-api 3.0.0)
FROM tomcat:10.1-jdk21
WORKDIR /usr/local/tomcat

# Remove the default Tomcat applications to keep it clean (optional)
RUN rm -rf webapps/*

# Copy the generated WAR file from the build stage to Tomcat's webapps directory
# Renaming it to ROOT.war so it serves at the root path (/)
COPY --from=build /app/target/MyShoppy-0.0.1-SNAPSHOT.war webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
