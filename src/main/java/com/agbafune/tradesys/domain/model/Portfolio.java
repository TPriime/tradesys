package com.agbafune.tradesys.domain.model;

import java.util.ArrayList;

public record Portfolio(
        ArrayList<Asset> assets
) {
}