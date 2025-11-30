package webcamera.com.vn.webapp.DTO.admin.ProductDTO_AD;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductUpdateRequestDTOAD {
    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String image;

    private String shortDescription;

    private String description;

    @NotNull(message = "Standard cost ko duoc de trong")
    private Double standardCost;

    @NotNull(message = "List price ko duoc de trong")
    private Double listPrice;

    @NotBlank(message = "quy dinh so luong tren mot don vi ko duoc de trong")
    private String quantityPerUnit;

    private Boolean discontinued;

    private Boolean isFeatured;

    private Boolean isNew;

    @NotNull(message = "Category ID ko duoc de trong")
    private Integer categoryId;

    @NotNull(message = "Supplier ID ko duoc de trong")
    private Integer supplierId;
}
