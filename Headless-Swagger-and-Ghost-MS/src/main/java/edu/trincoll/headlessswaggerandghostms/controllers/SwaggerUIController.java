package edu.trincoll.headlessswaggerandghostms.controllers;

import edu.trincoll.headlessswaggerandghostms.records.KitchenItem;
import edu.trincoll.headlessswaggerandghostms.records.RecipeRecommendationResponse;
import edu.trincoll.headlessswaggerandghostms.records.SpoonacularRecipeResponse;
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

import java.util.List;

@RestController
public class SwaggerUIController
{
    @Value("${MS1_BASE_URL:${ms1_base_url}}")
    private String ms1BaseUrl;
    @Value("${MS3_BASE_URL:${ms3_base_url}}")
    private String ms3BaseUrl;

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
                .map(recipeRecommendationResponse -> new RecipeRecommendationResponse(recipeRecommendationResponse.text().replace("\n", " ")));
    }

    // Health endpoint for Kubernetes ingress
    @GetMapping("/health")
    public String Health()
    {
        return "Personal Recipe Book and Kitchen Inventory Tracker";
    }
}
