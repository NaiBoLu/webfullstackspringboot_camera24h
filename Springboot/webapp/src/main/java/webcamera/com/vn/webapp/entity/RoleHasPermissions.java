package webcamera.com.vn.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_has_permissions")
public class RoleHasPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_id", nullable = false)
    private int roleId;

    @Column(name = "permission_id", nullable = false)
    private int permissionId;

}
