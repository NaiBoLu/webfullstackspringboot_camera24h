package webcamera.com.vn.webapp.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserHasPermisionResponseDTO {
   public String username;
   public String permisionName;
   public String displayName;
}
