package org.com.itpple.spot.server.domain.pot.domain.hashtag.service;

import io.jsonwebtoken.lang.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.repository.HashtagRepository;
import org.com.itpple.spot.server.domain.pot.dto.HashtagDTO;
import org.com.itpple.spot.server.domain.pot.dto.request.CreateHashtagRequest;
import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    private static final int HASHTAG_PAGE_SIZE = 10;

    @Override
    @Transactional(readOnly = true)
    public List<HashtagDTO> getHashtag(String keyword, int page) {
        var pageable = PageRequest.of(page, HASHTAG_PAGE_SIZE);
        return hashtagRepository.findByHashtagContainingOrderByCountDesc(keyword, pageable)
                .stream().filter(hashtag -> hashtag.getCount()
                        > 1) // 페이징 시 count 기준으로 정렬되어 있어서 1보다 작은 경우는 필터링해도 문제 없음
                .sorted((o1, o2) -> Math.toIntExact(o2.getCount() - o1.getCount()))
                .map(HashtagDTO::from).toList();
    }

    @Override
    @Transactional
    public List<HashtagDTO> createHashtag(CreateHashtagRequest createHashtagRequest) {
        this.checkDuplicateHashtagList(createHashtagRequest.getHashtagList());

        return hashtagRepository.saveAll(
                        createHashtagRequest.getHashtagList().stream()
                                .map(Hashtag::newInstance).toList()
                )
                .stream().map(HashtagDTO::from).toList();
    }

    private void checkDuplicateHashtagList(List<String> hashtagList) {
        var existHashtagList = hashtagRepository.findByHashtagIn(hashtagList);
        if (!Collections.isEmpty(existHashtagList)) {
            throw new CustomException(
                    ErrorCode.CONFLICT_HASHTAG,
                    existHashtagList.stream().map(Hashtag::getHashtag)
                            .reduce((s, s2) -> s + ", " + s2).orElse("")
            );
        }
    }
}
