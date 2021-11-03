package com.jwt.refresh.token.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
