package de.telran.pizzaproject.service;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role findRoleByRoleName(RoleName roleName);

    Role add(Role role);
}
