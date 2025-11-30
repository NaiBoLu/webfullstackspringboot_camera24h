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
@Table(name = "Shop_exports")
public class ShopExportAD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Column(name = "employee_id", nullable = false)
    private int employeeId;

    @Column(name = "export_date")
    private LocalDateTime exportDate;

    @Column(name = "description")
    private String description;

    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;


}
