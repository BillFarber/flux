package com.marklogic.newtool.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.marklogic.newtool.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ImportAvroFilesTest extends AbstractTest {

    @Test
    void defaultSettingsMultipleFiles() {
        run(
            "import_avro_files",
            "--path", "src/test/resources/avro/*",
            "--clientUri", makeClientUri(),
            "--permissions", DEFAULT_PERMISSIONS,
            "--collections", "avro-test",
            "--uriTemplate", "/avro/{color}.json"
        );

        assertCollectionSize("avro-test", 6);
        verifyColorDoc("/avro/blue.json", "1", "blue", true);
        verifyColorDoc("/avro/red.json", "2", "red", false);
        verifyColorDoc("/avro/green.json", "3", "green", true);
        verifyColorDoc("/avro/puce.json", "4", "puce", true);
        verifyColorDoc("/avro/ecru.json", "5", "ecru", false);
        verifyColorDoc("/avro/mauve.json", "6", "mauve", true);
    }

    @Test
    void ignoreExtension() {
        run(
            "import_avro_files",
            "--path", "src/test/resources/avro/*",
            "--clientUri", makeClientUri(),
            "--permissions", DEFAULT_PERMISSIONS,
            "--collections", "avro-test",
            "-PignoreExtension=false",
            "--uriTemplate", "/avro/{color}.json"
        );

        assertCollectionSize("avro-test", 3);
        verifyColorDoc("/avro/blue.json", "1", "blue", true);
        verifyColorDoc("/avro/red.json", "2", "red", false);
        verifyColorDoc("/avro/green.json", "3", "green", true);
    }

    private void verifyColorDoc(String uri, String expectedNumber, String expectedColor, boolean expectedFlag) {
        JsonNode doc = readJsonDocument(uri);

        assertEquals(expectedNumber, doc.get("number").asText());
        assertEquals(JsonNodeType.STRING, doc.get("number").getNodeType());

        assertEquals(expectedColor, doc.get("color").asText());
        assertEquals(JsonNodeType.STRING, doc.get("color").getNodeType());

        assertEquals(expectedFlag, doc.get("flag").asBoolean());
        assertEquals(JsonNodeType.BOOLEAN, doc.get("flag").getNodeType());
    }
}