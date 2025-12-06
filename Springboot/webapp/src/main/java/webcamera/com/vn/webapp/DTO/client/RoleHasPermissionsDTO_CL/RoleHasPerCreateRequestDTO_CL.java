package webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleHasPerCreateRequestDTO_CL {
    @NotNull(message = "ko duoc de trong role id")
    private Integer roleId;

    @NotNull(message = "ko duoc de trong permission id")
    private Integer permissionId;

}
