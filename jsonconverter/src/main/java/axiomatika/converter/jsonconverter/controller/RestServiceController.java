package axiomatika.converter.jsonconverter.controller;

import axiomatika.converter.jsonconverter.dto.ConvertResponseDto;
import axiomatika.converter.jsonconverter.dto.ConvertRequestDto;
import axiomatika.converter.jsonconverter.dto.ConvertToXsltResult;
import axiomatika.converter.jsonconverter.service.RestJsonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Rest Controller",
        description = "Controller for processing JSONs"
)
@RestController
@RequestMapping("/api/v1/converter")
public class RestServiceController {

    private final RestJsonService restJsonService;

    public RestServiceController(RestJsonService restJsonService) {
        this.restJsonService = restJsonService;
    }

    @Operation(
            summary = "Converting JSON",
            description = "Converting JSON to XML using XSLT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "JSON successfully converted.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                                            {
                                                "data": "<person name=\\"Тест\\" surname=\\"Тестов\\" patronymic=\\"Тестович\\" birthDate=\\"01.01.1990\\" gender=\\"MAN\\">\\r\\n    <document series=\\"1333\\" number=\\"112233\\" type=\\"PASSPORT\\" issueDate=\\"01.01.2020\\"/>\\r\\n</person>"
                                            }"""
                            )
                    )),
            @ApiResponse(responseCode = "400",
                    description = """
                    JSON wasn't converted. It may be \s
                    Group of validation exceptions (api error code 801), \s
                    Validation exception: Incorrect JSON (api error code 802), \s
                    Validation exception: Incorrect convert request (api error code 803)""",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                                            {
                                                 "type": "error",
                                                 "title": "Bad Request",
                                                 "status": 400,
                                                 "detail": "Group validation exception.",
                                                 "instance": "/api/v1/converter",
                                                 "date": "2025-03-05T15:59:33.6777991",
                                                 "api_error_code": 801,
                                                 "api_error_name": "GROUP_VALIDATION_EXCEPTION",
                                                 "args": {},
                                                 "errors": [
                                                     {
                                                         "api_error_code": 802,
                                                         "api_error_name": "INCORRECT_JSON",
                                                         "args": {},
                                                         "detail": "Json can't be null"
                                                     }
                                                 ]
                                             }"""
                            )
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error. It may be troubles with " +
                    "connect to SOAP service, or internal SOAP service error")
    })
    @PostMapping
    public ConvertResponseDto convert(@RequestBody ConvertRequestDto convertRequestDto) {
        ConvertToXsltResult converted = restJsonService.convertToXslt(convertRequestDto);
        return new ConvertResponseDto(converted.getResult());
    }
}
