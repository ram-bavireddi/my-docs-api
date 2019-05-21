package org.ram.mydocs.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.ram.mydocs.resource.FileResource;
import org.ram.mydocs.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyDocsService {

    private final DriveService driveService;

    public List<FileResource> listFiles(String username) throws IOException {
        Drive drive = driveService.getDriveInstance(username);
        FileList fileList = drive.files().list().setFields("files(id,name,mimeType)").execute();
        return fileList.getFiles().stream().map(FileResource::from).collect(Collectors.toList());
    }

    public FileResource uploadFile(String username, MultipartFile multipart) throws IOException {
        Drive drive = driveService.getDriveInstance(username);
        String filename = multipart.getOriginalFilename();
        java.io.File file = FileUtils.multipartToFile(multipart, filename);
        FileContent fileContent = new FileContent(multipart.getContentType(), file);
        File fileMetaData = new File();
        fileMetaData.setName(filename);
        File uploadedFile = drive.files().create(fileMetaData, fileContent).setFields("id,name").execute();
        return FileResource.from(uploadedFile);
    }

    public byte[] downloadFile(String username, String fileId) throws IOException {
        Drive drive = driveService.getDriveInstance(username);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
        return outputStream.toByteArray();
    }

    public FileResource createFolder(String username, FileResource file) throws IOException {
        Drive drive = driveService.getDriveInstance(username);
        File fileMetaData = new File();
        fileMetaData.setName(file.getName());
        fileMetaData.setMimeType("application/vnd.google-apps.folder");
        File folder = drive.files().create(fileMetaData).setFields("id,name").execute();
        return FileResource.from(folder);
    }
}
