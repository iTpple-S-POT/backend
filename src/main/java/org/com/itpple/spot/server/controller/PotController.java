package org.com.itpple.spot.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.UploadImageRequest;
import org.com.itpple.spot.server.dto.pot.UploadImageResponse;
import org.com.itpple.spot.server.service.PotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pot")
@RequiredArgsConstructor
public class PotController {

    private final PotService potService;

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCategoryResponse> getCategory() {
        return ResponseEntity.ok(potService.getCategory());
    }

    @PostMapping(value = "/upload-image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestBody UploadImageRequest uploadImageRequest) {
        return ResponseEntity.ok(potService.uploadImage(uploadImageRequest.fileName()));
    }
}
