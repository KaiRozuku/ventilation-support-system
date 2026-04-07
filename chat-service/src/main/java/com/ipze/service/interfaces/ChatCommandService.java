package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.InviteStatus;


public interface ChatCommandService {

    void sendToUser(ChatMessageDto chatMessageDto);

    InviteStatus sendInvite(String receiverId);
}