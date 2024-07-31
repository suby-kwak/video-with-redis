package com.example.mytv.application.port.out;

import com.example.mytv.domain.user.User;

import java.util.Set;

public interface CommentBlockPort {
    void saveUserCommentBlock(String userId, String commentId);

    Set<String> getUserCommentBlocks(String userId);
}
