package com.joaogsrocha._SpringREST.repository;

import com.joaogsrocha._SpringREST.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("roleuserRepository")
public interface RoleUserRepository<T> extends JpaRepository<User, Long> {

}