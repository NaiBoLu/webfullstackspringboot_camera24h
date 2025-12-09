package webcamera.com.vn.webapp.DTO.client.RoleHasPermissionsDTO_CL;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleHasPerBatchCreateRequestDTO_CL {
    @NotNull(message = "Khong duoc de trong role id")
    private Integer roleId;

    @NotNull(message = "Ko duoc de trong permission id")
    private List<Integer> permissionId;
}
