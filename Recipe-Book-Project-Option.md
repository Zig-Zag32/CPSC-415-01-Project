# CPSC-415-01-Project

This project is a simple Recipe Book and Kitchen Inventory service. Recipes and food items all have CRUD operations available to the user, and other functionality may include shopping list reccomendations, and recipe reccomendations based on current kitchen inventory. The goal of this project is to help provide a user-friendly centralized place that makes life easier for casual kitchen users.

## Group Members
- George Zack
- Jeff Cui
- Ibsa Tassew Geleta

## Possible Microservices

1. REST food item service, with CRUD operations for the current kitchen stock
2. REST recipe service, with CRUD operstions for the recipe book
3. Shopping list generator, based on current kitchen stock, possibly powered by an LLM
4. Recipe recommender based on current kitchen stock
5. Database module will store user data on the cloud

## UI Layout

- Landing page will have centralized buttons to navigate to each microservice.
- Each microservice will have its own page in the UI. Once the home page is exited, the services will appear in a side bar.
- REST pages will have four subpages; one for each operation.
- The search pages will list the contents of the recipe book or kitchen inventory, with searching options displayed above
- Each subpage will have input fields for necessary information the user will have to input, as well as a display for recipes.
- The recommendation pages will have a generate button, as well as a display for the result.