package org.com.itpple.spot.server.global.s3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.s3.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    //TODO: 프론트에서 S3 이미지 올라갔는지 확인하는 api(s3 테스트 후 삭제)
    @GetMapping(value = "/image/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkImage(
            @RequestParam(name = "fileName") String fileName) {
        return ResponseEntity.ok(fileService.isUploaded(fileName));
    }
}
