package edu.bowiestateuni.groupproj.foodpantry.services.impl;

import edu.bowiestateuni.groupproj.foodpantry.dao.EmployeeDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.RoleTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.services.LoginService;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginRequestDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.dto.login.LoginResponseDTO;
import edu.bowiestateuni.groupproj.foodpantry.services.security.JWTService;
import edu.bowiestateuni.groupproj.foodpantry.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final UserDAO userDAO;
    private final JWTService jwtService;
    private final EmployeeDAO employeeDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO processLogin(final LoginRequestDTO loginRequest,  final HttpServletRequest servletRequest) {
        log.info("Login attempt from IP address {} , with email address {} ", WebUtils.extractClientRequestIP(servletRequest), loginRequest.getEmailAddress());
       final UserEntity userEntity =  userDAO.findByEmailAddress(loginRequest.getEmailAddress()).orElseThrow(() -> new IllegalArgumentException("Invalid email address or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            log.info("++++ Login failed due to incorrect password using email - [{}]  ", loginRequest.getEmailAddress());
            throw new IllegalArgumentException("Invalid email address or password");
        }
        return createToken(userEntity);
    }

    private LoginResponseDTO createToken(final UserEntity userEntity) {
        final int tokenExpiryInMins = 60;
        RoleTypeConstant employeeRole = RoleTypeConstant.CUSTOMER;
        if(userEntity.getUserType() != UserTypeConstant.CUSTOMER){
            final EmployeeEntity employee = employeeDAO.findByUser(userEntity).orElseThrow(() -> new IllegalArgumentException("Invalid user details provided"));
            employeeRole= employee.getEmployeeRole();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("userId", userEntity.getId().toString());
        tokenMap.put("email", userEntity.getEmailAddress());

        tokenMap.put("name", String.format("%s", userEntity.getName()));
        tokenMap.put("role", String.format("%s,%s", userEntity.getUserType(), employeeRole));
        tokenMap.put("access_token_expiry_time", tokenExpiryInMins+"");
        final String accessToken = jwtService.expiringToken(tokenMap,  tokenExpiryInMins);
        userEntity.setLastLoggedIn(ZonedDateTime.now());
        userDAO.save(userEntity);
        return new LoginResponseDTO(accessToken, userEntity.getUserType(), userEntity.getName(), employeeRole);
    }
}
