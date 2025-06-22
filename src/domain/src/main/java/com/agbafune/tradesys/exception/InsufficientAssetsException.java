package com.agbafune.tradesys.exception;

public class InsufficientAssetsException extends RuntimeException {

    public InsufficientAssetsException(Long assetId) {
        super("Not enough quantity of asset for operation. Asset ID " + assetId + ".");
    }

}
