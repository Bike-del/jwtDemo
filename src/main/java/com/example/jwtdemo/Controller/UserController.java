package com.example.jwtdemo.Controller;

import com.example.jwtdemo.GlobalException.ResourceNotFoundException;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.service.UserService;
import com.example.jwtdemo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController()
public class UserController {

    @Autowired
    private UserService userService;




    @GetMapping("/{id}")
    public User userById(@PathVariable Long id){
        return userService.userById(id);
    }

    //get All
    @GetMapping("/allUsers")
    public List<User> allUsers(){
        return userService.getAllUser();
    }

    //update
    @PostMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id,user);
    }

    //delete
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication){
        String loggedUser = authentication.getName();
        User user = userService.userById(id);
        if(Objects.equals(user.getUserName(), loggedUser)){


            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cant not delete your own Account");
        }
         userService.deleteUser(id);
        return ResponseEntity.ok().build();

    }

//    //findByname
//    @GetMapping("/user/{name}")
//    public User userByName(@PathVariable String name){
//        return userService.findByName(name);
//    }

    //checkUsername
    @GetMapping("/check-username")
    public Map<String,Boolean> checkUsername(@RequestParam String username){
        boolean exist = userService.findByName(username);
        Map<String,Boolean> map = new HashMap<>();
        map.put("valid", !exist);  //formValidation take "valid: true/false"
        return map;


    }

}
