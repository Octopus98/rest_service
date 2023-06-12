package com.example.util;

import com.example.config.Constant;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * This class provides static method to validate the JSON object. The JSON schema is specified in
 * <code>JsonSchema.SCHEMA_VALIDATION_NAME</code>.
 *
 * @see Constant
 * @version 1.0
 */
public class JsonSchemaValidator {

  private static String getSchemaAbsolutePath() throws URISyntaxException {
    URL resource = JsonSchemaValidator.class.getResource(Constant.SCHEMA_VALIDATION_NAME);
    assert resource != null;

    File file = Paths.get(resource.toURI()).toFile();

    return file.getAbsolutePath();
  }

  /**
   * Validate the JSON string based on the given JSON schema.
   *
   * @param jsonString string representation of a JSON object
   * @return <code>ProcessingReport</code> a wrapper of processing result.
   */
  public static ProcessingReport validate(String jsonString) {
    ProcessingReport report = null;
    final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
    try {
      JsonNodeReader jsonNodeReader = new JsonNodeReader();
      JsonNode node = jsonNodeReader.fromReader(new StringReader(jsonString));

      final JsonNode fstabSchema = JsonLoader.fromPath(getSchemaAbsolutePath());
      final JsonSchema schema = factory.getJsonSchema(fstabSchema);

      report = schema.validate(node);
    } catch (IOException | ProcessingException | URISyntaxException e) {
      e.printStackTrace();
    }

    return report;
  }
}
