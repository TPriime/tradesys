package com.agbafune.tradesys.domain.exceptions;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(Long assetId) {
        super("Asset with ID " + assetId + " not found.");
    }

    public AssetNotFoundException(String assetSymbol) {
        super("Asset with symbol '" + assetSymbol + "' not found.");
    }
}
