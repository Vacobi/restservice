package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.config.TestContainersConfig;
import axiomatika.converter.jsonconverter.dto.ConvertRequestDto;
import axiomatika.converter.jsonconverter.dto.ConvertToXsltResult;
import axiomatika.converter.jsonconverter.entity.JsonEntity;
import axiomatika.converter.jsonconverter.entity.XsltEntity;
import axiomatika.converter.jsonconverter.exception.ClientExceptionName;
import axiomatika.converter.jsonconverter.exception.GroupValidationException;
import axiomatika.converter.jsonconverter.exception.IncorrectJsonException;
import axiomatika.converter.jsonconverter.exception.ValidationException;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import axiomatika.converter.jsonconverter.repository.XsltRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testutils.TestAsserts.assertTagsInXmlStringsAreEqualsWithoutOrder;

@SpringBootTest
@ContextConfiguration(initializers = TestContainersConfig.class)
class RestJsonServiceTest {

    // Перевод в XML не имеет смысл тестировать, т.к. он производится с помощью сторонней библиотеки

    @Autowired
    private JsonRepository jsonRepository;

    @Autowired
    private XsltRepository xsltRepository;

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

        String expXslt = """
                <person name="Тест" surname="Тестов" patronymic="Тестович" birthDate="01.01.1990" gender="MAN">\r
                    <document series="1333" number="112233" type="PASSPORT" issueDate="01.01.2020"/>\r
                </person>""";

        ConvertRequestDto convertRequestDto = new ConvertRequestDto(jsonString);
        ConvertToXsltResult actualConverted = restJsonService.convertToXslt(convertRequestDto);

        // Content
        assertTagsInXmlStringsAreEqualsWithoutOrder(expXslt, actualConverted.getResult());

        // Json Rep
        assertTrue(jsonRepository.findById(actualConverted.getJsonId()).isPresent());
        JsonEntity actualJson = jsonRepository.findById(actualConverted.getJsonId()).get();
        assertEquals(jsonString, actualJson.getContent());

        // Xslt Rep
        assertTrue(xsltRepository.findById(actualConverted.getXsltId()).isPresent());
        XsltEntity actualXslt = xsltRepository.findById(actualConverted.getXsltId()).get();
        assertEquals(expXslt, actualXslt.getContent());
    }

    @Test
    void jsonWithoutSeveralExpectedKeys() {
        String jsonString = """
                {
                    "name": "Тест",
                    "surname": "Тестов",
                    "birthDate": "1990-01-01",
                    "gender": "MAN",
                    "document": {
                        "series": "1333",
                        "type": "PASSPORT",
                        "issueDate": "2020-01-01"
                    },
                }""";

        String expXslt = """
                <person name="Тест" surname="Тестов" patronymic="" birthDate="01.01.1990" gender="MAN">\r
                    <document series="1333" number="" type="PASSPORT" issueDate="01.01.2020"/>\r
                </person>""";

        ConvertRequestDto convertRequestDto = new ConvertRequestDto(jsonString);
        ConvertToXsltResult actualConverted = restJsonService.convertToXslt(convertRequestDto);

        // Content
        assertTagsInXmlStringsAreEqualsWithoutOrder(expXslt, actualConverted.getResult());

        // Json Rep
        assertTrue(jsonRepository.findById(actualConverted.getJsonId()).isPresent());
        JsonEntity actualJson = jsonRepository.findById(actualConverted.getJsonId()).get();
        assertEquals(jsonString, actualJson.getContent());

        // Xslt Rep
        assertTrue(xsltRepository.findById(actualConverted.getXsltId()).isPresent());
        XsltEntity actualXslt = xsltRepository.findById(actualConverted.getXsltId()).get();
        assertEquals(expXslt, actualXslt.getContent());
    }

    @Test
    void jsonWithEmptyBody() {
        String jsonString = """
                {  
                }""";
        ConvertRequestDto convertRequestDto = new ConvertRequestDto(jsonString);


        ConvertToXsltResult actualConverted = restJsonService.convertToXslt(convertRequestDto);


        String expXslt = """
                <person name="" surname="" patronymic="" birthDate="" gender="">\r
                    <document series="" number="" type="" issueDate=""/>\r
                </person>""";
        // Content
        assertTagsInXmlStringsAreEqualsWithoutOrder(expXslt, actualConverted.getResult());
        // Json Rep
        assertTrue(jsonRepository.findById(actualConverted.getJsonId()).isPresent());
        JsonEntity actualJson = jsonRepository.findById(actualConverted.getJsonId()).get();
        assertEquals(jsonString, actualJson.getContent());
        // Xslt Rep
        assertTrue(xsltRepository.findById(actualConverted.getXsltId()).isPresent());
        XsltEntity actualXslt = xsltRepository.findById(actualConverted.getXsltId()).get();
        assertEquals(expXslt, actualXslt.getContent());
    }

    @Test
    void emptyStringInsteadOfJson() {
        String jsonString = "";
        ConvertRequestDto convertRequestDto = new ConvertRequestDto(jsonString);

        int beforeRequestInJsonRep = jsonRepository.findAll().size();
        int beforeRequestInXsltRep = xsltRepository.findAll().size();
        IncorrectJsonException incorrectJsonException = assertThrows(
                IncorrectJsonException.class,
                () -> {
                    restJsonService.convertToXslt(convertRequestDto);
                }
        );
        int afterRequestInJsonRep = jsonRepository.findAll().size();
        int afterRequestInXsltRep = xsltRepository.findAll().size();

        assertTrue(beforeRequestInJsonRep == afterRequestInJsonRep);
        assertTrue(beforeRequestInXsltRep == afterRequestInXsltRep);
    }

    @Test
    void jsonIsNull() {
        String jsonString = null;
        ConvertRequestDto convertRequestDto = new ConvertRequestDto(jsonString);

        int beforeRequestInJsonRep = jsonRepository.findAll().size();
        int beforeRequestInXsltRep = xsltRepository.findAll().size();
        GroupValidationException groupValidationException = assertThrows(
                GroupValidationException.class,
                () -> {
                    restJsonService.convertToXslt(convertRequestDto);
                }
        );
        int afterRequestInJsonRep = jsonRepository.findAll().size();
        int afterRequestInXsltRep = xsltRepository.findAll().size();

        assertTrue(beforeRequestInJsonRep == afterRequestInJsonRep);
        assertTrue(beforeRequestInXsltRep == afterRequestInXsltRep);
        List<ClientExceptionName> expected = List.of(
                ClientExceptionName.INCORRECT_JSON
        );
        List<ClientExceptionName> actual = groupValidationException.getExceptions().stream()
                .map(ValidationException::getExceptionName)
                .sorted(Comparator.comparing(Enum::ordinal))
                .toList();
        assertEquals(expected, actual);
    }

    @Test
    void convertRequestDtoIsNull() {
        ConvertRequestDto convertRequestDto = null;

        int beforeRequestInJsonRep = jsonRepository.findAll().size();
        int beforeRequestInXsltRep = xsltRepository.findAll().size();
        GroupValidationException groupValidationException = assertThrows(
                GroupValidationException.class,
                () -> {
                    restJsonService.convertToXslt(convertRequestDto);
                }
        );
        int afterRequestInJsonRep = jsonRepository.findAll().size();
        int afterRequestInXsltRep = xsltRepository.findAll().size();

        assertTrue(beforeRequestInJsonRep == afterRequestInJsonRep);
        assertTrue(beforeRequestInXsltRep == afterRequestInXsltRep);
        List<ClientExceptionName> expected = List.of(
                ClientExceptionName.INCORRECT_CONVERT_REQUEST
        );
        List<ClientExceptionName> actual = groupValidationException.getExceptions().stream()
                .map(ValidationException::getExceptionName)
                .sorted(Comparator.comparing(Enum::ordinal))
                .toList();
        assertEquals(expected, actual);
    }
}
