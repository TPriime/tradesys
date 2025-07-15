package com.agbafune.tradesys;

import com.agbafune.tradesys.api.UserRankAccessor;
import com.agbafune.tradesys.event.AppEventListener;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class UserRankManager implements UserRankAccessor {
    private final ConcurrentHashMap<Long, Integer> userGemValue = new ConcurrentHashMap<>();
    private final ConcurrentSkipListMap<Integer, LongAdder> gemValueCounts = new ConcurrentSkipListMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public UserRankManager(AppEventListener eventListener) {
        eventListener.onUserRewardedEvent(event -> {
            updateRank(event.userId(), event.userGems());
        });
    }

    public void updateRank(Long userId, Integer newGemValue) {
        lock.writeLock().lock();
        try {
            Integer oldGemValue = userGemValue.get(userId);
            if (oldGemValue != null) {
                LongAdder counter = gemValueCounts.get(oldGemValue);
                if (counter != null) {
                    counter.decrement();
                    if (counter.sum() == 0) {
                        gemValueCounts.remove(oldGemValue);
                    }
                }
            }
            userGemValue.put(userId, newGemValue);
            gemValueCounts.computeIfAbsent(newGemValue, k -> new LongAdder()).increment();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getRank(Long userId) {
        lock.readLock().lock();
        try {
            Integer gemValue = userGemValue.get(userId);
            if (gemValue == null) return -1;

            int rank = 1;
            for (Map.Entry<Integer, LongAdder> entry : gemValueCounts.tailMap(gemValue, false).entrySet()) {
                rank += entry.getValue().intValue();
            }
            return rank;
        } finally {
            lock.readLock().unlock();
        }
    }

    public LinkedHashMap<Long, Integer> getTopRankedUsers(int topN) {
        lock.readLock().lock();
        try {
            LinkedHashMap<Long, Integer> topUsers = new LinkedHashMap<>();
            int rank = 1;

            // Iterate over gem values in descending order
            for (Map.Entry<Integer, LongAdder> entry : gemValueCounts.descendingMap().entrySet()) {
                int gem = entry.getKey();
                int count = entry.getValue().intValue();

                // Get all users with the current gem value (they would all share the same rank )
                List<Long> usersWithGem = userGemValue.entrySet().stream()
                        .filter(e -> e.getValue().equals(gem))
                        .map(Map.Entry::getKey)
                        .toList();

                // Assign the same rank to all users sharing this gem value
                for (Long userId : usersWithGem) {
                    topUsers.put(userId, rank);
                    if (topUsers.size() == topN) return topUsers;
                }

                // Move to the next rank (incrementing by count of users sharing the gem value)
                rank += count;
            }

            return topUsers;
        } finally {
            lock.readLock().unlock();
        }
    }
}