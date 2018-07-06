package org.igrator.reactivetest;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TestRepository extends ReactiveCrudRepository<Text, String> {

    @Tailable
//    @Query("{}")
    Flux<Text> findTextsBy();
}
