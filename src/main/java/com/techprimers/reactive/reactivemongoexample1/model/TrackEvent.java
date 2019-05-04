package com.techprimers.reactive.reactivemongoexample1.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class TrackEvent {

    private Track track;
    private Date date;

}
