package com.techprimers.reactive.reactivemongoexample1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Track {

    @Id
    private String id;
    private String name;
    private float latitude;
    private float longitude;
}
