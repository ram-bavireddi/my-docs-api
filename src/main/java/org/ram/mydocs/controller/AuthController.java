package org.ram.mydocs.controller;

import lombok.RequiredArgsConstructor;
import org.ram.mydocs.resource.UserResource;
import org.ram.mydocs.service.DriveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final DriveService driveService;

    @PostMapping("/google/signin")
    public UserResource doGoogleSignin(@RequestBody UserResource user) {
        String authorizationUrl = driveService.createAuthorizationUrl(user.getEmail());
        user.setAuthorizationUrl(authorizationUrl);
        return user;
    }

    @GetMapping("/oauth")
    public void storeCredential(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String username = new String(Base64.getDecoder().decode(request.getParameter("state")));
        String page = "index.html";
        if (code != null) {
            String accessToken = driveService.storeCredential(code, username);
            page = "dashboard.html?accessToken=" + accessToken;
        }
        response.sendRedirect(page);
    }
}
