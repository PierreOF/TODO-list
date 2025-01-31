package org.example.controller.validation.Impl;

import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.controller.validation.UserInterfaceValidation;
import org.example.model.User;

import java.util.List;

public class UserValidation implements UserInterfaceValidation {

    @Override
    public ResultValidationEnum validateUserLogin(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum userIsNotNull(User user) {
        if (user == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum validateUser(User user) {
        if (userIsNotNull(user) == ResultValidationEnum.REJECTED) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum emailAlreadyExists(List<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return ResultValidationEnum.REJECTED;
            }
        }
        return ResultValidationEnum.APPROVED;
    }
}