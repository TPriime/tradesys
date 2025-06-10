package com.agbafune.tradesys.service;

import com.agbafune.tradesys.domain.service.system.RankerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RankerImplTest {
    private RankerImpl systemRanker;

    @BeforeEach
    void setUp() {
        systemRanker = new RankerImpl();
    }

    @Test
    void testUpdateRank_ShouldUpdateWithoutErrors() {
        assertDoesNotThrow(() -> systemRanker.updateRank(1L, 50));
        assertDoesNotThrow(() -> systemRanker.updateRank(2L, 100));

        assertEquals(2, systemRanker.getRank(1L));
        assertEquals(1, systemRanker.getRank(2L));
    }

    @Test
    void testGetRank_ShouldGetCorrectRankForUser() {
        systemRanker.updateRank(1L, 50);
        systemRanker.updateRank(2L, 100);
        systemRanker.updateRank(3L, 75);

        assertEquals(3, systemRanker.getRank(1L));
        assertEquals(1, systemRanker.getRank(2L));
        assertEquals(2, systemRanker.getRank(3L));
    }

    @Test
    void testGetTopRankedUsers_ShouldIncludeUsersWIthHighestGems() {
        List<Long> usersWithHighestGemCount = new ArrayList<>();
        // Generate gems (above 1000) for 10 users
        for (long userId = 1; userId <= 10; userId++) {
            int randomGems = 1000 + (int) userId;
            systemRanker.updateRank(userId, randomGems);
            usersWithHighestGemCount.add(userId);
        }

        // Generate random gems (below 100) for 10 users
        for (long userId = 1; userId <= 10; userId++) {
            int randomGems = (int) (Math.random() * 100); // Random gems between 0 and 99
            systemRanker.updateRank(userId, randomGems);
        }

        // Get the top-ranked users
        List<Long> highestRankedUserIds = systemRanker.getTopRankedUsers(10)
                .keySet().stream().toList();

        assertTrue(highestRankedUserIds.containsAll(usersWithHighestGemCount),
                "Top-ranked users should include all users with the highest gem count");
    }

    @Test
    void testGetTopRankedUsers_ShouldRankUsersWithSameGemsEqually() {
        int gems = 10;
        for (long userId = 1; userId <= 3; userId++) {
            systemRanker.updateRank(userId, gems);
        }

        // add a user with a different gem count
        long userWithLowestGems = 4L;
        systemRanker.updateRank(4L, 2);

        // Get the top-ranked users
        List<Long> highestRankedUserIds = systemRanker.getTopRankedUsers(4)
                .keySet().stream().toList();

        assertTrue(highestRankedUserIds.containsAll(List.of(1L, 1L, 1L, 4L)));
    }

    @Test
    void testGetTopRankedUsers_ShouldReturnCorrectLimitSize() {
        systemRanker.updateRank(1L, 50);
        systemRanker.updateRank(2L, 100);
        systemRanker.updateRank(3L, 75);
        systemRanker.updateRank(4L, 25);
        systemRanker.updateRank(5L, 60);
        systemRanker.updateRank(6L, 80);

        Map<Long, Integer> topUsers = systemRanker.getTopRankedUsers(3);

        assertEquals(3, topUsers.size());
        assertTrue(topUsers.containsKey(2L)); // User with the highest score
        assertTrue(topUsers.containsKey(3L)); // User with the lowest score in the top 10
    }

    @Test
    void testGetTopRankedUsers_ShouldMaintainRankOrder() {
        systemRanker.updateRank(5L, 50);
        systemRanker.updateRank(1L, 100);
        systemRanker.updateRank(3L, 75);
        systemRanker.updateRank(6L, 25);
        systemRanker.updateRank(4L, 60);
        systemRanker.updateRank(2L, 80);

        Map<Long, Integer> topUsers = systemRanker.getTopRankedUsers(3);

        // The expected order is based on gem count: 100, 80, 75
        List<Long> expectedIdOrder = List.of(1L, 2L, 3L);
        List<Long> actualIdOrder = new ArrayList<>(topUsers.keySet());
        assertEquals(expectedIdOrder, actualIdOrder, "Top users should be ordered by gem count in descending order");

        assertEquals(1, topUsers.get(1L)); // User with the highest score
        assertEquals(2, topUsers.get(2L)); // User with the second highest score
        assertEquals(3, topUsers.get(3L)); // User with the third highest score
        assertEquals(3, topUsers.size(), "Top users should contain exactly 3 users");
    }
}