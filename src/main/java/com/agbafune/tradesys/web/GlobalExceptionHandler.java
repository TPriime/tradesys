package com.agbafune.tradesys.web;

import com.agbafune.tradesys.domain.exceptions.AssetNotFoundException;
import com.agbafune.tradesys.domain.exceptions.InsufficientFundsException;
import com.agbafune.tradesys.domain.exceptions.PortfolioNotFoundException;
import com.agbafune.tradesys.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    record ErrorResponse(String message) {
    }

     @ExceptionHandler(AssetNotFoundException.class)
     public ResponseEntity<ErrorResponse> handleAssetNotFound(AssetNotFoundException ex) {
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
}
