package com.agbafune.tradesys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeData(
        Long id,
        Long userId,
        Long assetId,
        String assetName,
        String assetSymbol,
        BigDecimal assetPrice,
        String action,
        BigDecimal quantity,
        BigDecimal total,
        LocalDateTime timestamp
) {

    public static class Builder {
        private Long id;
        private Long userId;
        private Long assetId;
        private String assetName;
        private String assetSymbol;
        private BigDecimal assetPrice;
        private String action;
        private BigDecimal quantity;
        private BigDecimal total;
        private LocalDateTime timestamp;

        public Builder(){}

        public Builder(TradeData trade) {
            this.id = trade.id;
            this.userId = trade.userId;
            this.assetId = trade.assetId;
            this.assetName = trade.assetName;
            this.assetSymbol = trade.assetSymbol;
            this.assetPrice = trade.assetPrice;
            this.action = trade.action;
            this.quantity = trade.quantity;
            this.total = trade.total;
            this.timestamp = trade.timestamp;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder assetId(Long assetId) {
            this.assetId = assetId;
            return this;
        }

        public Builder assetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public Builder assetSymbol(String assetSymbol) {
            this.assetSymbol = assetSymbol;
            return this;
        }

        public Builder assetPrice(BigDecimal assetPrice) {
            this.assetPrice = assetPrice;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TradeData build() {
            return new TradeData(id, userId, assetId, assetName, assetSymbol, assetPrice, action, quantity, total, timestamp);
        }
    }
}