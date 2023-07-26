package com.ssamz.jblogweb.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Username이 전달되지 않았습니다.")
    @NotBlank(message = "Username은 필수 입력 항목입니다.")
    @Size(min = 1, max = 20, message = "Username은 한 글자 이상 20자 미만으로 입력하세요.")
    private String username;

    @NotNull(message = "Emial이 전달되지 않았습니다.")
    @NotBlank(message = "Email은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotNull(message = "Password가 전달되지 않았습니다.")
    @NotBlank(message = "Password는 필수 입력 항목입니다.")
    @Size(min = 4, max = 20, message = "Password는 네 글자 이상 20자 미만으로 입력하세요.")
    private String password;
}
