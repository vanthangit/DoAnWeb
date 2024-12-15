package vn.iotstar.AloTra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.entity.Inventory;
import vn.iotstar.AloTra.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean checkStock(Long productId, Long branchId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return inventory != null && inventory.getQuantity() >= quantity;
    }

    public boolean updateStock(Long productId, Long branchId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        if (inventory != null && inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }

    public List<Inventory> getInventoriesByBranchId(Long branchId) {
        return inventoryRepository.findByBranchBranchId(branchId);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id " + id));
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}

