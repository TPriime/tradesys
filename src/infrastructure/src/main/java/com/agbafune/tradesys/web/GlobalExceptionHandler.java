package com.agbafune.tradesys.web;

import com.agbafune.tradesys.exception.ConflictException;
import com.agbafune.tradesys.exception.InsufficientAssetsException;
import com.agbafune.tradesys.exception.InsufficientFundsException;
import com.agbafune.tradesys.exception.ModelNotFoundException;
import com.agbafune.tradesys.exception.PortfolioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    record ErrorResponse(String message) {
    }

     @ExceptionHandler(ModelNotFoundException.class)
     public ResponseEntity<ErrorResponse> handleAssetNotFound(ModelNotFoundException ex) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
     }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePortfolioNotFound(PortfolioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInsufficientAsset(InsufficientAssetsException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("An unexpected error occurred: " + ex.getMessage()));
    }
}
