package com.example.mytv.adapter.out.mongo.comment;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;

public interface CommentMongoRepository extends CrudRepository<CommentDocument, String> {
    List<CommentDocument> findAllByVideoIdOrderByPublishedAtDesc(String videoId, LocalDateTime offset, Limit limit);
}
