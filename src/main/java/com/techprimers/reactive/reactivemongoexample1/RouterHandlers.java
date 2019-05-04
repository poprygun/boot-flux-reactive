package com.techprimers.reactive.reactivemongoexample1;

import com.techprimers.reactive.reactivemongoexample1.model.Track;
import com.techprimers.reactive.reactivemongoexample1.model.TrackEvent;
import com.techprimers.reactive.reactivemongoexample1.repository.TrackRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class RouterHandlers {

    private TrackRepository trackRepository;

    public RouterHandlers(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(trackRepository.findAll(), Track.class);
    }

    public Mono<ServerResponse> getId(ServerRequest serverRequest) {

        String empId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .body(trackRepository.findById(empId), Track.class);
    }

    public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
        String trackId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        trackRepository.findById(trackId)
                                .flatMapMany(track -> {
                                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                                    Flux<TrackEvent> trackEventFlux =
                                            Flux.fromStream(
                                                    Stream.generate(() -> new TrackEvent(track,
                                                            new Date()))
                                            );
                                    return Flux.zip(interval, trackEventFlux)
                                            .map(Tuple2::getT2);
                                }), TrackEvent.class
                );
    }
}
