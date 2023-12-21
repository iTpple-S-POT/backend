package org.com.itpple.spot.server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.jwt.UserDetailsCustom;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.request.UploadImageRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.service.PotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping(value = "/image/pre-signed-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadImageResponse> uploadImageUsingPreSignedUrl(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
            @Valid @RequestBody UploadImageRequest uploadImageRequest) {
        var userId = userDetailsCustom.getUser().getId();
        return ResponseEntity.ok(potService.uploadImage(userId, uploadImageRequest.fileName()));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePotResponse> createPot(
            @AuthenticationPrincipal UserDetailsCustom userDetails,
            @Valid @RequestBody CreatePotRequest createPotRequest) {
        var userId = userDetails.getUser().getId();
        return ResponseEntity.ok(potService.createPot(userId, createPotRequest));
    }
}
