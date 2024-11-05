/*
 * Copyright © 2024 MarkLogic Corporation. All Rights Reserved.
 */
package com.marklogic.flux.api;

import java.util.Map;

/**
 * @since 1.2.0
 */
public interface EmbedderOptions {

    /**
     * @param name the fully-qualified class name of an implementation of
     *             {@code java.util.Function<Map<String, String>, dev.langchain4j.model.embedding.EmbeddingModel>} or
     *             an abbreviation associated with the class name of an implementation.
     * @return
     */
    EmbedderOptions embedder(String name);

    EmbedderOptions chunksJsonPointer(String jsonPointer);

    EmbedderOptions textJsonPointer(String jsonPointer);

    EmbedderOptions chunksXPath(String xpath);

    EmbedderOptions textXPath(String xpath);

    EmbedderOptions embeddingName(String embeddingName);

    EmbedderOptions embeddingNamespace(String embeddingNamespace);

    EmbedderOptions embedderOptions(Map<String, String> options);
}
