/*
 * Copyright © 2025 MarkLogic Corporation. All Rights Reserved.
 */
package com.marklogic.flux.cli;

import com.marklogic.flux.impl.Command;
import picocli.CommandLine;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public abstract class PicoliUtil {

    public static Command getCommandInstance(@NotNull CommandLine.ParseResult parseResult) {
        CommandLine.ParseResult subcommand = parseResult.subcommand();
        Objects.requireNonNull(subcommand);
        CommandLine.Model.CommandSpec commandSpec = subcommand.commandSpec();
        Objects.requireNonNull(commandSpec);
        return (Command) commandSpec.userObject();
    }

    private PicoliUtil() {
    }
}
