package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.example.mytv.adapter.out.mongo.CommentMongoRepository;
import com.example.mytv.domain.comment.CommentFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentPersistenceAdapterTest {
    private CommentPersistenceAdapter sut;

    private final CommentMongoRepository commentMongoRepository = mock(CommentMongoRepository.class);

    @BeforeEach
    void setUp() {
        sut = new CommentPersistenceAdapter(commentMongoRepository);
    }

    @Test
    void testCreateComment() {
        var comment = CommentFixtures.stub("commentId");
        given(commentMongoRepository.save(any())).willAnswer(arg -> arg.getArgument(0));

        var result = sut.saveComment(comment);

        then(result)
            .hasFieldOrPropertyWithValue("id", "commentId");
    }

}