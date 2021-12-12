package com.joaogsrocha._SpringREST.services.implementation;

import com.joaogsrocha._SpringREST.model.roleuser.RoleUser;
import com.joaogsrocha._SpringREST.repository.RoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleuserService")
public class RoleUserServiceImpl {

    @Autowired
    private RoleUserRepository roleuserRepository;

    public Iterable<RoleUser> findAll() {
        return roleuserRepository.findAll();
    }

    public RoleUser find(Long id) {
        return (RoleUser) roleuserRepository.findById(id).get();
    }

    public RoleUser create(RoleUser roleUser) {
        return (RoleUser) roleuserRepository.save(roleUser);
    }

    public void update(RoleUser roleUser) {
        roleuserRepository.save(roleUser);
    }

    public void delete(Long id) {
        roleuserRepository.delete(id);
    }

}
