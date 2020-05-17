package com.kokotripadmin.viewmodel.login;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginVm {

    @Email(message = "유효한 이메일 주소를 입력해주세요")
    @NotEmpty(message = "이메일 주소를 입력해주세요")
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호는 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
