package com.ipze.dto.request;

import java.util.UUID;

public record ChangeNameRequest(UUID uuid, String name) {}