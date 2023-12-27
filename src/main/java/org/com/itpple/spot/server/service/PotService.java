package org.com.itpple.spot.server.service;

import java.util.List;
import org.com.itpple.spot.server.dto.PotDto;
import org.com.itpple.spot.server.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(Long userId, String fileName);

    CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest);

    List<PotDto> getPotListWithoutExpired(SearchRange searchRange, Long categoryId);

    List<PotDto> getPotList(SearchRange searchRange, Long categoryId);
}
