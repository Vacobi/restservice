package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.config.TestContainersConfig;
import axiomatika.converter.jsonconverter.dto.ConvertToXsltResult;
import axiomatika.converter.jsonconverter.entity.Json;
import axiomatika.converter.jsonconverter.entity.Xslt;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static testutils.TestAsserts.assertTagsInXmlStringsAreEqualsWithoutOrder;

@SpringBootTest
@ContextConfiguration(initializers = TestContainersConfig.class)
class RestJsonServiceTest {

    private JsonRepository jsonRepository;

    @Autowired
    private RestJsonService restJsonService;

    @Test
    void convertCorrectJsonToXml() {
        String jsonString = """
                {
                    "name": "Тест",
                    "surname": "Тестов",
                    "patronymic": "Тестович",
                    "birthDate": "1990-01-01",
                    "gender": "MAN",
                    "document": {
                        "series": "1333",
                        "number": "112233",
                        "type": "PASSPORT",
                        "issueDate": "2020-01-01"
                    },
                }""";

        String expXml = """
                <person>
                    <name>Тест</name>
                    <surname>Тестов</surname>
                    <patronymic>Тестович</patronymic>
                    <birthDate>1990-01-01</birthDate>
                    <gender>MAN</gender>
                    <document>
                        <series>1333</series>
                        <number>112233</number>
                        <type>PASSPORT</type>
                        <issueDate>2020-01-01</issueDate>
                    </document>
                </person>""";

        ConvertToXsltResult actualConverted = restJsonService.convertToXslt(jsonString);

        // Content
        assertTagsInXmlStringsAreEqualsWithoutOrder(expXslt, actualConverted.getResult());

        assertTagsInXmlStringsAreEqualsWithoutOrder(expXml, actualXml);
    }
}
