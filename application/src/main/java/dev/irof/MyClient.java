package dev.irof;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyClient {
    private static final Logger logger = LoggerFactory.getLogger(MyClient.class);

    private final RestTemplate restTemplate;

    MyClient(RestTemplateBuilder restTemplateBuilder, @Value("${my.external.url}") String url) {
        restTemplate = restTemplateBuilder
                .rootUri(url)
                .build();
    }

    public void call() {
        String response = restTemplate.getForObject("/sample", String.class);

        logger.info(response);
    }
}