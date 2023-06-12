package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Role;
import de.telran.pizzaproject.repository.RoleRepository;
import de.telran.pizzaproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
