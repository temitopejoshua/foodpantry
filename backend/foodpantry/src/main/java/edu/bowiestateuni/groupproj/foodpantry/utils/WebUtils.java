package edu.bowiestateuni.groupproj.foodpantry.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class WebUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public static String extractClientRequestIP(HttpServletRequest servletRequest) {
        String ipAddress = null;
        if (servletRequest != null) {
            ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = servletRequest.getHeader("RemoteAddr");
            }
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = servletRequest.getHeader("x-real-ip");
            }
        }
        ipAddress = ipAddress != null && ipAddress.contains(",") ? ipAddress.split(",")[0] : ipAddress;
        return ipAddress;
    }
    public static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T asObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
