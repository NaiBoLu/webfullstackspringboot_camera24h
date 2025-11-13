package webcamera.com.vn.webapp.exceptions;

/*day la file chinh xu ly bat loi exception toan cuc cho project
* + violation chua ghi nhan cac file name vaf message loi
* + ValidationErrorRespone tao list(bon chua) tong hop va chua cac loi
* ==> tong hop loc lua, ghi nhan vaf thong bao loi cu the la gi va cua filename nao
* de nguoi dung client hieu va nam dc cai loi do la gi va o dau
* */

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* @ControllerAdvice: annotaion danh dau cho spring bit day la class xu ly loi exception va no tu anh xa cai exceptin
*  nay vao controller bit thong qua cai annotation @Valid
* @Order(Ordered.HIGHEST_PRECEDENCE): noi cho spring boot bit day la class exception co do uu tien cuc cao va can xu ly
* trc tien
* */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /*
    * + ConstraintViolationException: annotation dc su dung de bao hieu rang mot hoac nhieu rang buoc(constraint)
    * cua mot doi tuong da bi vi pham khi thuc hien kiem tra tich hop le -> tra ve list danh sach loi
    * + @ExceptionHandler: chi dinh method xu ly bad resuest tu client (note: trong method cua ConstraintViolationException
    * co rat nhieu method xu ly khac nhau vi vay chi dinh cu the method nao do se giup spring de thao tac hon)
    * + @ResponseStatus: Đây là annotation của Spring Framework. Nó báo cho Spring biết mã trạng thái HTTP nào cần gửi
    *  cùng với phản hồi (response).
    * + HttpStatus.INTERNAL_SERVER_ERROR: Đây là một mã trạng thái HTTP tiêu chuẩn, có giá trị là 500.
    * Mã 500 có nghĩa là "Lỗi Máy chủ Nội bộ" (Internal Server Error).
    * + @ResponseBody: chi dinh cho xu ly dl lay tu request body vi neu lien quan den form thi se lien
    * quan den method post nen value thg se luu o body
    * */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e){
      //goi va khoi  tao lop ghi nhan tra ve loi validationErrorResponse
        ValidationErrorResponse error = new ValidationErrorResponse();
        //tra ve ds cac loi
        for(ConstraintViolation violation : e.getConstraintViolations()){
            error.getViolations().add(
                    new Violations(violation.getPropertyPath().toString(), violation.getMessage())
            );
        }
        return error;
    }

}
