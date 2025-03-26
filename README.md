# rickmortyapi

Introduction

This document outlines how to create a RESTful API for fetching Rick and Morty character data using Kubernetes for deployment and orchestration. The API will be built using Java Springboot and integrated with Kubernetes for scalability and high availability.

Technology Stack

Backend Framework: Springboot API

Database: PostgreSQL (and Redis for caching)

Containerization: Docker

Orchestration: Kubernetes (K8s)

Deployment Management: Helm Charts

API Endpoints

1. Store all characters
   
   - Fetch a paginated list of characters and all contents from api, store to DB  /store-characters
     
2. Search Characters by Species, status and Origin.

   -GET /fetch-characters?species=Human&status=Alive&page=1&size=2

Architecture diagram for technology been used for this project.    
 
![image](https://github.com/user-attachments/assets/72913e41-097f-42db-9e7c-ef60debcb509)

Dockerizing the API

Created a Dockerfile with a multi-stage build process using distroless images (Has enhanced security, minimal dependence and ideal for PRODUCTION workloads )

1. Created a Dockerfile.

FROM openjdk:17-jdk-slim AS build
COPY . /app
WORKDIR /app
FROM gcr.io/distroless/java17-debian12
COPY --from=build /app /app
WORKDIR /app
CMD ["rickmotyapi.jar"]

2. Built and push the docker image
docker build -t rickmoty:v1.0 .
docker push hemambar19/rickmoty:v1.0

Deploying to Kubernetes
1. Created a Deployment YAML file (deployment.yml)
   
apiVersion: apps/v1
kind: Deployment
metadata:
  name: rick-morty-hpa-app
  labels:
    app: rick-morty-hpa-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: rick-morty-hpa-app
  template:
    metadata:
      labels:
        app: rick-morty-hpa-app
    spec:
      containers:
      - name: rick-morty-hpa-app
        image: hemambar17/rickmoty:v1.0
        ports:
        - containerPort: 8082
        resources:
          requests:
            cpu: "250m"
            memory: "512Mi"
          limits:
            cpu: "500m"
            memory: "1Gi"
2. Created a Service YAML file (service.yml)

apiVersion: v1
kind: Service
metadata:
  name: rick-morty-hpa-service
spec:
  selector:
    app: rick-morty-hpa-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8082
  type: ClusterIP

3. Implemented Horizontal pod Autoscaling for high availability

apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: rick-morty-hpa-app
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: rick-morty-hpa-app
  minReplicas: 1
  maxReplicas: 5
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 50

4. Applied the sidecard deployment

apiVersion: apps/v1
kind: Deployment
metadata:
  name: rick-morty-hpa-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: rick-morty-hpa-app
  template:
    metadata:
      labels:
        app: rick-morty-hpa-app
    spec:
      containers:
        - name: rick-morty-hpa-app
          image: hemambar17/rickmoty:v1.0
          ports:
            - containerPort: 80
          volumeMounts:
            - name: shared-logs
              mountPath: /var/log/app

        - name: sidecar-container
          image: busybox
          command: ["/bin/sh", "-c"]
          args:
            - while true; do cat /var/log/app/access.log; sleep 5; done;
          volumeMounts:
            - name: shared-logs
              mountPath: /var/log/app

      volumes:
        - name: shared-logs
          emptyDir: {}
          
5. Apply the confirmation 

Kubectl apply -f deployment.yml
kubectl apply -f service.yml
kubectl apply -f sidecardcontainer.yml
kubectl apply -f hpa.yml

Monitoring the logs

minikube addons enable metrics-server


