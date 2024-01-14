package org.com.itpple.spot.server.global.s3.service;

public interface FileService {

    String getPreSignedUrl(String fileName);

    boolean isUploaded(String fileName);
}
