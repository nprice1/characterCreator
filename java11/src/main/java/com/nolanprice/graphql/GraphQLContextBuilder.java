package com.nolanprice.graphql;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nolanprice.CharacterInfoFactory;
import com.nolanprice.dnd.DndApiClient;

import graphql.com.google.common.collect.ImmutableList;
import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;

@Component
public class GraphQLContextBuilder implements GraphQLServletContextBuilder {

    public interface DataLoaders {
        String RACE = "raceLoader";
        String CHARACTER_CLASS = "characterClassLoader";
        String BACKGROUND = "backgroundLoader";
        String ALIGNMENT = "alignmentLoader";
        String STAT_ALLOTMENTS = "statAllotments";
    }

    private final DndApiClient dndApiClient;

    @Autowired
    public GraphQLContextBuilder(DndApiClient dndApiClient) {
        this.dndApiClient = dndApiClient;
    }

    @Override
    public GraphQLContext build() {
        return new DefaultGraphQLContext();
    }

    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return DefaultGraphQLServletContext.createServletContext()
                                           .with(httpServletRequest)
                                           .with(httpServletResponse)
                                           .with(buildDataLoaderRegistry())
                                           .build();
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
                                             .with(session)
                                             .with(handshakeRequest)
                                             .with(buildDataLoaderRegistry())
                                             .build();
    }

    private DataLoaderRegistry buildDataLoaderRegistry() {
        DataLoaderRegistry registry = new DataLoaderRegistry();

        registry.register(DataLoaders.RACE,
                          DataLoaderFactory.newDataLoader(createAsyncBatchLoader(dndApiClient::getRace)));
        registry.register(DataLoaders.CHARACTER_CLASS,
                          DataLoaderFactory.newDataLoader(createAsyncBatchLoader(dndApiClient::getCharacterClass)));
        registry.register(DataLoaders.BACKGROUND,
                          DataLoaderFactory.newDataLoader(createAsyncBatchLoader(dndApiClient::getBackground)));
        registry.register(DataLoaders.ALIGNMENT,
                          DataLoaderFactory.newDataLoader(createAsyncBatchLoader(dndApiClient::getAlignment)));
        registry.register(DataLoaders.STAT_ALLOTMENTS,
                          DataLoaderFactory.newDataLoader(createSyncBatchLoader(CharacterInfoFactory::getBaseStatAllotments)));
        return registry;
    }

    private <T> BatchLoader<String, T> createAsyncBatchLoader(Supplier<CompletableFuture<T>> supplier) {
        return (unused) -> supplier.get()
                                   .thenApply(result -> ImmutableList.of(result));
    }

    private <T> BatchLoader<String, T> createSyncBatchLoader(Supplier<T> supplier) {
        return (unused) -> CompletableFuture.completedFuture(ImmutableList.of(supplier.get()));
    }

}
