package webcamera.com.vn.webapp.DTO.admin.PermissionDTO_AD;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionUpdateRequestDTO_AD {
    @NotBlank(message = "name ko duoc de trong")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String name;

    @NotBlank(message = "name ko duoc de trong")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String displayName;

    @NotBlank(message = "name ko duoc de trong")
    @Size(min = 3, message = "name co do dai toi thieu 3 ky tu")
    private String guardName;
}
