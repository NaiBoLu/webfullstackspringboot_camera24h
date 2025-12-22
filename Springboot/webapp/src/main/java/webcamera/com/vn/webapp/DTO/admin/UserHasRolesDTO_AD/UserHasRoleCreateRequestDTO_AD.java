package webcamera.com.vn.webapp.DTO.admin.UserHasRolesDTO_AD;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHasRoleCreateRequestDTO_AD {
    @NotNull(message = "ko duoc de trong user id")
    private Integer userId;

    @NotNull(message = "ko duoc de trong role id")
    private Integer roleId;
}
