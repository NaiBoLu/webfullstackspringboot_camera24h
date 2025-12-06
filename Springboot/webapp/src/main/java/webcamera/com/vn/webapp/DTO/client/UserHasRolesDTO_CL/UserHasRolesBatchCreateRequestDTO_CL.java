package webcamera.com.vn.webapp.DTO.client.UserHasRolesDTO_CL;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHasRolesBatchCreateRequestDTO_CL {
    @NotNull(message = "con cec may de trong ta odam may")
    private Integer userId;

    @NotNull(message = "con cec may de trong ta odam may")
    private List<Integer> listRoleId;
}
