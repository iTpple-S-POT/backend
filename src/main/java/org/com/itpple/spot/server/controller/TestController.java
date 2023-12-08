package org.com.itpple.spot.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: 프론트와 연결 확인 후 제거
@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("test 확인 - develop");
    }
}
