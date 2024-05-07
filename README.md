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

- [ ] MS 2: REST recipe service(Ibsa*).
    - With CRUD operations for the recipe book.  

- [ ] MS 3: Recipe recommender(Jeff*).
    - Calling the `read` methods from MS 1 to access ingredients inventory data.
    - Generates Recipe based on ingredients inventory data, powered by LLM llama 2 or Chat GPT 3.5-turbo.
    - Generates a random recipe based on the list of missing materials using [Spoonacular API-Get Random Recipes](https://spoonacular.com/food-api/docs#Get-Random-Recipes) 

- [ ] MS 4: Shopping list generator(Jeff*).
    - Calling the `read` methods from MS 2 to find out what ingredients we need for cooking certain recipe.
    - Calling the `read` methods from MS 1 to find our what ingredients we already have.
    - Required ingredients - Ingredients in stock = List of missing materials. 
    - Generates a shopping list based on the list of missing materials using [Spoonacular API-Compute Shopping List](https://spoonacular.com/food-api/docs#Compute-Shopping-List)  

- [ ] MS 5: Swagger Interface(George*).  
    - MS 5 is a microservice that integrates functionalities from Microservices 1, 2, 3, and 4, providing a unified set of APIs in Swagger UI.  
    - MS 5 is able to call the Ghost API (internal API) to post blogs based on recommended recipes.  

- [ ] Ghost. 
    - 
    - Step 1: APIs of MS 3 are called by MS 5.
    - Step 2: MS 3 gets stored food information from MS1 and sends it to Chat GPT 3.5-turbo with a prompt.
    - Step 3: Chat GPT 3.5 sends a JSON response(a recipe).
    - Step 3: MS 3 sends back a JSON response to MS 5.
    - Step 4: MS 5 calls Ghost API and sends the JSON response to it.
    - Step 5: Ghost posts a blog based on MS 3's JSON response on http://34.49.160.195/.



Component Diagram:
![Component Diagram](images/componentUML.png)  

Configuration Diagram:
![Configuration Diagram](images/configurationUML.png)  

Ollama on Cluster:
![ollama](images/ollama.png)

## Technologies
- Microservices' source code: 
    - Spring Boot, Node.js.
- Ghost (Internal API): 
    - For posting blogs based on recommended recipes.
- Spoonacular API (External API): 
    - For getting a random recipes and computing shopping list.
- LLM: 
    - Ollama + Llama2 (Internal API), 
        - For generating a recipe recommendation.
    - Chat GPT 3.5-turbo (External API): 
        - For generating a recipe recommendation.
- Docker desktop: 
    - For Containerization.
- Kubernetes: 
    - For Container Orchestration.

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
6. Week 15(only two days): May 7 Course Project Presentations.  

## Getting started
### User access: 
1. MS 5: http://34.111.130.246/  

2. Ghost: http://34.49.160.195/

### Deploying instructions:   
This project is a microservice-based application designed to be deployed and run on Kubernetes. To deploy it, you'll need either Docker Desktop (with Kubernetes enabled) or a standalone Kubernetes cluster.

To deploy this application on Kubernetes, using this command (ONLY ONE COMMAND!AND IT DEPLOY EVERYTHING!!!):
```
kubectl apply -f k8s 
```
To delete this application on Kubernetes, using this command:
```
kubectl delete -f k8s 
```