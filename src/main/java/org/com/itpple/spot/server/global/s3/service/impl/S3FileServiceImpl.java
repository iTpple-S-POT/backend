package org.com.itpple.spot.server.global.s3.service.impl;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.s3.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements FileService {

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

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

        return preSignedRequest.url().toString();
    }

    @Override
    public boolean isUploaded(String fileName) {
        try {
            s3Client.headObject(builder -> builder.bucket(bucket).key(fileName));
        } catch (NoSuchKeyException e) {
            return false;
        }
        return true;
    }
}
