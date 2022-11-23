package se.pavradev.demo.dao;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Value
@Document(collection = "channels")
@Builder
public class ChannelDO {

    @Id
    private String channelId;
    private String title;
    private String author;
    private List<RecommendationDO> recommendations;
}
