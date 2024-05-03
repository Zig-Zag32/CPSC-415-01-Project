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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<List<KitchenItem>> getAllKitchenItems(RestTemplate restTemplate)
    {
        String url = ms1BaseUrl + "/kitchenItems";
        try
        {
            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<KitchenItem>>() {
                    }
            );
        }
        catch (Exception e)
        {
            log.error("Failed to fetch data from " + url, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
