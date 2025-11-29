package webcamera.com.vn.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_products")
public class ShopProduct {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "image")
    private String image;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "standard_cost")
    private Double standardCost;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "quantity_per_unit")
    private String quantityPerUnit;

    @Column(name = "discontinued")
    private Boolean discontinued;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "created_at")
    private LocalDateTime create_at;

    @Column(name = "updated_at")
    private LocalDateTime update_at;
}
