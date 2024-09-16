package ru.ushkalov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ushkalov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.ushkalov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.ushkalov.MySecondTestAppSpringBoot.model.*;
import ru.ushkalov.MySecondTestAppSpringBoot.service.ModifyResponceService;
import ru.ushkalov.MySecondTestAppSpringBoot.service.UnsupportedCodeService;
import ru.ushkalov.MySecondTestAppSpringBoot.service.ValidationService;
import util.DataTimeUtil;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final UnsupportedCodeService unsupportedCodeService;
    private final ModifyResponceService modifyResponceService;

    @Autowired
    public MyController(ValidationService validationService, UnsupportedCodeService unsupportedCodeService,
            @Qualifier("ModifySystemTimeResponceService") ModifyResponceService modifyResponceService){
        this.validationService = validationService;
        this.unsupportedCodeService = unsupportedCodeService;
        this.modifyResponceService = modifyResponceService;

    }
    @PostMapping("/feedback")
    public ResponseEntity<Responce> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        Responce responce = Responce.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DataTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        try {
            validationService.isValid(bindingResult);
            unsupportedCodeService.isSupported(Integer.parseInt(request.getUid()));
        } catch (ValidationFailedException e){
            responce.setCode(Codes.FAILED);
            responce.setErrorCode(ErrorCodes.VALIDATION_EXEPTION);
            responce.setErrorMessage(ErrorMessages.VALIDATION);
            return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            responce.setCode(Codes.FAILED);
            responce.setErrorCode(ErrorCodes.UNSUPPORTED_EXEPTION);
            responce.setErrorMessage(ErrorMessages.UNSUPPORTED);
            return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responce.setCode(Codes.FAILED);
            responce.setErrorCode(ErrorCodes.UNKNOWN_EXEPTION);
            responce.setErrorMessage(ErrorMessages.UNKNOWN);
            return new ResponseEntity<>(responce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponceService.modify(responce);
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }
}
