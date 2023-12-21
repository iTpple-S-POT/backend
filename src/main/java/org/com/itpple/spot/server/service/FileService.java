package org.com.itpple.spot.server.service;

public interface FileService {

    String getPreSignedUrl(String fileName);

    boolean isUploaded(String fileName);
}
