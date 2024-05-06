# CPSC-415-01-Project: Personal Recipe Book and Kitchen Inventory Tracker
### * Indicates MVP

This project is a simple Recipe Book and Kitchen Inventory service. Recipes and food items all have CRUD operations available to the user, and other functionality may include shopping list recommendations, and recipe recommendations based on current kitchen inventory. The goal of this project is to help provide a user-friendly centralized place that makes life easier for casual kitchen users.

## Group Members
- George Zack
- Jeff Cui
- Ibsa Tassew Geleta

## Microservices

- [ ] MS 1: REST food item service(George*). 
    - With CRUD operations for the current kitchen stock.
    - **To Run This Microservice In a Docker Container**:
        1. If not done already, clone this repository to a local directory of your choice:
           ```
           git clone https://github.com/Zig-Zag32/CPSC-415-01-Project
           ```
        2. Navigate to this microservice's directory:
           ```
           cd Kitchen-Inventory-Microservice
           ```
        3. Run Gradle build:
           ```
           ./gradlew build
           ```
        4. Build a docker image of this microservice:
           ```
           docker build -t kitchen-inventory-microservice .
           ```
        5. Run this docker image in a container on port 8080:
           ```
           docker run -d -p 8080:8080 kitchen-inventory-microservice
           ```
        6. In a browser, navigate to the visual API:
           ```
           http://localhost:8080/swagger-ui/index.html
           ```

- [ ] MS 2: REST recipe service(Ibsa*).
    - With CRUD operations for the recipe book.  

- [ ] MS 3: Recipe recommender(Jeff*).
    - Calling the `read` database methods from MS 1 to access ingredients inventory data.
    - Generates Recipe based on ingredients inventory data, powered by LLM llama 2 or Chat GPT 3.5-turbo.
    - Generates a random recipe based on the list of missing materials using [Spoonacular API-Get Random Recipes](https://spoonacular.com/food-api/docs#Get-Random-Recipes) 

- [ ] MS 4: Shopping list generator(Jeff*).
    - Calling the `read` database methods from MS 2 to find out what ingredients we need for cooking certain recipe.
    - Calling the `read` database methods from MS 1 to find our what ingredients we already have.
    - Required ingredients - Ingredients in stock = List of missing materials. 
    - Generates a shopping list based on the list of missing materials using [Spoonacular API-Compute Shopping List](https://spoonacular.com/food-api/docs#Compute-Shopping-List)  

- [ ] MS 5: Headless MS  
    - MS 5 is a headless microservice that integrates functionalities from Microservices 1, 2, 3, and 4, providing a unified set of APIs for the frontend (Ghost).  



Component Diagram:
![Component Diagram](images/componentUML.png)  

Configuration Diagram:
![Configuration Diagram](images/configurationUML.png)  

Ollama on Cluster:
![ollama](images/ollama.png)

## Technologies
- Microservices: Spring Boot, Node.js
- HTML/CSS/JavaScript
- LLM: Llama2, Chat GPT 3.5-turbo (Open AI API)
- Docker for Containerization
- Kubernetes for Container Orchestration

## UI Layout
- Landing page will have centralized buttons to navigate to each microservice.
- Each microservice will have its own page in the UI. Once the home page is exited, the services will appear in a side bar.
- REST pages will have four subpages; one for each operation.
- The search pages will list the contents of the recipe book or kitchen inventory, with searching options displayed above
- Each subpage will have input fields for necessary information the user will have to input, as well as a display for recipes.
- The recommendation pages will have a generate button, as well as a display for the result.  

## Milestones (2 days left)
1. Week 10: Set up component diagram.
2. Week 11: Get MVP services working. Not necessarily correctly, but communicating.
3. Week 12: Get each microservice running and containerize them
4. Week 13: Get everything running correctly on the docker desktop cluster.
5. Week 14: Deploy everything to the GKE cluster. 
6. Week 15(only two days): May 7 Course Project Presentations

## Getting started
This project is designed as a microservice-based application that can be deployed and run on Kubernetes.  

User access: http://34.111.130.246/   

To deploy this application on Kubernetes, using this command (ONLY ONE COMMAND!!!):
```
kubectl apply -f k8s 
```
To delete this application on Kubernetes, using this command (ONLY ONE COMMAND!!!):
```
kubectl delete -f k8s 
```