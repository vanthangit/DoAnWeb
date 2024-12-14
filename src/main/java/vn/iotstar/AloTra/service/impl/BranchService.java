package vn.iotstar.AloTra.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.BranchDTO;
import vn.iotstar.AloTra.mapper.BranchMapper;
import vn.iotstar.AloTra.repository.BranchRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;

    public List<BranchDTO> getAllBranch() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toBranchDTO)
                .collect(Collectors.toList());
    }

}
