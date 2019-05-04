package com.techprimers.reactive.reactivemongoexample1.repository;

import com.techprimers.reactive.reactivemongoexample1.model.Track;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TrackRepository extends ReactiveMongoRepository<Track, String> {
}
