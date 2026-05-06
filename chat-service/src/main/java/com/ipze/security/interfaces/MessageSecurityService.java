package com.ipze.security.interfaces;

public interface MessageSecurityService {

    boolean isMessageOwner(String messageId, String userId);
}