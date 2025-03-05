package axiomatika.converter.jsonconverter.validation;

import axiomatika.converter.jsonconverter.dto.ConvertRequestDto;
import axiomatika.converter.jsonconverter.exception.ClientExceptionName;
import axiomatika.converter.jsonconverter.exception.GroupValidationException;
import axiomatika.converter.jsonconverter.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class JsonValidator {

    public Optional<GroupValidationException> validateConvertRequestDto(ConvertRequestDto convertRequestDto) {

        List<ValidationException> exceptions = new LinkedList<>();

        if (convertRequestDto == null) {
            exceptions.add(new ValidationException("Convert request can't be null", ClientExceptionName.INCORRECT_CONVERT_REQUEST));
        }

        if (convertRequestDto != null && convertRequestDto.getData() == null) {
            exceptions.add(new ValidationException("Json can't be null", ClientExceptionName.INCORRECT_JSON));
        }

        return exceptions.isEmpty() ? Optional.empty() : Optional.of(new GroupValidationException(exceptions));
    }
}
