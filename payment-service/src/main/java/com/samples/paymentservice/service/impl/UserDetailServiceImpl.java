package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.persistance.entity.User;
import com.samples.paymentservice.persistance.repository.UserJpaRepository;
import com.samples.paymentservice.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Service("UserDetailServiceImpl")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserJpaRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDto(user);
    }
}
