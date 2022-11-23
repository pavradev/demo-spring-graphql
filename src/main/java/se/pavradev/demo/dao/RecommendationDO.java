package se.pavradev.demo.dao;

import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
public class RecommendationDO {

    private String text;
    private String movieId;

}
