package vn.iotstar.AloTra.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.Inventory;
import vn.iotstar.AloTra.service.impl.InventoryService;
import vn.iotstar.AloTra.service.impl.UserService;

import java.util.List;

@Controller
@RequestMapping("/employee/inventory")
public class InventoryManagementController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getInventoriesByBranchId(Model model) {
        UserDTO currentUser = userService.getMyInfo();
        Long branchId = currentUser.getBranch_id();
        List<Inventory> inventories = inventoryService.getInventoriesByBranchId(branchId);
        model.addAttribute("inventories", inventories);
        return "employee/inventory-list";
    }

    @GetMapping("/edit/{id}")
    public String editInventoryForm(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "employee/edit-inventory";
    }

    @PostMapping("/edit")
    public String editInventory(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/employee/inventory";
    }

}
