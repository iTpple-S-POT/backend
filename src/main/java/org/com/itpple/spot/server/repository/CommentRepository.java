package org.com.itpple.spot.server.repository;

import java.util.Optional;
import org.com.itpple.spot.server.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.pot WHERE c.id = :commentId")
    Optional<Comment> findByIdWithPot(Long commentId);
}
