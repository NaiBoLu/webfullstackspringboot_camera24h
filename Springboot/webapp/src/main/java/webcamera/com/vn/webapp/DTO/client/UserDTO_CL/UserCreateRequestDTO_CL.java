package webcamera.com.vn.webapp.DTO.client.UserDTO_CL;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequestDTO_CL {
   
    @NotBlank(message = "khogn dc de username trong")
    @Length(min = 3, max = 70, message = "ten username phai co it nhat la 3 ky tu")
    private String username;

    @NotBlank(message = "mat khau khong dc de trong")
    @Length(min = 6, max = 128, message = "mk it nhat la phai co 6 -128 ky tu")
    private String password;

    @NotBlank(message = "Khong dc de trong email")
    private String email;

}
