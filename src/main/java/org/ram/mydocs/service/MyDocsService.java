package org.ram.mydocs.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.ram.mydocs.resource.FileItemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyDocsService {

    private final DriveService driveService;

    public List<FileItemResource> listFiles(String username) throws IOException {
        Drive drive = driveService.getDriveInstance(username);
        FileList fileList = drive.files().list().setFields("files(id,name)").execute();
        return fileList.getFiles().stream().map(FileItemResource::from).collect(Collectors.toList());
    }
}
