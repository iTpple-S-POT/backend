package org.com.itpple.spot.server.dto.pot;

public record UploadImageResponse(String preSignedUrl, String fileKey) {

    public static UploadImageResponse of(String preSignedUrl, String fileKey) {
        return new UploadImageResponse(preSignedUrl, fileKey);
    }
}
