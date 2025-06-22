package com.agbafune.tradesys.event;

public record UserRewardEvent(
    Long userId,
    Integer awardedGems,
    Integer userGems
) {
}
