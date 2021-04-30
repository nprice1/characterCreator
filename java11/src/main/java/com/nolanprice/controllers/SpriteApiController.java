package com.nolanprice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

import com.nolanprice.model.CharacterInfo;
import com.nolanprice.sprite.SpriteBuilder;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-04-26T17:45:10.977235Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.characterCreator.base-path:/character-builder/v1}")
public class SpriteApiController implements SpriteApi {

    private static Logger LOGGER = LoggerFactory.getLogger(SpriteApiController.class);

    private final NativeWebRequest request;
    private final SpriteBuilder spriteBuilder;

    @Autowired
    public SpriteApiController(NativeWebRequest request, SpriteBuilder spriteBuilder) {
        this.request = request;
        this.spriteBuilder = spriteBuilder;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<byte[]> getSpriteSheet(CharacterInfo characterInfo) {
        try {
            return ResponseEntity.ok(spriteBuilder.buildSpriteSheet(characterInfo));
        } catch (Exception e) {
            LOGGER.error("Failed to generate sprite sheet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
