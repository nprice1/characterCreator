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

import com.nolanprice.CharacterInfoFactory;
import com.nolanprice.model.CharacterInfo;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-04-26T17:45:10.977235Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.characterCreator.base-path:/rest/character-builder/v1}")
public class InfoApiController implements InfoApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoApiController.class);

    private final NativeWebRequest request;
    private final CharacterInfoFactory characterInfoFactory;

    @Autowired
    public InfoApiController(NativeWebRequest request, CharacterInfoFactory characterInfoFactory) {
        this.request = request;
        this.characterInfoFactory = characterInfoFactory;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<CharacterInfo> getInfo() {
        try {
            CharacterInfo characterInfo = characterInfoFactory.createCharacterInfo();
            return ResponseEntity.ok(characterInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to generate character", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
