package com.marklogic.newtool.api;

import java.util.function.Consumer;

public interface ParquetFilesExporter extends Executor<ParquetFilesExporter> {

    ParquetFilesExporter readRows(Consumer<ReadRowsOptions> consumer);

    ParquetFilesExporter writeFiles(Consumer<WriteSparkFilesOptions> consumer);
}