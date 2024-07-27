package com.example.mytv.adapter.out;

import com.example.mytv.adapter.out.mongo.CommentDocument;
import com.example.mytv.adapter.out.mongo.CommentMongoRepository;
import com.example.mytv.application.port.out.CommentPort;
import com.example.mytv.domain.comment.Comment;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CommentPersistenceAdapter implements CommentPort {
    private final CommentMongoRepository commentMongoRepository;

    public CommentPersistenceAdapter(CommentMongoRepository commentMongoRepository) {
        this.commentMongoRepository = commentMongoRepository;
    }

    @Override
    public Comment saveComment(Comment comment) {
        var commentDocument = CommentDocument.from(comment);

        return commentMongoRepository.save(commentDocument)
            .toDomain();
    }

    @Override
    public void deleteComment(String commentId) {
        commentMongoRepository.deleteById(commentId);
    }

    @Override
    public Optional<Comment> loadComment(String commentId) {
        return commentMongoRepository.findById(commentId)
            .map(CommentDocument::toDomain);
    }
}
