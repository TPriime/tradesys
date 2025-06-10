package com.agbafune.tradesys.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public record Portfolio(
        Long id,
        Long userId,
        ArrayList<PrtfAsset> assets
) {
    public BigDecimal value() {
        return assets.stream()
                .map(asset -> asset.getAsset().price().multiply(asset.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addAsset(Asset asset, BigDecimal quantity) {
        assets().stream()
                .filter(prtfAsset -> prtfAsset.getAsset().id().equals(asset.id()))
                .findFirst()
                .ifPresentOrElse(
                        prtfAsset -> prtfAsset.setQuantity(prtfAsset.getQuantity().add(quantity)),
                        () -> assets().add(new Portfolio.PrtfAsset(asset, quantity)));
    }

    public void removeAsset(Asset asset, BigDecimal quantity) {
        assets().stream()
                .filter(prtfAsset -> prtfAsset.getAsset().id().equals(asset.id()))
                .findFirst()
                .ifPresent(prtfAsset -> {
                    BigDecimal newQuantity = prtfAsset.getQuantity().subtract(quantity);
                    if (newQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                        assets().remove(prtfAsset);
                    } else {
                        prtfAsset.setQuantity(newQuantity);
                    }
                });
    }

    public class PrtfAsset {

        private final Asset asset;
        private BigDecimal quantity;

        private PrtfAsset(Asset asset, BigDecimal quantity) {
            this.asset = asset;
            this.quantity = quantity;
        }

        public Asset getAsset() {
            return asset;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }
    }
}