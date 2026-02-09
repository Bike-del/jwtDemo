package com.example.jwtdemo.service;


import com.example.jwtdemo.GlobalException.ResourceNotFoundException;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository repo;
    
    
    @Autowired
    private AuthenticationManager authenticationManager;


    public List<User> getAllUser(){
        return repo.findAll();
    }

    public User userById(Long id){
//        internally it takes the supplier and use the get method. (lazy creation of exception)
        return repo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User not found for this id:"+id));


//        return repo.findById(id).orElseThrow(new Supplier<ResourceNotFoundException>() {
//            @Override
//            public ResourceNotFoundException get() {
//                return new ResourceNotFoundException("not found");
//            }
//        });


    }

    public User saved(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Normalize role


//        String role = user.getRole().toUpperCase();
          String role = "USER";


        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        user.setRole(role);

        return repo.save(user);
    }

    public boolean userExist(User user) {
        return repo.findByUserName(user.getUserName()) != null;
    }

    //findByname
    public boolean findByName(String name){
       User user = repo.findByUserName(name);
        return user != null;
    }

    //update
    public User updateUser(Long id, User user){
         User oldUser = userById(id);
         oldUser.setUserName(user.getUserName());
         oldUser.setRole(user.getRole());

         return repo.save(oldUser);
    }


    //Delete
    public String deleteUser(Long id){
      repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found.."));
      repo.deleteById(id);
      return "Successfully deleted";
    }


}
