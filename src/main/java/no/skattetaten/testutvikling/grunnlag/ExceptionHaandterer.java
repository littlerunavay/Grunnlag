package no.skattetaten.testutvikling.grunnlag;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHaandterer {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> haandterValideringExceptions(MethodArgumentNotValidException e) {
        Map<String, String> alleFeil = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(feil -> {
            String feltNavn = ((FieldError) feil).getField();
            String melding = feil.getDefaultMessage();
            alleFeil.put(feltNavn, melding);
        });
        return new ResponseEntity<>(alleFeil, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> haandterValideringExceptions(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> haandterIkkeFunnetExceptions(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
