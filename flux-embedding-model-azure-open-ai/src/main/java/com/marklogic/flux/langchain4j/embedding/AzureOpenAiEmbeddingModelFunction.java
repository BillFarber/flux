/*
 * Copyright © 2024 MarkLogic Corporation. All Rights Reserved.
 */
package com.marklogic.flux.langchain4j.embedding;

import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

public class AzureOpenAiEmbeddingModelFunction implements Function<Map<String, String>, EmbeddingModel> {

    @Override
    public EmbeddingModel apply(Map<String, String> options) {
        // See https://docs.langchain4j.dev/integrations/embedding-models/azure-open-ai/#spring-boot-1 for reference
        // of all properties that should be configurable.
        if (!options.containsKey("api-key") && !options.containsKey("non-azure-api-key")) {
            throw new IllegalArgumentException("Must specify either api-key or non-azure-api-key.");
        }

        AzureOpenAiEmbeddingModel.Builder builder = AzureOpenAiEmbeddingModel.builder()
            .apiKey(options.get("api-key"))
            .deploymentName(options.get("deployment-name"))
            .endpoint(options.get("endpoint"))
            .dimensions(getInteger(options, "dimensions"))
            .maxRetries(getInteger(options, "max-retries"));

        if (options.containsKey("non-azure-api-key")) {
            builder.nonAzureApiKey(options.get("non-azure-api-key"));
        }

        if (options.containsKey("log-requests-and-responses")) {
            builder.logRequestsAndResponses(Boolean.parseBoolean(options.get("log-requests-and-responses")));
        }

        if (options.containsKey("duration")) {
            builder.timeout(Duration.ofSeconds(getInteger(options, "duration")));
        }

        return builder.build();
    }

    private Integer getInteger(Map<String, String> options, String key) {
        if (options.containsKey(key)) {
            try {
                return Integer.parseInt(options.get(key));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("%s must have a numeric value; invalid value: %s",
                    key, options.get(key)));
            }
        }
        return null;
    }
}
