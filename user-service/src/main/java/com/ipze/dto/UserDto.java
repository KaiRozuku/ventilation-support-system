//package com.ipze.dto1;
//
//import com.ipze.domain.postgres.Role;
//import lombok.Builder;
//
//import java.util.UUID;
//
//@Builder
//public record UserDto(UUID userID,
//                      String nameUKR,
//                      String email,
//                      Role role
//                      ) {
//    @Override
//    public String toString(){
//        return "{uuid}=" + userID + " {nameUKR}=" + nameUKR + " {email}=" + email + " {role}" + role.name();
//    }
//}