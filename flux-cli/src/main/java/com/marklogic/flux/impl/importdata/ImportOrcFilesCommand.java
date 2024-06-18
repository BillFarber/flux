/*
 * Copyright © 2024 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
 */
package com.marklogic.flux.impl.importdata;

import com.marklogic.flux.api.OrcFilesImporter;
import com.marklogic.flux.api.ReadTabularFilesOptions;
import com.marklogic.flux.api.WriteStructuredDocumentsOptions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@CommandLine.Command(
    name = "import-orc-files",
    abbreviateSynopsis = true,
    description = "Read ORC files from local, HDFS, and S3 locations using Spark's support " +
    "defined at https://spark.apache.org/docs/latest/sql-data-sources-orc.html, with each row being " +
    "written as a JSON or XML document in MarkLogic."
)
public class ImportOrcFilesCommand extends AbstractImportFilesCommand<OrcFilesImporter> implements OrcFilesImporter {

    @CommandLine.ArgGroup(exclusive = false, heading = READER_OPTIONS_HEADING, multiplicity = "1")
    private ReadOrcFilesParams readParams = new ReadOrcFilesParams();

    @CommandLine.ArgGroup(exclusive = false, heading = WRITER_OPTIONS_HEADING)
    private WriteStructuredDocumentParams writeParams = new WriteStructuredDocumentParams();

    @Override
    protected String getReadFormat() {
        return "orc";
    }

    @Override
    protected ReadFilesParams getReadParams() {
        return readParams;
    }

    @Override
    protected WriteDocumentParams getWriteParams() {
        return writeParams;
    }

    public static class ReadOrcFilesParams extends ReadFilesParams<ReadTabularFilesOptions> implements ReadTabularFilesOptions {

        @CommandLine.Option(
            names = "-P",
            description = "Specify any Spark ORC data source option defined at " +
                "https://spark.apache.org/docs/latest/sql-data-sources-orc.html; e.g. -PmergeSchema=true. " +
                "Spark configuration options must be defined via '-C'."
        )
        private Map<String, String> additionalOptions = new HashMap<>();

        @CommandLine.ArgGroup(exclusive = false)
        private AggregationParams aggregationParams = new AggregationParams();

        @Override
        public Map<String, String> makeOptions() {
            Map<String, String> options = super.makeOptions();
            options.putAll(additionalOptions);
            return options;
        }

        @Override
        public ReadTabularFilesOptions additionalOptions(Map<String, String> options) {
            this.additionalOptions = options;
            return this;
        }

        @Override
        public ReadTabularFilesOptions groupBy(String columnName) {
            aggregationParams.setGroupBy(columnName);
            return this;
        }

        @Override
        public ReadTabularFilesOptions aggregateColumns(String newColumnName, String... columns) {
            aggregationParams.addAggregationExpression(newColumnName, columns);
            return this;
        }
    }

    @Override
    protected Dataset<Row> afterDatasetLoaded(Dataset<Row> dataset) {
        return readParams.aggregationParams.applyGroupBy(dataset);
    }

    @Override
    public OrcFilesImporter from(Consumer<ReadTabularFilesOptions> consumer) {
        consumer.accept(readParams);
        return this;
    }

    @Override
    public OrcFilesImporter from(String... paths) {
        readParams.paths(paths);
        return this;
    }

    @Override
    public OrcFilesImporter to(Consumer<WriteStructuredDocumentsOptions> consumer) {
        consumer.accept(writeParams);
        return this;
    }
}
