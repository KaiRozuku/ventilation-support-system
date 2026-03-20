package com.ipze.dto.request;

import java.util.UUID;

public record ChangeEmailRequest(UUID uuid, String newEmail) {}