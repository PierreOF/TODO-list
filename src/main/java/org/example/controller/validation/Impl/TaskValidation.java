package org.example.controller.validation.Impl;

import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.controller.validation.TaskInterfaceValidation;
import org.example.model.Task;

public class TaskValidation implements TaskInterfaceValidation {
    @Override
    public ResultValidationEnum validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getStatus() == null) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getUserId() < 0) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getPriority() == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }
}