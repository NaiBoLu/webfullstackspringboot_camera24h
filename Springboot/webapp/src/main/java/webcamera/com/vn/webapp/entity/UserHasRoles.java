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
@Table(name = "user_has_roles")
public class UserHasRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // <--- Thêm Annotation này parrse json
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore // <--- Thêm Annotation này parrse json
    private Role role;
}
