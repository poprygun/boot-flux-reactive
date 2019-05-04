package com.techprimers.reactive.reactivemongoexample1;

import com.techprimers.reactive.reactivemongoexample1.model.Track;
import com.techprimers.reactive.reactivemongoexample1.repository.TrackRepository;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.api.Randomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static java.nio.charset.Charset.forName;

@SpringBootApplication
public class Application {

	@Bean
	CommandLineRunner employees(TrackRepository trackRepository) {

		EasyRandomParameters parameters = new EasyRandomParameters()
				.seed(123L)
				.randomize(FieldPredicates.named("name")
						.and(FieldPredicates.ofType(String.class))
						.and(FieldPredicates.inClass(Track.class)), new NameRandomizer())
				.randomize(FieldPredicates.named("id")
						.and(FieldPredicates.ofType(String.class))
						.and(FieldPredicates.inClass(Track.class)), new IdRandomizer())
				.objectPoolSize(100)
				.randomizationDepth(3)
				.charset(forName("UTF-8"))
				.stringLengthRange(5, 50)
				.collectionSizeRange(1, 10)
				.scanClasspathForConcreteTypes(true)
				.overrideDefaultInitialization(false)
				.ignoreRandomizationErrors(true);

		EasyRandom easyRandom = new EasyRandom(parameters);

		return args -> {
			trackRepository
					.deleteAll()
			.subscribe(null, null, () -> {

				easyRandom.objects(Track.class, 13)
						.forEach(track -> {
				trackRepository
						.save(track)
						.subscribe(System.out::println);
						});

			})
			;
		};

	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	class NameRandomizer implements Randomizer<String> {

		private List<String> names = Arrays.asList("AAA1", "BBB2", "CCC3");

		@Override
		public String getRandomValue() {
			return names.get(new Random().nextInt(2));
		}

	}
	class IdRandomizer implements Randomizer<String> {

		@Override
		public String getRandomValue() {
			return UUID.randomUUID().toString();
		}

	}
}
