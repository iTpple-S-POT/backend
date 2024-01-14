package org.com.itpple.spot.server.domain.pot.api;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.request.UploadImageRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.GetCategoryResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;
import org.com.itpple.spot.server.domain.pot.service.PotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/pot")
@RequiredArgsConstructor
public class PotController {

    private final PotService potService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PotDTO>> getPotList(
            @ModelAttribute("searchCondition") @Valid SearchCondition searchCondition) {
        return ResponseEntity.ok(potService.getPotList(searchCondition.getSearchRange(),
                searchCondition.getCategoryId()));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePotResponse> createPot(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody CreatePotRequest createPotRequest) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(potService.createPot(userId, createPotRequest));
    }

    @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PotDTO>> getPotListForMy(
            @Auth CustomUserDetails customUserDetails) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity.ok(potService.getPotListForMy(userId));
    }

    @GetMapping(value = "/{potId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PotDTO> getPot(
            @Auth CustomUserDetails customUserDetails,
            @PathVariable("potId") Long potId) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity.ok(potService.getPot(potId, userId));
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PotDTO>> getPotListForAdmin(
            @ModelAttribute("searchCondition") @Valid SearchCondition searchCondition
    ) {
        return ResponseEntity.ok(potService.getPotListForAdmin(
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
        return ResponseEntity.ok(potService.uploadImage(userId, uploadImageRequest.fileName()));
    }
}
