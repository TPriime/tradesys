package com.agbafune.tradesys.event;

public record UserRewardedEvent(
    Long userId,
    Integer awardedGems,
    Integer userGems
) {
}
