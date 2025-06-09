package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.TradeAction;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl {
    public void trade(String userId, String assetId, Double quantity, TradeAction action) {
        if (action == TradeAction.BUY) {
            // Logic for buying an asset
            System.out.println("Buying " + quantity + " of asset " + assetId + " for user " + userId);
        } else {
            // Logic for selling an asset
            System.out.println("Selling " + quantity + " of asset " + assetId + " for user " + userId);
        }
    }

    public void buy(String userId, String assetId, Double quantity) {
        // check if user balance is enough
        // check if asset is available

    }
}
