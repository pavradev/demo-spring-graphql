package se.pavradev.demo.dao;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document(collection = "movies")
@Builder
public class MovieDO {

    @Id
    private String movieId;
    private String title;
    private Integer rating;
    private String thumbnail;

}
