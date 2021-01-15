package com.zource.service.user;

import com.zource.model.User;
import com.zource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //for new users only
    @Override
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) { return userRepository.findById(id).orElse(null); }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }


/*
    int setUserById(@Param("salt") String salt, @Param("id") Long id);


    @Override
    @Modifying
    @Query("update User u set u.firstName = :userr where u.id = :id")
    public User updateGeneralInfo(@Param("userr") User user) {
        return null;
    }*/

}
