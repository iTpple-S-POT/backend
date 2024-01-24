package org.com.itpple.spot.server.domain.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.context.TestConstructor.AutowireMode;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.util.CommentTestUtil;
import org.com.itpple.spot.server.util.PotTestUtil;
import org.com.itpple.spot.server.util.UserTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class CommentRepositoryTest {

	private final CommentRepository sut;
	private final TestEntityManager testEntityManager;

	private final User user = UserTestUtil.create();
	private final Category category = PotTestUtil.createCategory();
	private final Pot pot = PotTestUtil.create(user, category);
	private final Comment parentComment = CommentTestUtil.create(pot, user, null, "content1");
	private final Comment comment = CommentTestUtil.create(pot, user, parentComment, "content1_1");
	private final Comment parentComment2 = CommentTestUtil.create(pot, user, null, "content2");

	@BeforeEach
	public void setUp() {
		testEntityManager.persist(user);
		testEntityManager.persist(category);
		testEntityManager.persist(pot);
		testEntityManager.persist(parentComment);
		testEntityManager.persist(comment);
		testEntityManager.persist(parentComment2);
	}

	@Test
	void POT의_모든_댓글_조회_시_부모_댓글이_NULL인_데이터를_먼저_조회한다() {
		// given
		Comment comment1 = CommentTestUtil.create(pot, user, parentComment, "content1_2");
		Comment comment2 = CommentTestUtil.create(pot, user, parentComment2, "content2_1");
		sut.save(comment1);
		sut.save(comment2);
		testEntityManager.flush();

		// when
		List<Comment> commentList = sut.findAllComment(pot.getId());

		// then
		assertThat(commentList.get(0).getParentComment()).isNull();
		assertThat(commentList.get(1).getParentComment()).isNull();
	}
}
