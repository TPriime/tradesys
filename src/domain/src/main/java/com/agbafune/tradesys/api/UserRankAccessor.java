package com.agbafune.tradesys.api;

import java.util.LinkedHashMap;

public interface UserRankAccessor {
    int getRank(Long userId);
    LinkedHashMap<Long, Integer> getTopRankedUsers(int topN);
}