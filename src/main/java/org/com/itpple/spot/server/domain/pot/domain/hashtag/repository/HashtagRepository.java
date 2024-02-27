package org.com.itpple.spot.server.domain.pot.domain.hashtag.repository;

import java.util.List;
import java.util.Optional;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagEquals(String hashtag);

    List<Hashtag> findAllByHashtagContaining(String hashtag);

    List<Hashtag> findByHashtagIn(List<String> hashtagList);
}
