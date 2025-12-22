package webcamera.com.vn.webapp.DTO.admin.RoleDTO_AD;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateRequestDTO_AD {

    @NotBlank(message = "con cec ai cho may de trong")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String name;

    @NotBlank(message = "con cec ai cho may de trong")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String displayName;

    @NotBlank(message = "con cec ai cho may de trong ")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String guardName;

//    private LocalDateTime created_at = LocalDateTime.now();
//
//    private LocalDateTime updated_at = LocalDateTime.now();

}
