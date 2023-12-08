package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.pot.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.UploadImageResponse;

public interface PotService {

    GetCategoryResponse getCategory();

    UploadImageResponse uploadImage(String fileName);
}
