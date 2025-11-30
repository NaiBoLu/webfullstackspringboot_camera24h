package webcamera.com.vn.webapp.DTO.admin.ShopSupplierDTO_AD;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopSupplierCreateRequestDTO_AD {
    @NotBlank(message = "Mã ko duoc de trống")
    @Length(max = 50)
    private String supplierCode;

    @NotBlank(message = "tên ko supplier ko dc de trong")
    @Length(min = 3, max = 70, message = "ten shop phai co it nhat  3 ky tu")
    private String supplierName;

    private String description;

    private String image;
}
