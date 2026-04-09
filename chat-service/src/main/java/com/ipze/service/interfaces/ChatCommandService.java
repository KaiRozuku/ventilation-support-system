package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.InviteStatus;


public interface ChatCommandService {

    void processIncomingMessage(ChatMessageDto chatMessageDto); //maybe change method name?

    InviteStatus sendInvite(String receiverId);
}