package org.com.itpple.spot.server.domain.comment.repository;

import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.parentComment LEFT JOIN FETCH c.writer "
        + "WHERE c.pot.id = :potId ORDER BY c.parentComment.id ASC NULLS FIRST, c.createdAt ASC")
    List<Comment> findAllComment(Long potId);

    @Query("SELECT c FROM Comment c WHERE c.id = :commentId AND c.writer.id = :userId")
    Optional<Comment> findByIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
