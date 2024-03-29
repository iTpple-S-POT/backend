package org.com.itpple.spot.server.domain.pot.domain.hashtag.service;

import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.repository.HashtagRepository;
import org.com.itpple.spot.server.domain.pot.dto.HashtagDTO;
import org.com.itpple.spot.server.domain.pot.dto.request.CreateHashtagRequest;
import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    private static final int HASHTAG_PAGE_SIZE = 10;

    @Override
    @Transactional(readOnly = true)
    public List<HashtagDTO> getHashtag(String keyword, int page) {

        var hashtagList = hashtagRepository.findAllByHashtagContaining(keyword)
                .stream().filter(hashtag -> hashtag.getCount() > 1) // 페이징 시 count 기준으로 정렬되어 있어서 1보다 작은 경우는 필터링해도 문제 없음
                .sorted((o1, o2) -> Math.toIntExact(o2.getCount() - o1.getCount()))
                .map(HashtagDTO::from).toList();


        if (hashtagList.isEmpty()) {
            hashtagList = this.getHashtag(keyword)
                    .map(List::of)
                    .orElseGet(ArrayList::new);
        }

        return hashtagList;
    }

    private Optional<HashtagDTO> getHashtag(String keyword) {
        return hashtagRepository.findByHashtagEquals(keyword)
                .map(HashtagDTO::from);
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
            throw new BusinessException(
                    ErrorCode.CONFLICT_HASHTAG,
                    existHashtagList.stream().map(Hashtag::getHashtag)
                            .reduce((s, s2) -> s + ", " + s2).orElse("")
            );
        }
    }
}
