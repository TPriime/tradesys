package com.agbafune.tradesys.domain.model;

import java.math.BigDecimal;

public record Asset(
        Long id,
        String name,
        String symbol,
        Integer quantity,
        BigDecimal price
) {
    public static class Builder {
        private Long assetId;
        private String name;
        private String symbol;
        private Integer quantity;
        private BigDecimal price;

        public Builder() { }

        public Builder (Asset asset) {
            this.assetId = asset.id();
            this.name = asset.name();
            this.symbol = asset.symbol();
            this.quantity = asset.quantity();
            this.price = asset.price();
        }

        public Builder id(Long assetId) {
            this.assetId = assetId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Asset build() {
            return new Asset(assetId, name, symbol, quantity, price);
        }
    }
}