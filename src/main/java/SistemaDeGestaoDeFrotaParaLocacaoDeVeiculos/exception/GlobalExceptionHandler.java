//package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception;
//
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorResponde> handleEntityNotFound(EntityNotFoundException ex) {
//        ErrorResponde error = new ErrorResponde.Builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.NOT_FOUND.value())
//                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
//                .errors(Collections.singletonList(ex.getMessage()))
//                .build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponde> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ErrorResponde error = new ErrorResponde.Builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.BAD_REQUEST.value())
//                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                .errors(validationErrors)
//                .build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorResponde> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        ErrorResponde error = new ErrorResponde.Builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.CONFLICT.value())
//                .status(HttpStatus.CONFLICT.getReasonPhrase())
//                .errors(Collections.singletonList("Conflito de dados: A operação viola uma restrição do banco de dados."))
//                .build();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponde> handleGenericException(Exception ex) {
//        ErrorResponde error = new ErrorResponde.Builder()
//                .timestamp(LocalDateTime.now())
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
//                .errors(Collections.singletonList("Ocorreu um erro interno no servidor."))
//                .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
//
//
//}
