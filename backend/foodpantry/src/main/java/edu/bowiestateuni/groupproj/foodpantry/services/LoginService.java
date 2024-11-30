package edu.bowiestateuni.groupproj.foodpantry.services;

import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponseDTO processLogin(LoginRequestDTO loginRequest, HttpServletRequest servletRequest);

}
