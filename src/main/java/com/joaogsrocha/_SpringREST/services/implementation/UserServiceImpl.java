package com.joaogsrocha._SpringREST.services.implementation;

import com.joaogsrocha._SpringREST.model.roleuser.RoleUser;
import com.joaogsrocha._SpringREST.model.user.User;
import com.joaogsrocha._SpringREST.repository.RoleUserRepository;
import com.joaogsrocha._SpringREST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User find(Long id) {
        return (User) userRepository.findById(id).get();
    }

    public User create(User user) {
        user.setPw( "{bcrypt}"+ new BCryptPasswordEncoder().encode(user.getPw()));
        user.setEnabled(true);
        User nuser =  (User) userRepository.save(user);

        RoleUser nroleUser = new RoleUser();
        nroleUser.setUserid(user.getId());
        nroleUser.setRoleid(Long.parseLong("2"));
        roleUserRepository.save(nroleUser);

        return nuser;
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

}
