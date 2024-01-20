package org.com.itpple.spot.server.domain.pot.domain.hashtag.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByHashtagContainingOrderByCountDesc(String keyword, Pageable pageable);

    List<Hashtag> findByHashtagIn(List<String> hashtagList);
}
