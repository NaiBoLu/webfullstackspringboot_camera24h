package webcamera.com.vn.webapp.exceptions;

/*
* class VailationErrorResponse nay lam gi?
*  +. lop nay sinh ra nham muc dich ghi nhan cac constrint validation
*  , constraint validation: chinh la cac rang buot thiet lap bat loi neu vi pham o lop DTO
* (@NOtnull, @NotBlank, @Email.....)
*  + lop nay sinh ra de ghi nhan cac thiet lap o day va tien hanh goi vao class GlobalException de
* nhan vaf xu ly loi -> sau do no tra ve cac loi ghi nhan loi -> anh xa tt loi len lop violation de
* hien thi thong bao loi la gi va o filename nao bi loi
*máy mày quá lag.. mang lag quá khng gõ đc gì
* */

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {
    //tao ra list chua cac loi ghi nhan cac messag e loi va filename cuj the cua cac file tu class vilolation
    private List<Violations> violations = new ArrayList<>();
}
