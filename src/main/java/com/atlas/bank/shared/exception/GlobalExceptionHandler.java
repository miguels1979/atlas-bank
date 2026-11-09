package com.atlas.bank.shared.exception;

import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.transaction.service.exception.FraudCheckException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(AccountNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
        problem.setTitle("Cuenta no encontrada");
        return problem;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFundsException(InsufficientFundsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422), ex.getMessage()
        );
        problem.setTitle("Saldo insuficiente");
        return problem;
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ProblemDetail handleAccountNotActiveException(AccountNotActiveException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422), ex.getMessage()
        );
        problem.setTitle("Cuenta no activa");
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();
        log.error("[{}] Error no controlado en {} {}", errorId, request.getMethod(), request.getRequestURI());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
               HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"
        );
        problem.setTitle("Error Interno");
        problem.setProperty("errorId", errorId);
        return problem;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");
        List<Map<String, String>> errors =  ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    return Map.of(
                            error.getField(),
                            Objects.requireNonNullElse(error.getDefaultMessage(), "Valor inválido")
                    );
                })
                .toList();
        problem.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(FraudCheckException.class)
    public ProblemDetail handleFraudCheckException(FraudCheckException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422), ex.getMessage()
        );
        problem.setTitle("Operación bloqueada por fraude");
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Datos inválidos");
        return problem;

    }

}
