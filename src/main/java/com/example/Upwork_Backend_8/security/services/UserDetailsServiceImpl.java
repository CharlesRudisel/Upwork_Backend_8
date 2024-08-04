package com.example.Upwork_Backend_8.security.services;



import com.example.Upwork_Backend_8.users.entity.UserInfo;
import com.example.Upwork_Backend_8.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Entering in loadUserByUsername Method...");
        Optional<UserInfo> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            System.out.println("Username not found: " + username);
            throw new UsernameNotFoundException("could not found users..!!");
        }
        System.out.println("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user.orElse(null));
    }


}
