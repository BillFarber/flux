/*
 * Copyright © 2024 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
 */
package com.marklogic.flux.impl.importdata;

import com.marklogic.flux.api.WriteDocumentsOptions;
import com.marklogic.flux.impl.OptionsUtil;
import com.marklogic.spark.Options;
import picocli.CommandLine;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Defines all basic params for writing documents. Does not include support for a URI template, as that is not always
 * relevant nor possible depending on what kind of data is being imported.
 */
public class WriteDocumentParams<T extends WriteDocumentsOptions> implements WriteDocumentsOptions<T>, Supplier<Map<String, String>> {

    @CommandLine.Option(
        names = "--abort-on-write-failure",
        description = "Causes the command to fail when a batch of documents cannot be written to MarkLogic."
    )
    private boolean abortOnWriteFailure;

    @CommandLine.Option(
        names = "--batch-size",
        description = "The maximum number of documents written in a single call to MarkLogic."
    )
    private int batchSize = 200;

    @CommandLine.Option(
        names = "--collections",
        description = "Comma-delimited sequence of collection names to add to each document - e.g. collection1,collection2."
    )
    private String collections;

    @CommandLine.Option(
        names = "--failed-documents-path",
        description = "File path for writing an archive file containing failed documents and their metadata. " +
            "The documents in the archive file can then be retried via the 'import-archive-files' command."
    )
    private String failedDocumentsPath;

    @CommandLine.Option(
        names = "--log-progress",
        description = "Log a count of documents written every time this many documents are written."
    )
    private int logProgress = 10000;

    @CommandLine.Option(
        names = "--permissions",
        description = "Comma-delimited sequence of MarkLogic role names and capabilities to add to each document - e.g. role1,read,role2,update,role3,execute."
    )
    private String permissions;

    @CommandLine.Option(
        names = "--temporal-collection",
        description = "Name of a temporal collection to assign to each document."
    )
    private String temporalCollection;

    @CommandLine.Option(
        names = "--thread-count",
        description = "The total number of threads used across all partitions when writing batches of documents to MarkLogic."
    )
    private int threadCount = 4;

    @CommandLine.Option(
        names = "--thread-count-per-partition",
        description = "The number of threads used by each partition worker when writing batches of documents to MarkLogic. " +
            "Takes precedence over the '--thread-count' option."
    )
    private int threadCountPerPartition;

    @CommandLine.Option(
        names = "--transform",
        description = "Name of a MarkLogic REST API transform to apply to each document."
    )
    private String transform;

    @CommandLine.Option(
        names = "--transform-params",
        description = "Comma-delimited sequence of REST API transform parameter names and values - e.g. param1,value1,param2,value2."
    )
    private String transformParams;

    @CommandLine.Option(
        names = "--transform-params-delimiter",
        description = "Delimiter to use instead of a comma for the '--transform-params' parameter."
    )
    private String transformParamsDelimiter;

    @CommandLine.Option(
        names = "--uri-prefix",
        description = "String to prepend to each document URI."
    )
    private String uriPrefix;

    @CommandLine.Option(
        names = "--uri-replace",
        description = "Comma-delimited sequence of regular expressions and replacement strings for modifying " +
            "the URI of each document - e.g. regex,'value',regex,'value'. " +
            "Each replacement string must be enclosed by single quotes."
    )
    private String uriReplace;

    @CommandLine.Option(
        names = "--uri-suffix",
        description = "String to append to each document URI."
    )
    private String uriSuffix;

    @CommandLine.Option(
        names = "--uri-template",
        description = "Defines a template for constructing each document URI. " +
            "See the Flux user guide on controlling document URIs for more information."
    )
    private String uriTemplate;

    public Map<String, String> makeOptions() {
        return OptionsUtil.makeOptions(
            Options.WRITE_ABORT_ON_FAILURE, abortOnWriteFailure ? "true" : "false",
            Options.WRITE_ARCHIVE_PATH_FOR_FAILED_DOCUMENTS, failedDocumentsPath,
            Options.WRITE_BATCH_SIZE, OptionsUtil.intOption(batchSize),
            Options.WRITE_COLLECTIONS, collections,
            Options.WRITE_LOG_PROGRESS, OptionsUtil.intOption(logProgress),
            Options.WRITE_PERMISSIONS, permissions,
            Options.WRITE_TEMPORAL_COLLECTION, temporalCollection,
            Options.WRITE_THREAD_COUNT, OptionsUtil.intOption(threadCount),
            Options.WRITE_THREAD_COUNT_PER_PARTITION, OptionsUtil.intOption(threadCountPerPartition),
            Options.WRITE_TRANSFORM_NAME, transform,
            Options.WRITE_TRANSFORM_PARAMS, transformParams,
            Options.WRITE_TRANSFORM_PARAMS_DELIMITER, transformParamsDelimiter,
            Options.WRITE_URI_PREFIX, uriPrefix,
            Options.WRITE_URI_REPLACE, uriReplace,
            Options.WRITE_URI_SUFFIX, uriSuffix,
            Options.WRITE_URI_TEMPLATE, uriTemplate
        );
    }

    @Override
    public Map<String, String> get() {
        return makeOptions();
    }

    @Override
    public T abortOnWriteFailure(boolean value) {
        this.abortOnWriteFailure = value;
        return (T) this;
    }

    @Override
    public T batchSize(int batchSize) {
        this.batchSize = batchSize;
        return (T) this;
    }

    @Override
    public T collections(String... collections) {
        this.collections = Stream.of(collections).collect(Collectors.joining(","));
        return (T) this;
    }

    @Override
    public T collectionsString(String commaDelimitedCollections) {
        this.collections = commaDelimitedCollections;
        return (T) this;
    }

    @Override
    public T failedDocumentsPath(String path) {
        this.failedDocumentsPath = path;
        return (T) this;
    }

    @Override
    public T logProgress(int numberOfDocuments) {
        this.logProgress = numberOfDocuments;
        return (T) this;
    }

    @Override
    public T permissionsString(String rolesAndCapabilities) {
        this.permissions = rolesAndCapabilities;
        return (T) this;
    }

    @Override
    public T temporalCollection(String temporalCollection) {
        this.temporalCollection = temporalCollection;
        return (T) this;
    }

    @Override
    public T threadCount(int threadCount) {
        this.threadCount = threadCount;
        return (T) this;
    }

    @Override
    public T threadCountPerPartition(int threadCountPerPartition) {
        this.threadCountPerPartition = threadCountPerPartition;
        return (T) this;
    }

    @Override
    public T transform(String transform) {
        this.transform = transform;
        return (T) this;
    }

    @Override
    public T transformParams(String delimitedNamesAndValues) {
        this.transformParams = delimitedNamesAndValues;
        return (T) this;
    }

    @Override
    public T transformParamsDelimiter(String delimiter) {
        this.transformParamsDelimiter = delimiter;
        return (T) this;
    }

    @Override
    public T uriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
        return (T) this;
    }

    @Override
    public T uriReplace(String uriReplace) {
        this.uriReplace = uriReplace;
        return (T) this;
    }

    @Override
    public T uriSuffix(String uriSuffix) {
        this.uriSuffix = uriSuffix;
        return (T) this;
    }

    @Override
    public T uriTemplate(String uriTemplate) {
        this.uriTemplate = uriTemplate;
        return (T) this;
    }
}
