package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(Long userId, String fileName);

    CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest);
}
