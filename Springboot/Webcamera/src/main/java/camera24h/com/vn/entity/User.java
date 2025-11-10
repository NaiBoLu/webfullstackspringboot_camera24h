package camera24h.com.vn.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
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

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    private Long gender;
    private String email;
    private LocalDate birthday;

    //avatar
    private String avatar;

    private String code;
    private String jobTitle;
    private String department;
    private String phone;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String rememberToken;
    private Long status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
