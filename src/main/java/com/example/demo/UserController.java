package com.example.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserJpaRepository userJpaRepository;

    public UserController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable long id){

        Optional<UserEntity> currentUserInBd = userJpaRepository.findById(id);

        if(currentUserInBd.isEmpty()){
            throw new RuntimeException("El usuario no existe");
        }

        return currentUserInBd.get();
    }

    @PostMapping()
    public UserEntity userRegisterandAddToCompany(@RequestBody UserRegisterCommand userRegisterCommand){

        return userJpaRepository.save(
                new UserEntity(
                        userRegisterCommand.getFirstName(),
                        userRegisterCommand.getLastName(),
                        userRegisterCommand.getEmail(),
                        userRegisterCommand.getIdentification()));
    }

    @PutMapping("/{id}")
    public UserEntity userEdit(@RequestBody UserRegisterCommand userEditCommand,
                         @PathVariable Long id){

        Optional<UserEntity> currentUserInBd = userJpaRepository.findById(id);

        if(currentUserInBd.isEmpty()){
            throw new RuntimeException("El usuario no existe");
        }

        UserEntity currentUser = currentUserInBd.get();

        return userJpaRepository.save(new UserEntity(
                currentUser.getId(),
                userEditCommand.getFirstName(),
                userEditCommand.getLastName(),
                userEditCommand.getEmail(),
                userEditCommand.getIdentification(),
                currentUser.getTotalPoints()));
    }


    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id){
         userJpaRepository.deleteById(id);
    }

}
