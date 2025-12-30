package webcamera.com.vn.webapp.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    public String token;
    private String avatar;
    private String username;
    private Integer id;
}
