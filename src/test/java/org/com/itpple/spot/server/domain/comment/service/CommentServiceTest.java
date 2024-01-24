package org.com.itpple.spot.server.domain.comment.service;

import org.com.itpple.spot.server.domain.comment.dto.CommentDto;
import org.com.itpple.spot.server.domain.comment.dto.request.CreateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.request.UpdateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.response.CreateCommentResponse;
import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.com.itpple.spot.server.domain.comment.exception.CommentPotNotMatchException;
import org.com.itpple.spot.server.domain.comment.exception.CommentWriterNotMatchException;
import org.com.itpple.spot.server.domain.comment.repository.CommentRepository;
import org.com.itpple.spot.server.domain.comment.service.impl.CommentServiceImpl;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.util.CommentTestUtil;
import org.com.itpple.spot.server.util.PotTestUtil;
import org.com.itpple.spot.server.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@InjectMocks
	private CommentServiceImpl sut;

	@Mock
	private CommentRepository commentRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PotRepository potRepository;

	@Test
	void 작성하고자_하는_댓글에_대한_데이터를_받아_추가한다() {
		// given
		User user = UserTestUtil.create();
		Pot pot = PotTestUtil.create();
		Comment expectSaveComment = CommentTestUtil.create(pot, user, null, "content");
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		given(potRepository.findById(anyLong())).willReturn(Optional.of(pot));
		given(commentRepository.save(any(Comment.class))).willReturn(expectSaveComment);

		// when
		CreateCommentResponse actualSavedComment = sut.addComment(
				anyLong(), createCommentRequest(1L, null, expectSaveComment.getContent())
		);

		// then
		assertThat(actualSavedComment.writerProfileImageUrl()).isEqualTo(user.getProfileImageUrl());
		assertThat(actualSavedComment.writerName()).isEqualTo(user.getName());
		assertThat(actualSavedComment.content()).isEqualTo(expectSaveComment.getContent());
	}

	@Test
	void 추가할_댓글의_potId가_부모_댓글의_potId와_다르다면_에러가_발생한다() {
		// given
		Long wrongPotId = 50L;
		User user = UserTestUtil.create();
		Pot pot = spy(PotTestUtil.create());
		Comment parentComment = CommentTestUtil.create(pot, user, null, "content1");
		doReturn(1L).when(pot).getId();
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		given(potRepository.findById(anyLong())).willReturn(Optional.of(pot));
		given(commentRepository.findById(anyLong())).willReturn(Optional.of(parentComment));

		// when
		Throwable throwable = catchThrowable(
				() -> sut.addComment(anyLong(), createCommentRequest(wrongPotId, 1L, "content")));

		// then
		assertThat(throwable).isInstanceOf(CommentPotNotMatchException.class);
	}

	@Test
	void POT에_존재하는_모든_댓글을_조회해_자식댓글이_존재한다면_부모댓글에_하위계층에_넣어_반환한다() {
		// given
		User user = UserTestUtil.create();
		Pot pot = PotTestUtil.create();
		Comment parentComment = CommentTestUtil.create(pot, user, null, "content");
		List<Comment> commentList = List.of(
				parentComment,
				CommentTestUtil.create(pot, user, parentComment, "content2"),
				CommentTestUtil.create(pot, user, parentComment, "content3")
		);
		given(userRepository.existsById(anyLong())).willReturn(true);
		given(potRepository.existsById(anyLong())).willReturn(true);
		given(commentRepository.findAllComment(anyLong())).willReturn(commentList);

		// when
		List<CommentDto> getCommentList = sut.getCommentList(1L, 1L);

		// then
		assertThat(getCommentList.size()).isEqualTo(1);
	}

	@Test
	void 댓글을_수정할_때_요청을_보낸_사용자가_등록한_댓글이_아니라면_에러가_발생한다() {
		// given
		Long wrongUserId = 50L;
		User user = spy(UserTestUtil.create());
		Pot pot = PotTestUtil.create();
		Comment comment = CommentTestUtil.create(pot, user, null, "content");
		doReturn(1L).when(user).getId();
		given(userRepository.existsById(anyLong())).willReturn(true);
		given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

		// when
		Throwable throwable = catchThrowable(
				() -> sut.updateComment(wrongUserId, anyLong(), updateCommentRequest("update content")));

		// then
		assertThat(throwable).isInstanceOf(CommentWriterNotMatchException.class);
	}

	@Test
	void 삭제할_댓글의_PK를_받아_댓글을_삭제한다() {
		// given
		Long userId = 1L;
		User user = spy(UserTestUtil.create());
		Pot pot = PotTestUtil.create();
		Comment comment = CommentTestUtil.create(pot, user, null, "content");
		doReturn(userId).when(user).getId();
		given(userRepository.existsById(anyLong())).willReturn(true);
		given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
		willDoNothing().given(commentRepository).deleteById(anyLong());

		// when
		sut.deleteComment(userId, anyLong());

		// then
		then(commentRepository).should().deleteById(anyLong());
	}

	@Test
	void 댓글을_삭제할_때_요청을_보낸_사용자가_등록한_댓글이_아니라면_에러가_발생한다() {
		// given
		Long wrongUserId = 50L;
		User user = spy(UserTestUtil.create());
		Pot pot = PotTestUtil.create();
		Comment comment = CommentTestUtil.create(pot, user, null, "content");
		doReturn(1L).when(user).getId();
		given(userRepository.existsById(anyLong())).willReturn(true);
		given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

		// when
		Throwable throwable = catchThrowable(() -> sut.deleteComment(wrongUserId, 1L));

		// then
		assertThat(throwable).isInstanceOf(CommentWriterNotMatchException.class);
	}

	private CreateCommentRequest createCommentRequest(Long potId, Long parentCommentId, String content) {
		return new CreateCommentRequest(potId, parentCommentId, content);
	}

	private UpdateCommentRequest updateCommentRequest(String content) {
		return new UpdateCommentRequest(content);
	}
}
