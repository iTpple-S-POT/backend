package org.com.itpple.spot.server.domain.pot.api;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.service.HashtagService;
import org.com.itpple.spot.server.domain.pot.dto.HashtagDTO;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition;
import org.com.itpple.spot.server.domain.pot.dto.request.CreateHashtagRequest;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.request.UploadImageRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.GetCategoryResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;
import org.com.itpple.spot.server.domain.pot.service.PotService;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/pot")
@RequiredArgsConstructor
public class PotController {

    private final PotService potService;
    private final HashtagService hashtagService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PotDTO>> getPotList(
            @ModelAttribute("searchCondition") @Valid SearchCondition searchCondition) {
        return ResponseEntity.ok(potService.getPotList(searchCondition.getSearchRange(),
                searchCondition.getCategoryId(), searchCondition.getHashtagId()));
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

    @GetMapping(value = "/recently-viewed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PotDTO>> getPotListByRecentlyViewed(
            @Auth CustomUserDetails customUserDetails) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity.ok(potService.getPotListByRecentlyViewed(userId));
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
        return ResponseEntity.ok(potService.getPotListForAdmin(searchCondition.getSearchRange(),
                searchCondition.getCategoryId(), searchCondition.getHashtagId()));
    }


    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCategoryResponse> getCategory() {
        return ResponseEntity.ok(potService.getCategory());
    }

    @GetMapping(value = "/hashtag", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashtagDTO>> getHashtag(
            @RequestParam("keyword")
            @NotBlank
            @Size(min = 1, max = 50, message = "해시태그는 1~50자로 입력해주세요.")
            @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ_]*$", message = "해시태그는 한글, 영문, 숫자, 언더바만 입력 가능합니다.")
            String keyword,
            @RequestParam(value = "page", defaultValue = "0")
            @Min(value = 0, message = "페이지는 0부터 시작합니다.")
            int page
    ) {
        return ResponseEntity.ok(hashtagService.getHashtag(keyword, page));
    }

    @PostMapping(value = "/hashtag", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<HashtagDTO>> createHashtag(
            @Valid @RequestBody CreateHashtagRequest createHashtagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hashtagService.createHashtag(createHashtagRequest));
    }

    @PostMapping(value = "/image/pre-signed-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadImageResponse> uploadImageUsingPreSignedUrl(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody UploadImageRequest uploadImageRequest) {
        var userId = customUserDetails.getUserId();
        return ResponseEntity.ok(potService.uploadImage(userId, uploadImageRequest.fileName()));
    }
}
