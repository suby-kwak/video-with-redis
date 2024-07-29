package com.example.mytv.adapter.out.mongodb.comment;

import static org.assertj.core.api.BDDAssertions.then;

import com.example.mytv.adapter.out.mongo.comment.CommentDocument;
import com.example.mytv.adapter.out.mongo.comment.CommentMongoRepository;
import com.example.mytv.domain.comment.Comment;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class CommentMongoRepositoryIntTest {
    @Autowired
    private CommentMongoRepository sut;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testListByPublishedAt() {
        var videoId = "videoId";
        for (int i = 0; i < 10; i++) {
            var id = UUID.randomUUID().toString();
            var comment = Comment.builder().id(id).channelId("channelId").videoId(videoId).text("text " + i).authorId("user")
                .publishedAt(LocalDateTime.now()).build();
            mongoTemplate.save(CommentDocument.from(comment));
        }

        var result = sut.findAllByVideoIdAndPublishedAtLessThanEqualOrderByPublishedAtDesc(videoId, LocalDateTime.now(), Limit.of(5));

        then(result)
            .hasSize(5)
            .extracting("publishedAt", LocalDateTime.class)
            .isSortedAccordingTo(Comparator.reverseOrder());

        // result.forEach(e -> System.out.println(e));
    }
}
