package org.com.itpple.spot.server.dto.pot;

public record UploadImageResponse(String preSignedUrl) {

    public static UploadImageResponse of(String preSignedUrl) {
        return new UploadImageResponse(preSignedUrl);
    }
}
