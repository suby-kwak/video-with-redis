package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.out.mongo.CommentDocument;
import com.example.mytv.adapter.out.mongo.CommentMongoRepository;
import com.example.mytv.domain.comment.CommentFixtures;
import java.util.Optional;
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
    void testSaveComment() {
        var comment = CommentFixtures.stub("commentId");
        given(commentMongoRepository.save(any())).willAnswer(arg -> arg.getArgument(0));

        var result = sut.saveComment(comment);

        then(result)
            .hasFieldOrPropertyWithValue("id", "commentId");
    }

    @Test
    void testDeleteComment() {
        var commentId = "commentId";

        sut.deleteComment(commentId);

        verify(commentMongoRepository).deleteById(commentId);
    }

    @Test
    void testLoadComment() {
        var commentId = "commentId";
        var comment = CommentFixtures.stub(commentId);
        given(commentMongoRepository.findById(any())).willReturn(Optional.of(CommentDocument.from(comment)));

        var result = sut.loadComment(commentId);
        then(result)
            .isPresent()
            .hasValueSatisfying(c -> {
                then(c)
                    .hasFieldOrPropertyWithValue("id", commentId)
                    .hasFieldOrPropertyWithValue("text", comment.getText());
            });
    }
}