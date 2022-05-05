package com.nolanprice.dnd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nolanprice.Choice;

@Component
public class DndApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DndApiClient.class);

    private static final URI BASE_URI = URI.create("https://www.dnd5eapi.co");

    private static final HttpClient CLIENT = HttpClient.newBuilder()
                                                       .version(HttpClient.Version.HTTP_1_1)
                                                       .connectTimeout(Duration.ofSeconds(5))
                                                       .executor(Executors.newFixedThreadPool(10))
                                                       .build();
    private static final Random RANDOM = new Random();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private interface Endpoints {
        String CLASSES = "/api/classes";
        String RACES = "/api/races";
        String BACKGROUNDS = "/api/backgrounds";
        String ALIGNMENTS = "/api/alignments";
    }

    public CompletableFuture<Race> getRace() {
        System.out.println("GET RACE");
        return fetchRandomApiReferenceValue(Endpoints.RACES, Race.class);
    }

    public CompletableFuture<CharacterClass> getCharacterClass() {
        System.out.println("GET CLASS");
        return fetchRandomApiReferenceValue(Endpoints.CLASSES, CharacterClass.class);
    }

    public CompletableFuture<Background> getBackground() {
        System.out.println("GET BACKGROUND");
        return fetchRandomApiReferenceValue(Endpoints.BACKGROUNDS, Background.class);
    }

    public CompletableFuture<Alignment> getAlignment() {
        System.out.println("GET ALIGNMENT");
        return fetchRandomApiReferenceValue(Endpoints.ALIGNMENTS, Alignment.class);
    }

    public CompletableFuture<Choice<Equipment>> expandEquipmentChoices(Choice<Equipment> equipmentChoice) {
        System.out.println("EXPAND EQUIPMENT WITH CHOICE");
        List<Equipment> newChoices = new ArrayList<>();
        List<CompletableFuture<Void>> equipmentFutures = new ArrayList<>();
        for (Equipment equipment : equipmentChoice.getFrom()) {
            if (equipment.getEquipmentCategory() != null) {
                CompletableFuture<Void> expandChoicesFuture = executeRequest(getApiUri(equipment.getEquipmentCategory()
                                                                                                .getUrl()),
                                                                             EquipmentList.class)
                        .thenAccept(equipmentList -> {
                            newChoices.addAll(equipmentList.getEquipment()
                                                           .stream()
                                                           .map(newEquipment -> new Equipment(newEquipment, 1))
                                                           .collect(Collectors.toList()));
                        });
                equipmentFutures.add(expandChoicesFuture);
            } else {
                newChoices.add(equipment);
            }
        }
        return CompletableFuture.allOf(equipmentFutures.toArray(new CompletableFuture[] {}))
                                .thenApply(empty -> new Choice<>(equipmentChoice.getChoose(), equipmentChoice.getType(), newChoices));
    }

    public CompletableFuture<Choice<Equipment>> expandEquipmentChoices(EquipmentOption equipmentOption) {
        System.out.println("EXPAND EQUIPMENT WITH OPTION");
        return executeRequest(getApiUri(equipmentOption.getFrom()
                                                       .getEquipmentCategory()
                                                       .getUrl()), EquipmentList.class)
                .thenApply(equipmentList -> {
                    List<Equipment> newChoices = equipmentList.getEquipment()
                                                              .stream()
                                                              .map(newEquipment -> new Equipment(newEquipment, 1))
                                                              .collect(Collectors.toList());
                    return new Choice<>(equipmentOption.getChoose(), null, newChoices);
                });
    }

    private URI getApiUri(String endpoint) {
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }
        return BASE_URI.resolve(endpoint);
    }

    private URI getApiUri(String endpoint, String index) {
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }
        return BASE_URI.resolve(endpoint + "/" + index);
    }

    private <T> CompletableFuture<T> fetchRandomApiReferenceValue(String endpoint, Class<T> responseClass) {
        return executeRequest(getApiUri(endpoint), ResourceList.class)
                .thenCompose(resourceList -> {
                    int chosenClassIndex = RANDOM.nextInt(resourceList.getCount());
                    ApiReference apiReference = resourceList.getResults().get(chosenClassIndex);
                    URI uri = apiReference.getUrl() != null ? getApiUri(apiReference.getUrl()) : getApiUri(endpoint, apiReference.getIndex());
                    return executeRequest(uri, responseClass);
                });
    }

    private <T> CompletableFuture<T> executeRequest(URI uri, Class<T> responseClass) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(uri)
                                         .build();
    return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                 .thenApply(response -> parseSafely(response.body(), responseClass));
    }

    private <T> T parseSafely(InputStream inputStream, Class<T> responseClass) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, responseClass);
        } catch (IOException e) {
            LOGGER.error("Failed to parse response");
            return null;
        }
    }

}
