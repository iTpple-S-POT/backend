package org.com.itpple.spot.server.domain.pot.service;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.GetCategoryResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(Long userId, String fileName);

    CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest);

    List<PotDTO> getPotListForAdmin(SearchRange searchRange, Long categoryId, Long hashtagId);

    List<PotDTO> getPotList(SearchRange searchRange, Long categoryId, Long hashtagId, Long userId);

    List<PotDTO> getPotListForMy(Long userId);

    PotDTO getPot(Long potId, Long userId);

    List<PotDTO> getPotListByRecentlyViewed(Long userId);
}
