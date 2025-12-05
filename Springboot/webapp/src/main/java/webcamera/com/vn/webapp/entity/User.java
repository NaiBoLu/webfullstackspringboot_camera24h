package webcamera.com.vn.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private Long gender;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    //avatar
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "level_id")
    private int levelId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "is_active")
    private Long isActive;

    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
}
