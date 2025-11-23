package webcamera.com.vn.webapp.DTO.ShopProductImageDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductImageUpdateRequestDTO {
    @NotNull(message = "id san pham ko duoc de trong")
    private Integer productId;

    @NotBlank(message = "hinh ko duoc de trong")
    private String image;
}
