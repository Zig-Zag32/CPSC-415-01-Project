# REST Recipe Service

## Prerequisites:
- Have [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed

## To Build:
1. Clone this [repository](https://github.com/Zig-Zag32/REST-Recipe-Service) into a directory of your choice
2. Open Docker Desktop
3. Navigate to the ```REST-Recipe-Service``` directory that was just created by the git clone in a terminal window
4. Run ```./gradlew build```
5. Build a Docker image of the app with 
```docker build -t rest-recipe-service .```
6. Ensure the image was created with ```docker images```

## To Run:
1. Create a Docker container of the image with ```docker run -d -p 8080:8080 rest-recipe-service```
2. Ensure the container is running with ```docker ps```
3. In a browser, go to ```http://localhost:8080/swagger-ui/index.html```

## Development Process
I originally wanted to make this a simple recipe book-like REST service, but given that
recipes would require a collection of ingredients as an attribute, I decided to
just make this functional for ingredients right now to eliminate that level of
complexity for now, though I've left room for it to be implemented at a later time
if I decide to go for it. 

I broke production of this app into three parts: Spring and CRUD, implementing Swagger,
and Docker. Though after doing so, there are some things I would do differently. 
When working on the CRUD operations and Spring setup, I based most of what I did
off of a similar project I did in Prof. Kousen's 310 class. 

I first ran into issues when trying to implement Swagger. From all my research it was 
apparent that implementing Swagger was as simple as adding one or two lines in my 
build.gradle file, and adding a simple config class. This ended up being the case, 
but finding the right combination of dependencies for this to work took a lot of 
trial and error.

Adding Docker went a bit smoother. I was able to generate a rough idea of what the
Dockerfile should look like from my notes and the use of generative AI (I generally
use Claude), and with some help from Prof. Johnson was able to get it to work as 
intended, and make sure Docker was accessing the right paths and files.

### Tools Used
- IntelliJ IDEA (IDE)
- [Previous project](https://github.com/Zig-Zag32/Personal-Library-Service) as reference
- Various internet sources
- Claude AI by Anthropic
- Peers and Professors