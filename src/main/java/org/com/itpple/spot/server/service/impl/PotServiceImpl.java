package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.GetCategoryResponse;
import org.com.itpple.spot.server.repository.CategoryRepository;
import org.com.itpple.spot.server.service.PotService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotServiceImpl implements PotService {

    private final CategoryRepository categoryRepository;

    @Override
    public GetCategoryResponse getCategory(){
        return GetCategoryResponse.of(categoryRepository.findAll());
    }
}
