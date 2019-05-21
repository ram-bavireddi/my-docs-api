package org.ram.mydocs.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
        throw new AssertionError("No org.ram.mydocs.utils.FileUtils instances for you!");
    }

    public static File multipartToFile(MultipartFile multipart, String filename) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + filename);
        multipart.transferTo(file);
        return file;
    }
}
