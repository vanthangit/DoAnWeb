package vn.iotstar.AloTra.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.iotstar.AloTra.dto.BranchDTO;
import vn.iotstar.AloTra.service.impl.BranchService;

import java.util.List;

@Controller
@RequestMapping("/api/branchs")
public class BranchController {


    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranch() {
        return new ResponseEntity<>(branchService.getAllBranch(), HttpStatus.OK);
    }
}
