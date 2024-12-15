package vn.iotstar.AloTra.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vn.iotstar.AloTra.configuration.JwtUtil;
import vn.iotstar.AloTra.dto.UserDTO;

@Component
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Value("${jwt.valid-duration}")
    private int VALID_DURATION;


    @Autowired
    public OAuth2SuccessHandler(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws java.io.IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info("User logged in via Google: {} ({})", name, email);

        // Lưu thông tin người dùng
        userService.saveUserInformation(name, email);

        // Tạo JWT
        var user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = authenticationService.generateToken(user);

        // Thêm cookie
        Cookie authCookie = new Cookie("auth_token", jwtToken);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        authCookie.setMaxAge(VALID_DURATION);
        response.addCookie(authCookie);

        request.getSession().setAttribute("myInfo", user);

        // Chuyển hướng sau khi xử lý xong
        response.sendRedirect("/");
    }
}
