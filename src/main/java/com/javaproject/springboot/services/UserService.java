package com.javaproject.springboot.services;


import com.javaproject.springboot.dto.UserRegistrationDTO;
import com.javaproject.springboot.repositories.UserRepository;
import com.javaproject.springboot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User registerNewUserAccount(UserRegistrationDTO userDto) {
        if (usernameExists(userDto.getName())) {
            throw new RuntimeException("There is an account with that username: " + userDto.getName());
        }
        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("USER");

        return userRepository.save(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByName(username).isPresent();
    }

}
