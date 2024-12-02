package edu.bowiestateuni.groupproj.foodpantry.controller;

import edu.bowiestateuni.groupproj.foodpantry.services.LoginService;
import edu.bowiestateuni.groupproj.foodpantry.services.RegistrationService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginResponseDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.registration.RegistrationRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.usermgt.CustomerDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class SignUpSignInController {
    private final RegistrationService registrationService;
    private final LoginService loginService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> registerUser(@Valid @RequestBody final RegistrationRequestDTO registrationRequest) {
        final CustomerDTO responseDTO = registrationService.registerCustomer(registrationRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> processLogin(@Valid @RequestBody final LoginRequestDTO loginRequest, HttpServletRequest servletRequest, HttpServletResponse httpServletResponse) {
        final LoginResponseDTO responseDTO = loginService.processLogin(loginRequest, servletRequest);
        final Cookie cookie = new Cookie("Authorization", responseDTO.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(true));
        return ResponseEntity.ok(responseDTO);
    }
}