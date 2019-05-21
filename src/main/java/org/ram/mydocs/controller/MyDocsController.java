package org.ram.mydocs.controller;

import lombok.RequiredArgsConstructor;
import org.ram.mydocs.resource.FileItemResource;
import org.ram.mydocs.service.MyDocsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyDocsController {

    private final MyDocsService myDocsService;

    @GetMapping("/files")
    public List<FileItemResource> listFiles(HttpServletRequest request) throws IOException {
        String username = request.getHeader("x-username");
        return myDocsService.listFiles(username);
    }
}
