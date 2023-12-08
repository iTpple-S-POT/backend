package org.com.itpple.spot.server.service.impl;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements FileService {

    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String getPreSignedUrl(String fileName) {
        var putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(fileName).build();

        var preSignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        var preSignedRequest = s3Presigner.presignPutObject(preSignRequest);

        log.info("PreSigned URL: {}", preSignedRequest.url());
        log.info("Which HTTP method needs to be used when uploading: [{}]",
                preSignedRequest.httpRequest().method());

        return preSignedRequest.url().toString();
    }
}
