package edu.trincoll.headlessswaggerandghostms.controllers;

import edu.trincoll.headlessswaggerandghostms.records.KitchenItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class HeadlessController
{
    @Value("${ms1_base_url}")
    private String ms1BaseUrl;
    @Value("${ms3_base_url}")
    private String ms3BaseUrl;

    private static final Logger log = LoggerFactory.getLogger(HeadlessController.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

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
}
