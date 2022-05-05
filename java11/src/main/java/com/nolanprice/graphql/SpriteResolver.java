package com.nolanprice.graphql;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nolanprice.sprite.SpriteBuilder;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class SpriteResolver implements GraphQLQueryResolver {

    private final SpriteBuilder spriteBuilder;

    @Autowired
    public SpriteResolver(SpriteBuilder spriteBuilder) {
        this.spriteBuilder = spriteBuilder;
    }

    public CompletableFuture<byte[]> sprites(List<String> equipment, String race) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return spriteBuilder.buildSpriteSheet(equipment, race);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
