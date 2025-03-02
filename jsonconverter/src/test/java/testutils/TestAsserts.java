package testutils;

import org.json.JSONObject;
import org.json.XML;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAsserts {

    public static void assertTagsInXmlStringsAreEqualsWithoutOrder(String expected, String actual) {
        String normalizedExpectedXml = normalizeXml(expected);
        String normalizedActualXml = normalizeXml(actual);

        assertEquals(normalizedExpectedXml, normalizedActualXml);
    }

    private static String normalizeXml(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);

        String normalizedXml = XML.toString(jsonObject);

        return normalizedXml.replaceAll(">\\s*<", "><").trim();
    }
}
