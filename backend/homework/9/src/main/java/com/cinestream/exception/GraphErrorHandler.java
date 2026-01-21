package com.cinestream.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphErrorHandler extends DataFetcherExceptionResolverAdapter {

    // added a logger to keep track of what goes wrong
    private static final Logger log = LoggerFactory.getLogger(GraphErrorHandler.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        // i'm checking if this is a known error so i can give a clear message to the user
        if (ex instanceof MissingItemException) {
            log.warn("client error: {}", ex.getMessage());

            // constructing a clean error response here so the client knows exactly what happened
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return null;
    }
}