package vn.iotstar.AloTra.controller;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.dto.auth.*;
import vn.iotstar.AloTra.service.impl.AuthenticationService;
import vn.iotstar.AloTra.service.impl.UserService;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected int VALID_DURATION;
    @Autowired
    private JwtDecoder jwtDecoder;

    @GetMapping("/form-login")
    public String showFormLogin(Model model) {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        model.addAttribute("authDTO", authenticationDTO);
        return "customer/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("authDTO") AuthenticationDTO authenticationDTO, Model model, HttpServletResponse response, HttpSession session) throws JOSEException, ParseException {
        // Xác thực người dùng và lấy token
        AuthenticationResponse authenticationResult = authenticationService.authenticate(authenticationDTO);
        if(!authenticationResult.isAuthenticated()){
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "customer/login";
        }
        String token = authenticationResult.getToken();



        // Lưu token vào cookie
        Cookie authCookie = new Cookie("auth_token", token);
        authCookie.setHttpOnly(true); // Bảo mật
        authCookie.setPath("/"); // Đảm bảo cookie có sẵn cho mọi trang
        authCookie.setMaxAge(VALID_DURATION); // Đặt thời gian hết hạn của cookie
        response.addCookie(authCookie);

        // Cập nhật SecurityContextHolder với thông tin người dùng
        Jwt jwt = jwtDecoder.decode(token);
        List<GrantedAuthority> authorities = jwt.getClaimAsStringList("scope").stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Tạo đối tượng Authentication và gán vào SecurityContext
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO myInfo = userService.getMyInfo();
        session.setAttribute("myInfo", myInfo);

        if(myInfo.getRole_id()==3){
            return "redirect:/owner/home";
        } else if ( myInfo.getRole_id()==2) {
            return "redirect:/employee/home";
        } else if (myInfo.getRole_id()==1) {
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        try {
            // Lấy token từ cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("auth_token".equals(cookie.getName())) {
                        String token = cookie.getValue();

                        // Gọi service để vô hiệu hóa token
                        LogoutRequest logoutRequest = new LogoutRequest(token);
                        authenticationService.logout(logoutRequest);

                        // Xóa cookie chứa token
                        Cookie authCookie = new Cookie("auth_token", null);
                        authCookie.setHttpOnly(true);
                        authCookie.setPath("/");
                        authCookie.setMaxAge(0); // Hết hạn ngay lập tức
                        response.addCookie(authCookie);
                        break;
                    }
                }
            }

            // Xóa session và SecurityContext
            session.invalidate();
            SecurityContextHolder.clearContext();

            // Chuyển hướng đến trang đăng nhập hoặc trang chủ
            model.addAttribute("message", "Bạn đã đăng xuất thành công!");
        } catch (RuntimeException | JOSEException | ParseException ex) {
            model.addAttribute("error", "Đăng xuất thất bại: " + ex.getMessage());
        }
        return "redirect:/";
    }

}
