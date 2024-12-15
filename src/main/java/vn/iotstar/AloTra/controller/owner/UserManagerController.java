package vn.iotstar.AloTra.controller.owner;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.Branch;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.mapper.UserMapper;
import vn.iotstar.AloTra.repository.BranchRepository;
import vn.iotstar.AloTra.service.impl.UserService;

@Controller
@RequestMapping("/owner")
public class UserManagerController {

	@Autowired
	private UserService userService;
	
	@Autowired
    private UserMapper userMapper;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@GetMapping("/users")
	public String getUsersByRoleId(Model model, @RequestParam("roleId") Long roleId) {
		Set<User> users = userService.findUsersByRoleId(roleId);

		model.addAttribute("roleId", roleId);
		model.addAttribute("users", users);

		return "owner/users-list";
	}
	
    @GetMapping("/user/add")
    public String showAddEmployy(Model model) {
    	User user = new User();
    
        Branch branch = new Branch();
        user.setBranch(branch);  

        model.addAttribute("user", user);
        
        return "owner/users-add";
    }

    @PostMapping("/user/add/submit")
    public String addEmployyWithBranch(@ModelAttribute User user, @RequestParam(value = "branchName", required = false) String branchName, @RequestParam(value = "branchAddress", required = false) String branchAddress) {
        userService.addUser(user, branchName, branchAddress);  
        return "redirect:/owner/users?roleId=2";
    }	

	@GetMapping("user/edit/{id}")
	public String editUser(@PathVariable Long id, @RequestParam("roleId") Long roleId, Model model) {
	    User user = userService.findUserById(id);
	    Branch branch = branchRepository.findByBranchId(user.getBranch().getBranch_id());
	    model.addAttribute("branch", branch);
	    model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);  
	    return "owner/users-edit";
	}

	@PostMapping("/user/edit/submit")
	public String updateUser(@ModelAttribute User user, 
	                         @RequestParam("roleId") Long roleId, 
	                         @RequestParam("branch_name") String branchName, 
	                         @RequestParam("branch_address") String branchAddress, 
	                         RedirectAttributes redirectAttributes) {

	    userService.updateEmployee(user.getUser_id(), user, branchName, branchAddress);

	    redirectAttributes.addAttribute("roleId", roleId);
	    return "redirect:/owner/users";
	}


	@GetMapping("user/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, @RequestParam("roleId") Long roleId, RedirectAttributes redirectAttributes) {
	    userService.deleteUser(userId);
	    
	    redirectAttributes.addAttribute("roleId", roleId);  
	    return "redirect:/owner/users";  
	}

	@GetMapping("user/search")
	public String searchUserByEmail(Model model, @RequestParam("email") String email, @RequestParam("roleId") Long roleId) {
		Set<User> users = userService.searchUsersByEmailAndRoleId(email, roleId);
		model.addAttribute("roleId", roleId);
		model.addAttribute("users", users);
		return "owner/users-list";
	}
	

}
