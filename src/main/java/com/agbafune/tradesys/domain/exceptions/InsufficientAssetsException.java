package com.agbafune.tradesys.domain.exceptions;

public class InsufficientAssetsException extends RuntimeException {

    public InsufficientAssetsException(Long assetId) {
        super("Not enough quantity of asset for operation. Asset ID " + assetId + ".");
    }

}
