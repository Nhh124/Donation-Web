package com.asm1.springboot.asm.service;

import com.asm1.springboot.asm.entity.Role;
import com.asm1.springboot.asm.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implement Service cho RoleService.
 *
 * Class này triển khai các phương thức được định nghĩa trong interface RoleService
 * và RoleRepository
 * để xử lý logic kinh doanh liên quan đến Donation.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id);
    }


}
