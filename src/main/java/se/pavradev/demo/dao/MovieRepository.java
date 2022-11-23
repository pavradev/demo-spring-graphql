package se.pavradev.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<MovieDO, String> {
}
