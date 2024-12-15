package vn.iotstar.AloTra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.AloTra.entity.Branch;
import vn.iotstar.AloTra.entity.Inventory;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.repository.BranchRepository;
import vn.iotstar.AloTra.repository.InventoryRepository;
import vn.iotstar.AloTra.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

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

    @Transactional
    public void updateAllToQuantity(int quantity) {
        List<Product> allProducts = productRepository.findAll();
        List<Branch> allBranches = branchRepository.findAll();

        for (Branch branch : allBranches) {
            for (Product product : allProducts) {
                Inventory existingInventory = inventoryRepository.findByBranchAndProduct(branch, product);

                if (existingInventory == null) {
                    Inventory newInventory = new Inventory();
                    newInventory.setBranch(branch);
                    newInventory.setProduct(product);
                    newInventory.setQuantity(quantity);
                    inventoryRepository.save(newInventory);
                } else {
                    existingInventory.setQuantity(quantity);
                    inventoryRepository.save(existingInventory);
                }
            }
        }
    }
}

