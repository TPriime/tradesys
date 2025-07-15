package tradesys;

import com.agbafune.tradesys.UserRankManager;
import com.agbafune.tradesys.event.AppEventListener;
import com.agbafune.tradesys.event.TradeEvent;
import com.agbafune.tradesys.event.UserRewardedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRankManagerTest {
    private UserRankManager systemUserRankManager;

    private AppEventListener mockEventListener = new AppEventListener() {
        @Override
        public void onTradeEvent(Consumer<TradeEvent> handler) {}
        @Override
        public void onUserRewardedEvent(Consumer<UserRewardedEvent> handler) {}
    };

    @BeforeEach
    void setUp() {
        systemUserRankManager = new UserRankManager(mockEventListener);
    }

    @Test
    void testUpdateRank_ShouldUpdateWithoutErrors() {
        assertDoesNotThrow(() -> systemUserRankManager.updateRank(1L, 50));
        assertDoesNotThrow(() -> systemUserRankManager.updateRank(2L, 100));

        assertEquals(2, systemUserRankManager.getRank(1L));
        assertEquals(1, systemUserRankManager.getRank(2L));
    }

    @Test
    void testGetRank_ShouldGetCorrectRankForUser() {
        systemUserRankManager.updateRank(1L, 50);
        systemUserRankManager.updateRank(2L, 100);
        systemUserRankManager.updateRank(3L, 75);

        assertEquals(3, systemUserRankManager.getRank(1L));
        assertEquals(1, systemUserRankManager.getRank(2L));
        assertEquals(2, systemUserRankManager.getRank(3L));
    }

    @Test
    void testGetTopRankedUsers_ShouldIncludeUsersWIthHighestGems() {
        List<Long> usersWithHighestGemCount = new ArrayList<>();
        // Generate gems (above 1000) for 10 users
        for (long userId = 1; userId <= 10; userId++) {
            int randomGems = 1000 + (int) userId;
            systemUserRankManager.updateRank(userId, randomGems);
            usersWithHighestGemCount.add(userId);
        }

        // Generate random gems (below 100) for 10 users
        for (long userId = 1; userId <= 10; userId++) {
            int randomGems = (int) (Math.random() * 100); // Random gems between 0 and 99
            systemUserRankManager.updateRank(userId, randomGems);
        }

        // Get the top-ranked users
        List<Long> highestRankedUserIds = systemUserRankManager.getTopRankedUsers(10)
                .keySet().stream().toList();

        assertTrue(highestRankedUserIds.containsAll(usersWithHighestGemCount),
                "Top-ranked users should include all users with the highest gem count");
    }

    @Test
    void testGetTopRankedUsers_ShouldRankUsersWithSameGemsEqually() {
        final int higherGems = 10;
        final int lowerGems = 5;
        // Add first 3 users with same higher gems
        long userId = 1;
        for (; userId <= 3; userId++) {
            systemUserRankManager.updateRank(userId, higherGems);
        }

        // add a user with a different gem count
        long userWithLowerGems = userId + 1;
        systemUserRankManager.updateRank(userWithLowerGems, lowerGems);

        // Get the top-ranked users
        List<Integer> userRanks = systemUserRankManager.getTopRankedUsers(4)
                .values().stream().toList();

        assertEquals(4, userRanks.size());
        assertIterableEquals(List.of(1, 1, 1, 4), userRanks);
    }

    @Test
    void testGetTopRankedUsers_ShouldReturnCorrectLimitSize() {
        systemUserRankManager.updateRank(1L, 50);
        systemUserRankManager.updateRank(2L, 100);
        systemUserRankManager.updateRank(3L, 75);
        systemUserRankManager.updateRank(4L, 25);
        systemUserRankManager.updateRank(5L, 60);
        systemUserRankManager.updateRank(6L, 80);

        Map<Long, Integer> topUsers = systemUserRankManager.getTopRankedUsers(3);

        assertEquals(3, topUsers.size());
        assertTrue(topUsers.containsKey(2L)); // User with the highest score
        assertTrue(topUsers.containsKey(3L)); // User with the lowest score in the top 10
    }

    @Test
    void testGetTopRankedUsers_ShouldMaintainRankOrder() {
        systemUserRankManager.updateRank(5L, 50);
        systemUserRankManager.updateRank(1L, 100);
        systemUserRankManager.updateRank(3L, 75);
        systemUserRankManager.updateRank(6L, 25);
        systemUserRankManager.updateRank(4L, 60);
        systemUserRankManager.updateRank(2L, 80);

        Map<Long, Integer> topUsers = systemUserRankManager.getTopRankedUsers(3);

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