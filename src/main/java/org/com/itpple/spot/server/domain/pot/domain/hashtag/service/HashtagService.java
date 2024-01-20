package org.com.itpple.spot.server.domain.pot.domain.hashtag.service;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.dto.HashtagDTO;
import org.com.itpple.spot.server.domain.pot.dto.request.CreateHashtagRequest;

public interface HashtagService {

    List<HashtagDTO> getHashtag(String keyword, int page);
    List<HashtagDTO> createHashtag(CreateHashtagRequest createHashtagRequest);

}
