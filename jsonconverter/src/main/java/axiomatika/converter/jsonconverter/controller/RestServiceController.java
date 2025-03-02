package axiomatika.converter.jsonconverter.controller;

import axiomatika.converter.jsonconverter.service.RestJsonService;
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

    @PostMapping
    public String convert(@RequestBody String json) {
        return restJsonService.convertToXslt(json);
    }
}
