package com.nolanprice.graphql;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nolanprice.CharacterInfoFactory;
import com.nolanprice.model.CharacterInfo;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class NewCharacterResolver implements GraphQLQueryResolver {

    private final CharacterInfoFactory characterInfoFactory;

    @Autowired
    public NewCharacterResolver(CharacterInfoFactory characterInfoFactory) {
        this.characterInfoFactory = characterInfoFactory;
    }

    public CompletableFuture<CharacterInfo> newCharacter(DataFetchingEnvironment dataFetchingEnvironment) {
        return CompletableFuture.supplyAsync(CharacterInfo::new);
    }

}
