package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;


import com.example.ihmidtermprojectbanksystemapi.dto.AdminDTO;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Admin;
import com.example.ihmidtermprojectbanksystemapi.model.utils.PasswordUtil;
import com.example.ihmidtermprojectbanksystemapi.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/new")
    public Admin store(@RequestBody AdminDTO adminDTO) {
        Admin admin = new Admin(adminDTO.getUsername(), PasswordUtil.encryptedPassword(adminDTO.getPassword()));
        return adminRepository.save(admin);
    }
}
