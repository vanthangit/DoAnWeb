package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import vn.iotstar.AloTra.dto.BranchDTO;
import vn.iotstar.AloTra.entity.Branch;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    BranchDTO toBranchDTO(Branch branch);

    Branch toBranch(BranchDTO branchDTO);

}
