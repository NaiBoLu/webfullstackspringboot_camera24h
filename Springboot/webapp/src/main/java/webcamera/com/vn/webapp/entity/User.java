package webcamera.com.vn.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private Integer gender;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    //avatar
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "level_id")
    private Integer levelId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "is_active")
    private Integer isActive;

    // updatable = false: không cho phép cập nhật sau khi tạo đảm bảo tính truy vết lịch sử
    /*@CreationTimestamp:Tự động gán giá trị thời gian hiện tại (NOW()) cho trường này khi
     bản ghi được chèn (INSERT) vào CSDL.*/
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Khi INSERT: = NOW()

    /*@UpdateTimestamp:	Tự động cập nhật giá trị thời gian hiện tại (NOW()) cho trường này
    mỗi khi bản ghi được cập nhật (UPDATE).*/
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// Khi INSERT: = NOW()

    @OneToMany(mappedBy = "user")
    private List<UserHasRoles> userHasRoles;

    /*******ĐOẠN NÀY LẤY DANH SÁCH VAI TRÒ giữa các table user,
     * role, user_has_role
     * --> sử dụng annotation lk bằng ManyToMany thay cho câu lênh 
     * sql là: 
     *     select * from users u
     *     join user_has_roles uhr on uhr.user_id = u.id
     *     join roles r on r.role_id = u.id
     *      where..<dk can xet>
     * (fetch = FetchType.EAGER): Trong file UserDetailsServiceImpl, bạn thực hiện logic này:
        Lấy User từ Database.
        Dùng stream() trên getListRoles() để nạp vào JWT.
        Nếu là LAZY, danh sách Role lúc đó giống như một cái "hộp rỗng" chờ được đổ đầy. Nhưng vì quy trình tạo JWT diễn ra rất nhanh, Hibernate chưa kịp đổ dữ liệu vào hộp thì Token đã ký xong mất rồi. Kết quả là "role": "".
        Khi bạn đổi sang EAGER:
        Câu lệnh SQL của bạn từ SELECT * FROM users sẽ biến thành một câu lệnh JOIN hoành tráng: SELECT * FROM users JOIN user_has_roles JOIN roles...
        Dữ liệu Role được nạp ngay lập tức vào đối tượng User.
        JWT bốc được dữ liệu thật và hiện ra chữ "role": "customer"   
     * ****/
    @ManyToMany(fetch = FetchType.EAGER) // Đổi từ mặc định sang EAGER
    @JoinTable(name = "user_has_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
     private List<Role> listRoles;
}
