/*
 * Copyright © 2024 Progress Software Corporation and/or its subsidiaries or affiliates. All Rights Reserved.
 */
package com.marklogic.flux.api;

public interface ReadRowsOptions {

    ReadRowsOptions opticQuery(String opticQuery);

    ReadRowsOptions disableAggregationPushDown(Boolean value);

    ReadRowsOptions batchSize(Integer batchSize);

    ReadRowsOptions partitions(Integer partitions);
}
