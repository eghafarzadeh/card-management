package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.persistance.entity.User;
import com.samples.paymentservice.persistance.repository.UserJpaRepository;
import com.samples.paymentservice.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("UserDetailPasswordServiceImpl")
public class UserDetailPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserJpaRepository userRepository;

    public UserDetailPasswordServiceImpl(
            UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        user.setPassword(newPassword);
        return new UserDto(user);
    }
}
