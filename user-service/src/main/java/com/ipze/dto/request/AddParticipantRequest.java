package com.ipze.dto.request;

import com.ipze.dto.ParticipantRole;

public record AddParticipantRequest (String userId, ParticipantRole participantRole) {
}
