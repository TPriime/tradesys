package com.agbafune.tradesys.domain.events;

public record UserRewardEvent(
    Long userId,
    Integer awardedGems,
    Integer userGems
) {
}
