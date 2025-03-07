package br.com.fps.portfolio.demoparkapi.web.exceptions;

import br.com.fps.portfolio.demoparkapi.config.constants.Messages;
import br.com.fps.portfolio.demoparkapi.exception.EntityNotFoundException;
import br.com.fps.portfolio.demoparkapi.exception.PasswordInvalidException;
import br.com.fps.portfolio.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // ouvinte
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request,
                                                                      BindingResult result){

    log.error("Api Error - ", ex);
    return ResponseEntity
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, Messages.CAMPOS_INVALIDOS, result));
  }

@ExceptionHandler(UsernameUniqueViolationException.class)
  public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException ex, HttpServletRequest request){

    log.error("Api Error - ", ex);
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
  }

@ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex, HttpServletRequest request){

    log.error("Api Error - ", ex);
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
  }

@ExceptionHandler(PasswordInvalidException.class)
  public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request){

    log.error("Api Error - ", ex);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
  }






}
