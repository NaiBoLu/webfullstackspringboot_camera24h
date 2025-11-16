package webcamera.com.vn.webapp.DTO.ShopCategoryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategoryUpdateRequestDTO {
    @NotBlank(message = "category code ko duoc de trong")
    @Length(max = 50, message = "category code ko duoc qua 100 ky tu")
    private String categoryCode;

    @NotBlank(message = "category name ko duoc de trong")
    @Length(min = 3, max = 50, message = "category name có ít nhát 3 ký tự")
    private String categoryName;

    private String description;

    private String image;
}
