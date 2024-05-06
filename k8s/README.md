# Getting stared with K8s
This is an instruction for deploying the whole project to your k8s cluster.

1. Get into the folder where k8s's YAMLs located following commands:
```
cd k8s
```

1. Create 5 namespaces for five microservices using following command:
```
kubectl apply -f namespaces.yaml
```

2. Deploy the ConfigMap, Secret, and Ingress using the following commands:
```
kubectl apply -f configMap.yaml
kubectl apply -f recommend-recipe-ms-secret.yaml
kubectl apply -f ingress.yaml
```

3. Create service and pods for all microservices using following commands:
```
kubectl apply -f kitchen-inventory-ms-service.yaml
kubectl apply -f kitchen-inventory-ms-deployment.yaml

kubectl apply -f recommend-recipe-ms-service.yaml
kubectl apply -f recommend-recipe-ms-deployment.yaml
```