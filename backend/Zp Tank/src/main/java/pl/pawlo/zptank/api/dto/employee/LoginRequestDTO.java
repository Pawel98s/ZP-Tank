package pl.pawlo.zptank.api.dto.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {


    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
