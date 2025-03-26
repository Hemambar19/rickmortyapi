FROM openjdk:17-jdk-slim AS build

COPY . /app

# Set the working directory inside the container
WORKDIR /app

#stage 2
#Set the distroless image for second stage
 
FROM gcr.io/distroless/java17-debian12

COPY --from=build /app /app
WORKDIR /app

# Expose the application port (default for Spring Boot is 8080)
EXPOSE 8080

# Define the entry point for the container
CMD ["rickmotyapi.jar"]