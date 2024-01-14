package org.com.itpple.spot.server.domain.pot.dto.response;

public record UploadImageResponse(String preSignedUrl, String fileKey) {

    public static UploadImageResponse of(String preSignedUrl, String fileKey) {
        return new UploadImageResponse(preSignedUrl, fileKey);
    }
}
