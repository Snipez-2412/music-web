package org.project.musicweb.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class StorageService {
    private static final String BUCKET_NAME = "music-web-project";
    private final Storage storage;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName).build();
        storage.create(blobInfo, file.getBytes());
        return fileName;
    }

    public String generateSignedUrl(String fileName) {
        try {
            URL signedUrl = storage.signUrl(
                    BlobInfo.newBuilder(BUCKET_NAME, fileName).build(),
                    10, TimeUnit.MINUTES
            );
            return signedUrl.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
