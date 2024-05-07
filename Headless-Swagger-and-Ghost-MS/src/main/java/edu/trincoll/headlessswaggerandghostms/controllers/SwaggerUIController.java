package edu.trincoll.headlessswaggerandghostms.controllers;

import edu.trincoll.headlessswaggerandghostms.records.KitchenItem;
import edu.trincoll.headlessswaggerandghostms.records.RecipeRecommendationResponse;
import edu.trincoll.headlessswaggerandghostms.records.SpoonacularRecipeResponse;
import edu.trincoll.headlessswaggerandghostms.records.Recipe;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerUIController
{
    @Value("${MS1_BASE_URL:${ms1_base_url}}")
    private String ms1BaseUrl;
    @Value("${MS2_BASE_URL:${ms2_base_url}}")
    private String ms2BaseUrl;
    @Value("${MS3_BASE_URL:${ms3_base_url}}")
    private String ms3BaseUrl;
    @Value("${GHOST_API_KEY:not-available}")
    private String ghostApiKey;
    @Value("${GHOST_HOST:not-available}")
    private String ghostHost;
    @Value("${GHOST_PORT:not-available}")
    private String ghostPort;

    private static final Logger log = LoggerFactory.getLogger(SwaggerUIController.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

    // MS1 Endpoints
    @GetMapping("/kitchenItems")
    public Mono<List<KitchenItem>> getAllKitchenItems()
    {
        String url = ms1BaseUrl + "/kitchenItems";
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<KitchenItem>>() {
                });
    }

    @GetMapping("/kitchenItems/{id}")
    public Mono<KitchenItem> getKitchenItemById(@PathVariable String id)
    {
        String url = ms1BaseUrl + "/kitchenItems/" + id;
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(KitchenItem.class);
    }

    @PostMapping("/kitchenItems")
    public Mono<KitchenItem> createKitchenItem(@RequestBody KitchenItem kitchenItem)
    {
        String url = ms1BaseUrl + "/kitchenItems";
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(kitchenItem))
                .retrieve()
                .bodyToMono(KitchenItem.class);
    }

    @DeleteMapping("/kitchenItems/{id}")
    public Mono<Void> deleteKitchenItem(@PathVariable String id)
    {
        String url = ms1BaseUrl + "/kitchenItems/" + id;
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        return client.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @PutMapping("/kitchenItems/{id}")
    public Mono<KitchenItem> updateKitchenItem(@PathVariable String id, @RequestBody KitchenItem kitchenItem)
    {
        String url = ms1BaseUrl + "/kitchenItems/" + id;
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        return client.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(kitchenItem))
                .retrieve()
                .bodyToMono(KitchenItem.class);
    }

    // MS2 Endpoints
    @GetMapping("/recipes")
    public Mono<List<Recipe>> getAllRecipes() {
        String url = ms2BaseUrl + "/recipes";
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Recipe>>() {
                });
    }

    @GetMapping("/recipes/{id}")
    public Mono<Recipe> getRecipeById(@PathVariable String id) {
        String url = ms2BaseUrl + "/recipes/" + id;
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Recipe.class);
    }

    @DeleteMapping("/recipes/{id}")
    public Mono<Void> deleteRecipeById(@PathVariable String id) {
        String url = ms2BaseUrl + "/recipes/" + id;
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @PostMapping("/recipes")
    public Mono<Recipe> createRecipe(@RequestBody Recipe recipe) {
        String url = ms2BaseUrl + "/recipes";
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(recipe))
                .retrieve()
                .bodyToMono(Recipe.class);
    }

    @PutMapping("/recipes/{id}")
    public Mono<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe recipeUpdate) {
        String url = ms2BaseUrl + "/recipes/" + id;
        log.info("URL: " + url);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();
        return client
                .put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(recipeUpdate))
                .retrieve()
                .bodyToMono(Recipe.class);
    }

    // MS3 Endpoints
    @PostMapping("/getRandomRecipe")
    public Mono<SpoonacularRecipeResponse> getRandomRecipe()
    {
        String url = ms3BaseUrl + "/getRandomRecipes";
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        return client.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SpoonacularRecipeResponse.class);
    }

    @PostMapping("/openAIRecommendation")
    public Mono<RecipeRecommendationResponse> getRecipeRecommendation()
    {
        String url = ms3BaseUrl + "/openaiRecipe1";
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        return client.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RecipeRecommendationResponse.class)
                .map(recipeRecommendationResponse -> {
                    String responseText = recipeRecommendationResponse.text().replace("\n", " ");
                    if (!ghostApiKey.equals("not-available") && !ghostHost.equals("not-available") && !ghostPort.equals("not-available")) {
                        postToGhost(responseText);
                    }
                    return new RecipeRecommendationResponse(responseText);
                });
    }

    // Posting to Ghost
    private void postToGhost(String content) {
        String[] parts = ghostApiKey.split(":");
        String id = parts[0];
        String secret = parts[1];

        long currentTime = System.currentTimeMillis() / 1000;
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        long expirationTime = expirationDate.toEpochSecond(ZoneOffset.UTC);

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        header.put("kid", id);

        Map<String, Object> payload = new HashMap<>();
        payload.put("iat", currentTime);
        payload.put("exp", expirationTime);
        payload.put("aud", "/admin/");

        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .signWith(secretKey)
                .compact();

        String ghostUrl = "http://" + ghostHost + ":" + ghostPort + "/ghost/api/admin/posts/";

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> post = new HashMap<>();
        post.put("title", "Recipe Recommendation");
        post.put("content", content);
        requestBody.put("posts", new Object[]{post});

        WebClient webClient = WebClient.builder()
                .baseUrl(ghostUrl)
                .defaultHeader("Authorization", "Ghost " + token)
                .defaultHeader("Content-Type", "application/json")
                .build();

        webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Response from Ghost: " + response));
    }

    // Health endpoint for Kubernetes ingress
    @GetMapping("/health")
    public String Health()
    {
        return "Personal Recipe Book and Kitchen Inventory Tracker";
    }
}
