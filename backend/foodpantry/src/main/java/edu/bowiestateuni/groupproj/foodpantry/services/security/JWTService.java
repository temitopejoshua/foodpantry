package edu.bowiestateuni.groupproj.foodpantry.services.security;

import java.util.Map;

public interface JWTService {
    String expiringToken(Map<String, String> attributes, int minutes);

    Map<String, String> verify(String token);
}
