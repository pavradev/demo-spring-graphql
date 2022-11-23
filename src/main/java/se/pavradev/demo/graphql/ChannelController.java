package se.pavradev.demo.graphql;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import se.pavradev.demo.dao.ChannelDO;
import se.pavradev.demo.dao.ChannelRepository;
import se.pavradev.demo.dao.MovieDO;
import se.pavradev.demo.dao.MovieRepository;
import se.pavradev.demo.dao.RecommendationDO;
import se.pavradev.types.Channel;
import se.pavradev.types.Movie;
import se.pavradev.types.Recommendation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Controller
public class ChannelController {

    private MovieRepository movieRepository;
    private ChannelRepository channelRepository;

    @QueryMapping
    public Optional<Movie> movie(@Argument String id) {
        return movieRepository.findById(id)
            .map(this::toMovie);
    }

    @QueryMapping
    public List<Channel> channels() {
        return channelRepository.findAll()
            .stream()
            .map(this::toChannel)
            .collect(toList());
    }

    @QueryMapping
    public Optional<Channel> channel(@Argument String id) {
        return channelRepository.findById(id)
            .map(this::toChannel);
    }

    @BatchMapping
    public Mono<Map<Recommendation, Movie>> movie(List<Recommendation> recommendations) {
        List<String> movieIds = recommendations.stream()
            .map(rec -> rec.getMovie().getId())
            .collect(toList());
        Map<String, Movie> moviesById = StreamSupport
            .stream(movieRepository.findAllById(movieIds).spliterator(), false)
            .collect(toMap(MovieDO::getMovieId, movieDO -> toMovie(movieDO)));
        return Mono.just(recommendations.stream()
            .collect(toMap(rec -> rec, rec -> moviesById.get(rec.getMovie().getId()))));
    }

    private Channel toChannel(ChannelDO channelDO) {
        return Channel.newBuilder()
            .id(channelDO.getChannelId())
            .author(channelDO.getAuthor())
            .title(channelDO.getTitle())
            .recommendations(channelDO.getRecommendations().stream()
                .map(this::toRecommendation)
                .collect(toList()))
            .build();
    }

    private Recommendation toRecommendation(RecommendationDO recommendationDO) {
        return Recommendation.newBuilder()
            .text(recommendationDO.getText())
            .movie(Movie.newBuilder()
                .id(recommendationDO.getMovieId())
                .build())
            .build();
    }

    private Movie toMovie(MovieDO mDO) {
        return Movie.newBuilder()
            .id(mDO.getMovieId())
            .rating(mDO.getRating())
            .thumbnail(mDO.getThumbnail())
            .title(mDO.getTitle())
            .build();
    }


}
