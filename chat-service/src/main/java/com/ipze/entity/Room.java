package com.ipze.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "rooms")
@Builder
public class Room {

    @Id
    private String id;

    private String name;

    private VisibilityMode visibilityMode;
}