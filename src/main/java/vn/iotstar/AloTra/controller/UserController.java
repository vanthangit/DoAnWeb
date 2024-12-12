package vn.iotstar.AloTra.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return "customer/user-information";
    }

    @PostMapping("/edit-profile")
    public String updateUser(@ModelAttribute("userDTO") UserDTO userDTO, HttpSession session) {
        UserDTO currentUser = userService.getMyInfo();
        userService.updateUser(currentUser.getUser_id(), userDTO);

        UserDTO updatedUser = userService.getMyInfo();
        session.setAttribute("myInfo", updatedUser);
        return "redirect:/user/myInfo";
    }
}
