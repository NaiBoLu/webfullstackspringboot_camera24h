package webcamera.com.vn.webapp.DTO.admin.UserHasRolesDTO_AD;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHasRolesBatchCreateRequestDTO_AD {
    @NotNull(message = "con cec may de trong ta odam may")
    private Integer userId;

    @NotNull(message = "con cec may de trong ta odam may")
    private List<Integer> listRoleId;
}
