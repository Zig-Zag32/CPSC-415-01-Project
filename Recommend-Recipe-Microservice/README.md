# Recommend-Recipe-Microservice
This Microservice get the stock of the user from Microservice 1 and send it to LLM llama 2 with a prompt, getting a recipe recommend back which only uses stuff in the stock. 

## Prerequisites
1. Docker Desktop
2. helm

## Getting stared with Ollama 
1. Open docker desktop and start k8s.
2. Install helm chart:
```
helm install ollama /"Path to your CPSC 415-01 Project folder"/CPSC-415-01-Project/Recommend-Recipe-Microservice/ollama-0.21.1.tgz  
```
3. Connecting your local port 11434 with Ollama:
```
kubectl port-forward service/ollama 11434:11434  
```  

## Getting stared with MS 3
1. Get an image for app.js
```
docker pull jeffyf/app.js:latest
```
or 
```
docker build -t app.js:latest .
```
2. Run app.js locally.
```
node app
```

3. Visting the Swagger UI at http://localhost:2001/swagger 
  
    

//2. Get a container for app.js
//```
//docker run -d --name app.js -p 2001:2001 jeffyf/app.js:latest
//```