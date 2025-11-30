package webcamera.com.vn.webapp.entity.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_stores")
public class ShopStoreAD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "store_code")
    private String storeCode;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
}
