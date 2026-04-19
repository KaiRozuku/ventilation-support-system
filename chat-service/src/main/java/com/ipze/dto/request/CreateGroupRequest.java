package com.ipze.dto.request;

import java.util.List;

public record CreateGroupRequest(String groupName, List<String> users) {}