package com.ikaansh.hardwarecatalog.service;

import com.ikaansh.hardwarecatalog.dto.request.LoginRequest;
import com.ikaansh.hardwarecatalog.dto.request.RegisterRequest;
import com.ikaansh.hardwarecatalog.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}