package se.pavradev.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.pavradev.demo.dao.ChannelDO;
import se.pavradev.demo.dao.ChannelRepository;
import se.pavradev.demo.dao.MovieDO;
import se.pavradev.demo.dao.MovieRepository;
import se.pavradev.demo.dao.RecommendationDO;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Test
	void seedDb() {
		channelRepository.deleteAll();
		movieRepository.deleteAll();

		MovieDO rockyMovie = MovieDO.builder()
			.rating(5)
			.thumbnail("https://cdn/movies/rokkie/thumbnail.jpg")
			.title("Rocky")
			.build();
		rockyMovie = movieRepository.save(rockyMovie);

		MovieDO casablancaMovie = MovieDO.builder()
			.rating(7)
			.thumbnail("https://cdn/movies/casablanca/thumbnail.jpg")
			.title("Casablanca")
			.build();
		casablancaMovie = movieRepository.save(casablancaMovie);

		ChannelDO goodMoviesChannel = ChannelDO.builder()
			.author("John Travolta")
			.title("Good movies")
			.recommendations(List.of(
				RecommendationDO.builder()
					.text("Rocky rocks")
					.movieId(rockyMovie.getMovieId())
					.build(),
				RecommendationDO.builder()
					.text("To watch with your partner")
					.movieId(casablancaMovie.getMovieId())
					.build()
			))
			.build();
		channelRepository.save(goodMoviesChannel);
	}

}
