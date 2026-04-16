package com.ipze.dto.request;

import java.util.List;

public record CreateGroupRequest(String name, List<String> users) {}