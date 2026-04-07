package com.hackathon.estoque.service;

import com.hackathon.estoque.model.entity.Role;
import com.hackathon.estoque.model.enums.RoleType;
import com.hackathon.estoque.repository.RoleRepository;
import com.hackathon.estoque.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleType(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleType.getValue()));
    }

    public Role createRoleIfNotExists(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseGet(() -> {
                    Role newRole = new Role(roleType);
                    return roleRepository.save(newRole);
                });
    }

    public void initializeDefaultRoles() {
        createRoleIfNotExists(RoleType.ADMIN);
        createRoleIfNotExists(RoleType.USER);
    }
}
