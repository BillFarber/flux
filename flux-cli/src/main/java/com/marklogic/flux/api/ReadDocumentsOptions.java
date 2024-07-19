/*
 * Copyright © 2024 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
 */
package com.marklogic.flux.api;

public interface ReadDocumentsOptions<T extends ReadDocumentsOptions> {

    T stringQuery(String stringQuery);

    T uris(String... uris);

    T query(String query);

    T options(String options);

    T collections(String... collections);

    T directory(String directory);

    T transform(String transform);

    T transformParams(String delimitedNamesAndValues);

    T transformParamsDelimiter(String delimiter);

    T batchSize(int batchSize);

    T partitionsPerForest(int partitionsPerForest);
}
