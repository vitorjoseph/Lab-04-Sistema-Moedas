package com.main.sistema_moedas.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_STRING = "Usuário não encontrado";

    public UserNotFoundException() {
        super(MESSAGE_STRING);
    }

}