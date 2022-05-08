package com.commerce.shop.dto;

import com.commerce.shop.constant.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자리 이상, 16자리 이하로 입력해주세요.")
    private String pw1;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자리 이상, 16자리 이하로 입력해주세요.")
    private String pw2;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;

//    @DefaultValue(Role.USER)
//    private Role role = Role.USER;

}