package org.example.controller.validation;

import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.model.Task;

public interface TaskInterfaceValidation {
    ResultValidationEnum validateTask(Task task);
}