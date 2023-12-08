package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(String fileName);

    CreatePotResponse createPot(CreatePotRequest createPotRequest);
}
