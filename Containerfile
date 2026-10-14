FROM registry.access.redhat.com/ubi8/openjdk-8-runtime:latest
COPY target/*.jar /deployments/app.jar
