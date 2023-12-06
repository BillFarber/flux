package com.marklogic.newtool;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecuteCustomCommandTest {

    @Test
    void test() {
        Main.main(new String[]{
            "custom",
            "--class-name", "com.marklogic.newtool.TestCustomCommand",
            "-Pparam1=value1",
            "-Pparam2=value2"
        });

        Map<String, String> params = TestCustomCommand.getDynamicParameters();
        assertEquals("value1", params.get("param1"));
        assertEquals("value2", params.get("param2"));
    }
}
