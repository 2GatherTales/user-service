package com.joaogsrocha._SpringREST.services.implementation;

import com.joaogsrocha._SpringREST.model.user.User;
import com.joaogsrocha._SpringREST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl  {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User find(Long id) {
        return (User) userRepository.findById(id).get();
    }

    public User create(User user) {
        return (User) userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

}
