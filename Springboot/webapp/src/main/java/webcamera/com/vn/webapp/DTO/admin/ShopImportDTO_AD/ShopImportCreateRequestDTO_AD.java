package webcamera.com.vn.webapp.DTO.admin.ShopImportDTO_AD;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportCreateRequestDTO_AD {

    @NotNull(message = "store id ko duoc de trong")
    private Integer storeId;

    @NotNull(message = "employee id ko duoc de trong")
    private Integer employeeId;

    private LocalDateTime importDate;
}
