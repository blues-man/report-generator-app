FROM registry.access.redhat.com/ubi8/openjdk-8-runtime:1.21
COPY target/*.jar /deployments/app.jar
