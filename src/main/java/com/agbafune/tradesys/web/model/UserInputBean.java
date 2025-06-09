package com.agbafune.tradesys.web.model;

import jakarta.validation.constraints.NotBlank;

public record UserInputBean(@NotBlank String username) {
}
