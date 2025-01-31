package org.example.controller;

import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.controller.validation.Impl.UserValidation;
import org.example.controller.validation.UserInterfaceValidation;
import org.example.model.User;
import org.example.repository.TaskDAO;
import org.example.repository.UserDAO;

import java.sql.Connection;
import java.util.List;

public class UserController {
    private final UserInterfaceValidation userInterfaceValidation;
    private final TaskDAO taskProxyDAO;
    private final UserDAO usuarioProxy;

    public UserController(Connection connection) {
        this.usuarioProxy = new UserDAO(connection);
        this.taskProxyDAO = new TaskDAO(connection);
        this.userInterfaceValidation = new UserValidation();
    }

    public User login(User userDTO) {
        ResultValidationEnum resultValidation = userInterfaceValidation.validateUserLogin(userDTO);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return null;
        }

        User usuario = usuarioProxy.userLogin(userDTO);

        resultValidation = userInterfaceValidation.userIsNotNull(usuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {

            return null;
        }
        return usuario;
    }

    public ResultValidationEnum register(User userDTO) {
        ResultValidationEnum resultValidation = emailAlreadyExists(userDTO.getEmail());
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        User novoUsuario = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
        resultValidation = userInterfaceValidation.validateUser(novoUsuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        usuarioProxy.insertUser(novoUsuario);
        return ResultValidationEnum.APPROVED;
    }

    private ResultValidationEnum emailAlreadyExists(String email) {
        List<User> usuarios = usuarioProxy.getAllUsers();
        return userInterfaceValidation.emailAlreadyExists(usuarios, email);
    }
}