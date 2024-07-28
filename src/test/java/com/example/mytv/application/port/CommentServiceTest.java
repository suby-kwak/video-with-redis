package com.example.mytv.application.port;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.in.api.dto.CommentRequest;
import com.example.mytv.application.CommentService;
import com.example.mytv.application.port.out.CommentLikePort;
import com.example.mytv.application.port.out.CommentPort;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.comment.Comment;
import com.example.mytv.domain.comment.CommentFixtures;
import com.example.mytv.domain.user.User;
import com.example.mytv.domain.user.UserFixtures;
import com.example.mytv.exception.BadRequestException;
import com.example.mytv.exception.DomainNotFoundException;
import com.example.mytv.exception.ForbiddenRequestException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CommentServiceTest {
    private CommentService sut;

    private final CommentPort commentPort = mock(CommentPort.class);
    private final LoadUserPort loadUserPort = mock(LoadUserPort.class);
    private final CommentLikePort commentLikePort = mock(CommentLikePort.class);

    @BeforeEach
    void setUp() {
        sut = new CommentService(commentPort, loadUserPort, commentLikePort);
    }

    @Test
    @DisplayName("댓글 생성")
    void testCreateComment() {
        // given
        var user = UserFixtures.stub();
        var commentRequest = new CommentRequest("channelId", "videoId", "comment");
        // when
        sut.createComment(user, commentRequest);
        // then
        var argumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentPort).saveComment(argumentCaptor.capture());
        then(argumentCaptor.getValue())
            .hasFieldOrPropertyWithValue("authorId", user.getId())
            .hasFieldOrPropertyWithValue("channelId", commentRequest.getChannelId())
            .hasFieldOrPropertyWithValue("videoId", commentRequest.getVideoId())
            .hasFieldOrPropertyWithValue("text", commentRequest.getText());
    }

    @Nested
    @DisplayName("댓글 수정")
    class UpdateComment {
        @Test
        @DisplayName("댓글 수정 후 수정된 댓글 반환")
        void updateCommentThenReturnUpdatedComment() {
            var commentId = "commentId";
            var user = UserFixtures.stub();
            var comment = CommentFixtures.stub(commentId);
            var commentRequest = new CommentRequest("channelId", "videoId", "new comment");
            given(commentPort.loadComment(any())).willReturn(Optional.of(comment));
            given(commentPort.saveComment(any())).willAnswer(arg -> arg.getArgument(0));

            var result = sut.updateComment(commentId, user, commentRequest);

            then(result.getText()).isEqualTo("new comment");
        }

        @Test
        @DisplayName("댓글 channelId, videoId 가 다르면 BadRequestException throw")
        void givenOtherMetaDataThenBadRequestException() {
            var commentId = "commentId";
            var user = UserFixtures.stub();
            var commentRequest = new CommentRequest("otherChannelId", "otherVideoId", "new comment");
            given(commentPort.loadComment(any())).willReturn(Optional.of(CommentFixtures.stub(commentId)));

            thenThrownBy(() -> sut.updateComment(commentId, user, commentRequest))
                .isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("댓글 수정 user와 댓글 author 가 다르면 ForbiddenRequestException throw")
        void givenOtherUserThenForbiddenRequestException() {
            var commentId = "commentId";
            var otherUser = UserFixtures.stub("otherUser");
            var commentRequest = new CommentRequest("channelId", "videoId", "new comment");
            given(commentPort.loadComment(any())).willReturn(Optional.of(CommentFixtures.stub(commentId)));

            thenThrownBy(() -> sut.updateComment(commentId, otherUser, commentRequest))
                .isInstanceOf(ForbiddenRequestException.class);
        }

        @Test
        @DisplayName("존재하지 않는 댓글이면 DomainNotFoundException throw")
        void givenNoCommentThenDomainNotFoundException() {
            var commentId = "commentId";
            var user = UserFixtures.stub();
            var commentRequest = new CommentRequest("channelId", "videoId", "new comment");
            given(commentPort.loadComment(any())).willReturn(Optional.empty());

            thenThrownBy(() -> sut.updateComment(commentId, user, commentRequest))
                .isInstanceOf(DomainNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class DeleteComment {
        @Test
        @DisplayName("댓글을 삭제")
        void givenCommentIdThenDeleteComment() {
            var commentId = "commentId";
            var user = UserFixtures.stub();
            var comment = CommentFixtures.stub(commentId);
            given(commentPort.loadComment(any())).willReturn(Optional.of(comment));

            sut.deleteComment(commentId, user);

            verify(commentPort).deleteComment(commentId);
        }

        @Test
        @DisplayName("댓글 삭제 user와 댓글 author 가 다르면 ForbiddenRequestException throw")
        void givenOtherUserThrowForbiddenRequestException() {
            var commentId = "commentId";
            var otherUser = UserFixtures.stub("otherUser");
            given(commentPort.loadComment(any())).willReturn(Optional.of(CommentFixtures.stub(commentId)));

            thenThrownBy(() -> sut.deleteComment(commentId, otherUser))
                .isInstanceOf(ForbiddenRequestException.class);
        }

        @Test
        @DisplayName("존재하지 않는 댓글이면 DomainNotFoundException throw")
        void givenNoCommentThenDomainNotFoundException() {
            var commentId = "commentId";
            var user = UserFixtures.stub();
            given(commentPort.loadComment(any())).willReturn(Optional.empty());

            thenThrownBy(() -> sut.deleteComment(commentId, user))
                .isInstanceOf(DomainNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("댓글 한개 조회")
    class GetComment {
        @Test
        void givenCommentThenReturnComment() {
            var comment = CommentFixtures.stub("commentId");
            var author = User.builder().id(comment.getAuthorId()).name("author").profileImageUrl("https://example.com/profile.jpg").build();
            given(commentPort.loadComment(any())).willReturn(Optional.of(comment));
            given(loadUserPort.loadUser(any())).willReturn(Optional.of(author));
            given(commentLikePort.getCommentLikeCount(any())).willReturn(20L);

            var result = sut.getComment("commentId");

            // Then
            then(result)
                .hasFieldOrPropertyWithValue("id", comment.getId())
                .hasFieldOrPropertyWithValue("videoId", comment.getVideoId())
                .hasFieldOrPropertyWithValue("text", comment.getText())
                .hasFieldOrPropertyWithValue("authorId", author.getId())
                .hasFieldOrPropertyWithValue("authorName", author.getName())
                .hasFieldOrPropertyWithValue("authorProfileImageUrl", author.getProfileImageUrl())
                .hasFieldOrPropertyWithValue("likeCount", 20L);
        }
    }
}