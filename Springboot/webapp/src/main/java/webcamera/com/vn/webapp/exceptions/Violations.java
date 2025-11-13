package webcamera.com.vn.webapp.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*lop nay tao ra 2 properties nhan t hong tin vi pham
*  + filename
*  + message
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Violations {
    private String filename;
    private String message;
}
