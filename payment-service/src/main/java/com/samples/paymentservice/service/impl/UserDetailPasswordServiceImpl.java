package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.persistance.entity.User;
import com.samples.paymentservice.persistance.repository.UserJpaRepository;
import com.samples.paymentservice.service.ModelConverter;
import com.samples.paymentservice.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Qualifier("UserDetailPasswordServiceImpl")
public class UserDetailPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserJpaRepository userRepository;

    private final ModelConverter modelConverter;

    public UserDetailPasswordServiceImpl(
            UserJpaRepository userRepository, ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        user.setPassword(newPassword);
        return new UserDto(user);
    }
}
