package org.com.itpple.spot.server.domain.comment.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.parentComment LEFT JOIN FETCH c.writer "
        + "WHERE c.pot.id = :potId ORDER BY c.parentComment.id ASC NULLS FIRST, c.createdAt ASC")
    List<Comment> findAllComment(Long potId);

    List<Comment> findByWriterId(Long userId);
}
