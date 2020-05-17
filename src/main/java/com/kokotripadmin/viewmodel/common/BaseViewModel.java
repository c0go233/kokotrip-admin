package com.kokotripadmin.viewmodel.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class BaseViewModel {

    protected Integer id;
    protected String createdAt;
    protected String updatedAt;

    public BaseViewModel() {
    }

    public BaseViewModel(Integer id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
