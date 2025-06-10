package com.agbafune.tradesys.web.model;

import com.agbafune.tradesys.domain.model.Portfolio;
import com.agbafune.tradesys.domain.model.User;

import java.math.BigDecimal;
import java.util.List;

public record UserStatsBean(
        Long userId,
        Integer gemCount,
        Integer rank,
        BigDecimal funds,
        List<AssetBean> assets,
        BigDecimal totalAssetValue
){
    private record AssetBean(
            Long assetId,
            String assetName,
            String assetSymbol,
            BigDecimal assetPrice,
            BigDecimal quantity
    ){}

    public static UserStatsBean build(User user, Portfolio portfolio, Integer rank) {
        List<AssetBean> assetList = portfolio.assets().stream()
                .map(asset -> new AssetBean(
                        asset.getAsset().id(),
                        asset.getAsset().name(),
                        asset.getAsset().symbol(),
                        asset.getAsset().price(),
                        asset.getQuantity()
                ))
                .toList();

        return new UserStatsBean(user.id(), user.gemCount(), rank, user.funds(), assetList, portfolio.value());
    }
}
