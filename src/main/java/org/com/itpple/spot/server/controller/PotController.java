package org.com.itpple.spot.server.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.dto.PotDto;
import org.com.itpple.spot.server.dto.SearchCondition;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.request.UploadImageRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.service.PotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PotDto>> getPotList(
            @ModelAttribute("searchCondition") @Valid SearchCondition searchCondition) {
        return ResponseEntity.ok(potService.getPotList(searchCondition.getSearchRange(),
                searchCondition.getCategoryId()));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePotResponse> createPot(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody CreatePotRequest createPotRequest) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity.ok(potService.createPot(userId, createPotRequest));
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PotDto>> getPotListWithoutExpired(
            @ModelAttribute("searchCondition") @Valid SearchCondition searchCondition
    ) {
        return ResponseEntity.ok(potService.getPotListWithoutExpired(
                searchCondition.getSearchRange(), searchCondition.getCategoryId()));
    }


    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCategoryResponse> getCategory() {
        return ResponseEntity.ok(potService.getCategory());
    }

    @PostMapping(value = "/image/pre-signed-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadImageResponse> uploadImageUsingPreSignedUrl(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody UploadImageRequest uploadImageRequest) {
        var userId = customUserDetails.getUserId();
//        var userId = 1L;
        return ResponseEntity.ok(potService.uploadImage(userId, uploadImageRequest.fileName()));
    }
}
