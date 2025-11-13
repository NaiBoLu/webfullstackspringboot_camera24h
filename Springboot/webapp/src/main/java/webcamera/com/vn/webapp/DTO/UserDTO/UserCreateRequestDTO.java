package webcamera.com.vn.webapp.DTO.UserDTO;

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
public class UserCreateRequestDTO {
    @NotBlank(message = "khogn dc de username trong")
    @Length(min = 3, max = 70, message = "ten username phai co it nhat la 3 ky tu")
    private String username;

    @NotBlank(message = "mat khau khong dc de trong")
    @Length(min = 6, max = 128, message = "mk it nhat la phai co 6 -128 ky tu")
    private String password;

    @NotBlank(message = "khong de trong cot nay")
    @Length(max = 200)
    private String lastName;

    @NotBlank(message = "khong de trong cot nay")
    @Length(max = 200)
    private String firstName;

    @NotBlank(message = "khong dc de trong email")
    @Email(message = "phai dung cau truc email la <name@gmail.com>")
    private String email;

    @Pattern(message = "phai dung cau truc sdt; xxxx-xxx-xxx", regexp = "^\\d{4}\\d{3}\\d{3}$")
    private String phone;

    @NotBlank(message = "khong dc de trong dia chi nha may")
    @Length(max = 500)
    private String address;

    @NotNull(message = "khong dc de trong")
    private Long status;
}
