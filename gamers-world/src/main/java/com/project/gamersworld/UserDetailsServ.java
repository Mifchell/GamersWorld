// package com.project.gamersworld;

// import java.util.Collections;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// @Service
// public class UserDetailsServ implements UserDetailsService {
//     @Autowired
//     private UserRepo userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         User user = userRepository.findByProfileEmailAddress(email);

//         if (user == null) {
//             throw new UsernameNotFoundException("User not found with email: " + email);
//         }

//         return new org.springframework.security.core.userdetails.User(user.getProfile().getEmail(), user.getProfile().getPassword(), Collections.emptyList());
//     }
// }

