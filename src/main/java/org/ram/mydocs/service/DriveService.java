package org.ram.mydocs.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import lombok.RequiredArgsConstructor;
import org.ram.mydocs.config.DriveConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveService {

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String APPLICATION_NAME = "Sample";

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final DriveConfig driveConfig;

    private GoogleAuthorizationCodeFlow flow;

    @PostConstruct
    public void init() throws IOException {
        GoogleClientSecrets secrets = GoogleClientSecrets.load(
                JACKSON_FACTORY, new InputStreamReader(driveConfig.getSecretKeys().getInputStream())
        );
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JACKSON_FACTORY, secrets, SCOPES)
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .build();
    }

    public String createAuthorizationUrl(String username) {
        return flow.newAuthorizationUrl()
                .setRedirectUri(driveConfig.getCallback())
                .setAccessType("offline").setState(Base64.getEncoder().encodeToString(username.getBytes()))
                .build();
    }

    public boolean isUserAuthenticated(String username) throws IOException {
        Credential credential = flow.loadCredential(username);
        return credential != null && credential.refreshToken();
    }

    public String storeCredential(String code, String username) throws IOException {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(driveConfig.getCallback()).execute();
        flow.createAndStoreCredential(response, username);
        Credential credential = flow.loadCredential(username);
        return credential.getAccessToken();
    }

    public Drive getDriveInstance(String username) throws IOException {
        Credential credential = flow.loadCredential(username);
        return new Drive.Builder(HTTP_TRANSPORT, JACKSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
