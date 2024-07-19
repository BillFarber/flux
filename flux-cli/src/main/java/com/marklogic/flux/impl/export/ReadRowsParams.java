/*
 * Copyright © 2024 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
 */
package com.marklogic.flux.impl.export;

import com.marklogic.flux.api.ReadRowsOptions;
import com.marklogic.flux.impl.OptionsUtil;
import com.marklogic.spark.Options;
import picocli.CommandLine;

import java.util.Map;

/**
 * Defines parameters for reading rows via an Optic query.
 */
public class ReadRowsParams implements ReadRowsOptions {

    @CommandLine.Option(
        names = "--query",
        description = "The Optic DSL query for retrieving rows; must use op.fromView as an accessor.",
        required = true
    )
    private String query;

    @CommandLine.Option(names = "--batch-size", description = "Approximate number of rows to retrieve in each call to MarkLogic; defaults to 100000.")
    private int batchSize;

    // Not yet showing this in usage as it is confusing for a typical user to understand and would only need to be
    // set if push down aggregation is producing incorrect results. See the MarkLogic Spark connector documentation
    // for more information.
    @CommandLine.Option(names = "--disable-aggregation-push-down", hidden = true)
    private boolean disableAggregationPushDown;

    @CommandLine.Option(names = "--partitions", description = "Number of partitions to create when reading rows from MarkLogic. " +
        "Increasing this may improve performance as the number of rows to read increases.")
    private int partitions;

    public Map<String, String> makeOptions() {
        return OptionsUtil.makeOptions(
            Options.READ_OPTIC_QUERY, query,
            Options.READ_BATCH_SIZE, OptionsUtil.intOption(batchSize),
            Options.READ_PUSH_DOWN_AGGREGATES, disableAggregationPushDown ? "true" : null,
            Options.READ_NUM_PARTITIONS, OptionsUtil.intOption(partitions)
        );
    }

    @Override
    public ReadRowsOptions opticQuery(String opticQuery) {
        this.query = opticQuery;
        return this;
    }

    @Override
    public ReadRowsOptions disableAggregationPushDown(boolean value) {
        this.disableAggregationPushDown = value;
        return this;
    }

    @Override
    public ReadRowsOptions batchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    @Override
    public ReadRowsOptions partitions(int partitions) {
        this.partitions = partitions;
        return this;
    }
}
