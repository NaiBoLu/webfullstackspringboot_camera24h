package webcamera.com.vn.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore // <--- Thêm Annotation này parrse json
    private  Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    @JsonIgnore // <--- Thêm Annotation này parrse json
    private Permission permission;

}
