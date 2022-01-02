package com.twogathertales.userservice.repository;

import com.twogathertales.userservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("roleuserRepository")
public interface RoleUserRepository<T> extends JpaRepository<User, Long> {

}