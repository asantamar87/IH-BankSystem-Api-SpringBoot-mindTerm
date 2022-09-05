package com.example.ihmidtermprojectbanksystemapi.repository;

import com.example.ihmidtermprojectbanksystemapi.model.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository <Role,Long > {

}
