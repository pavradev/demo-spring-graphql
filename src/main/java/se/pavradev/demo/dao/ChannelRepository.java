package se.pavradev.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelRepository extends MongoRepository<ChannelDO, String> {
}
