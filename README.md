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

3. Architecture diagram for technology been used for this project.    
 
![image](https://github.com/user-attachments/assets/72913e41-097f-42db-9e7c-ef60debcb509)
