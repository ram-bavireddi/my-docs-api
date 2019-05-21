package org.ram.mydocs.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ram.mydocs.exception.MyDocsException;
import org.ram.mydocs.exception.MyDocsExceptionHandler;
import org.ram.mydocs.resource.BaseResource;
import org.ram.mydocs.service.DriveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class DriveTokenFilter extends OncePerRequestFilter {

    private final DriveService driveService;
    private final MyDocsExceptionHandler myDocsExceptionHandler;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USERNAME_HEADER = "x-username";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isExcluded(request)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                validateToken(request);
            } catch (MyDocsException ex) {
                sendError(response, ex);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }

    private void validateToken(HttpServletRequest request) throws IOException {
        String token = getToken(request.getHeader(AUTHORIZATION_HEADER));
        String username = request.getHeader(USERNAME_HEADER);
        if (token == null) {
            throw new MyDocsException("access token not found in the headers", HttpStatus.UNAUTHORIZED);
        }
        if (username == null) {
            throw new MyDocsException("username not found in the headers", HttpStatus.UNAUTHORIZED);
        }
        if (!driveService.getAccessToken(username).equals(token)
                || !driveService.isUserAuthenticated(username)) {
            throw new MyDocsException("invalid access token", HttpStatus.UNAUTHORIZED);
        }
    }

    private String getToken(String header) {
        if (header == null) {
            return null;
        } else {
            String[] split = header.split(" ");
            if (split.length < 2) {
                return null;
            } else {
                if (!split[0].equals("Bearer")) {
                    return null;
                }
                return split[1];
            }
        }
    }

    private boolean isExcluded(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !requestURI.contains("api");
    }

    private void sendError(HttpServletResponse response, MyDocsException ex) throws IOException {
        BaseResource error = myDocsExceptionHandler.handle(ex);
        response.setStatus(200);
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(error));
        out.flush();
    }
}
