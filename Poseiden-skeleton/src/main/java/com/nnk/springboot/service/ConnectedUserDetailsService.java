package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

public interface ConnectedUserDetailsService {
    User getConnectedUser();
}
