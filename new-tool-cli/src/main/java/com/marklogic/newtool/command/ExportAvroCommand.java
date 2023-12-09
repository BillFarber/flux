package com.marklogic.newtool.command;

import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Row;

public class ExportAvroCommand extends AbstractExportFilesCommand {

    // TODO Support Avro-specific params

    @Override
    protected DataFrameWriter configureWriter(DataFrameWriter<Row> writer) {
        return writer.format("avro");
    }
}
