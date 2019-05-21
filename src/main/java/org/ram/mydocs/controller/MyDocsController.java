package org.ram.mydocs.controller;

import lombok.RequiredArgsConstructor;
import org.ram.mydocs.resource.FileResource;
import org.ram.mydocs.service.MyDocsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyDocsController {

    private final MyDocsService myDocsService;

    @PostMapping("/files")
    public FileResource uploadFile(@RequestParam("file") MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        String username = request.getHeader("x-username");
        return myDocsService.uploadFile(username, file);
    }

    @GetMapping("/files")
    public List<FileResource> listFiles(HttpServletRequest request) throws IOException {
        String username = request.getHeader("x-username");
        return myDocsService.listFiles(username);
    }

    @GetMapping("/files/{id}")
    public byte[] downloadFile(@PathVariable("id") String fileId,
                               HttpServletRequest request) throws IOException {
        String username = request.getHeader("x-username");
        return myDocsService.downloadFile(username, fileId);
    }

    @PostMapping("folders")
    public FileResource createFolder(@RequestBody FileResource file,
                                     HttpServletRequest request) throws IOException {
        String username = request.getHeader("x-username");
        return myDocsService.createFolder(username, file);
    }
}
