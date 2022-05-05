package com.nolanprice.name;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.com.google.common.collect.ImmutableList;

@Component
public class NameClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NameClient.class);

    private static final String NAME_URL_TEMPLATE = "https://donjon.bin.sh/name/rpc-name.fcgi?type=%s+%s&n=1";

    private static final List<String> GENDERS = ImmutableList.of("Male", "Female");

    private static final Random RANDOM = new Random();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Map<String, String> RACE_MAPPINGS;
    static {
        Map<String, String> raceMappings = Collections.emptyMap();
        try {
            raceMappings = OBJECT_MAPPER.readValue(NameClient.class.getResourceAsStream("raceMappings.json"),
                                                   new TypeReference<>() {});
        } catch (Exception e) {
            LOGGER.error("Failed to load race mapping file", e);
        }
        RACE_MAPPINGS = raceMappings;
    }

    public CompletableFuture<String> getCharacterName(String race) {
        String gender = GENDERS.get(RANDOM.nextInt(2));
        String mappedRace = RACE_MAPPINGS.get(race);

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(String.format(NAME_URL_TEMPLATE, mappedRace, gender)))
                                         .build();
        return HttpClient.newHttpClient()
                         .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                         .thenApply(HttpResponse::body);
    }

}
