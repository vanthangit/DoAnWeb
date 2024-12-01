package vn.iotstar.AloTra.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.service.impl.UserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/form-register")
    public String showFormRegister(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "customer/register";
    }

    @GetMapping("/form-forgot")
    public String showFormForgot() {
        return "customer/forgot-pass";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            return "customer/register";
        }
        userService.createUser(userDTO);
        return "redirect:/auth/form-login";
    }

    @GetMapping("/myInfo")
    public String showMyInfo(Model model) {
        UserDTO  userDTO = userService.getMyInfo();
        model.addAttribute("userDTO", userDTO);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return "customer/user-information";
    }
}
