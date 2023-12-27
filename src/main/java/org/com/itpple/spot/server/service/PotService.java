package org.com.itpple.spot.server.service;

import java.util.List;
import org.com.itpple.spot.server.dto.pot.PotDTO;
import org.com.itpple.spot.server.dto.pot.SearchCondition.SearchRange;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(Long userId, String fileName);

    CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest);

    List<PotDTO> getPotListWithoutExpired(SearchRange searchRange, Long categoryId);

    List<PotDTO> getPotList(SearchRange searchRange, Long categoryId);
}
