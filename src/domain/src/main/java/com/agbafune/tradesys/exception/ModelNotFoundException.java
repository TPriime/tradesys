package com.agbafune.tradesys.exception;

public class ModelNotFoundException extends RuntimeException {

    public ModelNotFoundException(String modelName, Long id) {
        super(modelName + " with ID " + id + " not found.");
    }
}
