package com.nolanprice.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nolanprice.Choice;
import com.nolanprice.ChoiceUtils;

import graphql.com.google.common.collect.ImmutableList;

@Component
public class SpriteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteBuilder.class);

    private static final List<String> GENDERS = ImmutableList.of("male", "female");
    private static final Random RANDOM = new Random();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);
    public  static final String SPRITE_FOLDER = "sprites";

    private static final List<AllowedPaths> ALLOWED_PATHS;
    static {
        List<AllowedPaths> allowedPaths = Collections.emptyList();
        try {
            allowedPaths = OBJECT_MAPPER.readValue(SpriteBuilder.class.getResourceAsStream("allowedPaths.json"),
                                                   new TypeReference<>() {});
        } catch (Exception e) {
            LOGGER.error("Failed to load allowed paths mapping file", e);
        }
        ALLOWED_PATHS = allowedPaths;
    }

    public File getSpriteSheetFile(String name) {
        return new File(String.format("%s/%s", SPRITE_FOLDER, name));
    }

    public File buildSpriteSheet(List<String> equipmentNames, String race) throws IOException {
        String gender = GENDERS.get(RANDOM.nextInt(2));

        SubmissionPublisher<Set<String>> publisher = new SubmissionPublisher<>(EXECUTOR, 10);
        CountDownLatch countDownLatch = new CountDownLatch(ALLOWED_PATHS.size());
        SpriteSubscriber spriteSubscriber = new SpriteSubscriber(countDownLatch);
        publisher.subscribe(spriteSubscriber);
        for (AllowedPaths allowedPaths : ALLOWED_PATHS) {
            Set<String> paths = evaluateAllowedPaths(allowedPaths, race, equipmentNames, gender);
            publisher.offer(paths,
                            200,
                            TimeUnit.MILLISECONDS,
                            (subscriber, strings) -> {
                                subscriber.onError(new RuntimeException("Dropped message"));
                                return false;
                            });
        }
        try {
            countDownLatch.await();
            publisher.close();
            return toFile(spriteSubscriber.getSpriteSheet());
        } catch (InterruptedException e) {
            Thread.currentThread()
                  .interrupt();
            return null;
        }
    }

    private File toFile(BufferedImage bufferedImage) throws IOException {
        File file = getSpriteSheetFile(UUID.randomUUID().toString());
        ImageIO.write(bufferedImage, "png", file);
        return file;
    }

    private Set<String> evaluateAllowedPaths(AllowedPaths allowedPaths,
                                              String race,
                                              List<String> equipmentNames,
                                              String gender) {
        // Treat conditions as ANDs
        for (Map<AllowedPaths.InfoAttribute, List<String>> condition : allowedPaths.getConditions()) {
            if (condition == null) {
                continue;
            }
            List<String> equipmentConditions = condition.get(AllowedPaths.InfoAttribute.EQUIPMENT);
            if (equipmentConditions != null && !CollectionUtils.containsAny(equipmentConditions, equipmentNames)) {
                return Collections.emptySet();
            }

            List<String> raceConditions = condition.get(AllowedPaths.InfoAttribute.RACE);
            if (raceConditions != null && !raceConditions.contains(race)) {
                return Collections.emptySet();
            }

            List<String> genderConditions = condition.get(AllowedPaths.InfoAttribute.GENDER);
            if (genderConditions != null && !genderConditions.contains(gender)) {
                return Collections.emptySet();
            }
        }
        Set<String> paths = new HashSet<>();
        for (Choice<String> pathChoice : allowedPaths.getChoices()) {
            paths.addAll(ChoiceUtils.makeRandomChoices(pathChoice));
        }
        return paths.stream()
                    .map(path -> path.replace("{{gender}}", gender))
                    .collect(Collectors.toSet());
    }

}
