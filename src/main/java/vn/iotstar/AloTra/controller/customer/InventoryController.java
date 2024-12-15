package vn.iotstar.AloTra.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.entity.Inventory;
import vn.iotstar.AloTra.service.impl.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/checkStock")
    public ResponseEntity<Boolean> checkStock(@RequestParam Long productId, @RequestParam Long branchId, @RequestParam Integer quantity) {
        boolean inStock = inventoryService.checkStock(productId, branchId, quantity);
        return new ResponseEntity<>(inStock, HttpStatus.OK);
    }

    @PutMapping("/updateStock")
    public ResponseEntity<String> updateStock(@RequestParam Long productId, @RequestParam Long branchId, @RequestParam Integer quantity) {
        boolean updated = inventoryService.updateStock(productId, branchId, quantity);
        if (updated) {
            return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update stock", HttpStatus.BAD_REQUEST);
        }
    }
}

