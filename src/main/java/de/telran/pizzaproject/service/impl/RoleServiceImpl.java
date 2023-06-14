package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Role;
import de.telran.pizzaproject.repository.RoleRepository;
import de.telran.pizzaproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleByRoleName(RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }
}
