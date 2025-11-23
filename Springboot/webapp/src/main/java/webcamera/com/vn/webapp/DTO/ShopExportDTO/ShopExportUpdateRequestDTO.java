package webcamera.com.vn.webapp.DTO.ShopExportDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopExportUpdateRequestDTO {
    @NotNull(message = "store id ko duoc de trong")
    private Integer storeId;

    @NotNull(message = "employee id ko duoc de trong")
    private Integer employeeId;

    private LocalDateTime exportDate;

    private String description;

    @NotNull(message = "order id ko duoc de trong")
    private Integer orderId;
}
