package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;

public class CommentTestUtil {

	public static Comment create(Pot pot, User writer, Comment parentComment, String content) {
		return Comment.builder()
				.pot(pot)
				.writer(writer)
				.parentComment(parentComment)
				.content(content)
				.build();
	}
}
