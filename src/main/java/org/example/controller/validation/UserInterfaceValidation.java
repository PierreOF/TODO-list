package org.example.controller.validation;

import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.model.User;

import java.util.List;

public interface UserInterfaceValidation {
    ResultValidationEnum validateUserLogin(User userDTO);
    ResultValidationEnum userIsNotNull(User user);
    ResultValidationEnum validateUser(User user);
    ResultValidationEnum emailAlreadyExists(List<User> usuarios, String email);
}