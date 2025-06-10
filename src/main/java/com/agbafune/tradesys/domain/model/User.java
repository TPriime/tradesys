package com.agbafune.tradesys.domain.model;

import java.math.BigDecimal;

public record User(
                Long id,
                String username,
                Integer gemCount,
                Integer rank,
                BigDecimal funds
        ) {
            public static class Builder {
                private Long id;
                private String username;
                private Integer gemCount = 0;
                private Integer rank = Integer.MAX_VALUE;
                private BigDecimal funds = new BigDecimal("100.00");

                public Builder() {
                }

                public Builder(User user) {
                    this.id = user.id();
                    this.username = user.username();
                    this.gemCount = user.gemCount();
                    this.rank = user.rank();
                    this.funds = user.funds();
                }

                public Builder id(Long id) {
                    this.id = id;
                    return this;
                }

                public Builder username(String username) {
                    this.username = username;
                    return this;
                }

                public Builder gemCount(Integer gemCount) {
                    this.gemCount = gemCount;
                    return this;
                }

                public Builder rank(Integer rank) {
                    this.rank = rank;
                    return this;
                }

                public Builder funds(BigDecimal funds) {
                    this.funds = funds;
                    return this;
                }

                public User build() {
                    return new User(id, username, gemCount, rank, funds);
                }
            }
        }
