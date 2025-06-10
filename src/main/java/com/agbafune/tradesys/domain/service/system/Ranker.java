package com.agbafune.tradesys.domain.service.system;

import java.util.LinkedHashMap;

public interface Ranker {
    public int getRank(Long userId);
    public LinkedHashMap<Long, Integer> getTopRankedUsers(int limit);
    public void updateRank(Long userId, Integer newGems);
}
