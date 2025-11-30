package webcamera.com.vn.webapp.DTO.admin.ShopStoreDTO_AD;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopStoreUpdateRequestDTO_AD {

    @NotBlank(message = "Mã ko được để trống")
    @Length(max = 50)
    private String storeCode;

    @NotBlank(message = "tên shop ko được để trống")
    @Length(min = 3, max = 70, message = "ten shop phai co it nhat la 3 ky tu")
    private String storeName;

    private String description;

    private String image;
}
