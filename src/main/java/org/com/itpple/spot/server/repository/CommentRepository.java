package org.com.itpple.spot.server.repository;

import java.util.List;
import java.util.Optional;
import org.com.itpple.spot.server.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.pot WHERE c.id = :commentId")
    Optional<Comment> findByIdWithPot(Long commentId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.parentComment LEFT JOIN FETCH c.writer "
        + "WHERE c.pot.id = :potId ORDER BY c.parentComment.id ASC NULLS FIRST, c.createdAt ASC")
    List<Comment> findAllComment(Long potId);
}
