package webcamera.com.vn.webapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import webcamera.com.vn.webapp.entity.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer> {
        
        //them method nay tra ve ten  username
        User findByUsername(String username);

        /* function tiem kiem load danh dsach role cung luc voi mot user tranh
        #1 chuan JPA: viet kieu chuan hibernate JPA
        #2 dung tot cho JWT + Security  
        #3 khong hack: hacker co gang tan cong cac doan ma sql doc hai vao trg nhap lieu(username) de nham muc dich thay doi hoacj huy bo sql goc cua ugn dung
        --> chong hack, ngan chan
         + WHERE u.username = !username: co che goi PreparedStatment dam bao rang gia tri cuar username luon dc coi la du lieu chuan 
         + --> ketqua: ngan dc cuoc tan cong sql tu hacker, giup truy van cua ban an toan va dung chuan 
         
        #4 khong bi loi lazy: lazy loading(tai tri hoan) la moi quan he giua user va role dc cau hinh de chi  tai khi can thiet tranh loi lazyInitializationException(loi xay ra khi ban co gang  truy cap du lieu lien quan, vd list role sau khi ket noi db dong)
        --> giai phap:
          +  LEFT JOIN FETCH u.listRoles: tu kho FETCH trong truy van cua ban buoc JPA phai tai User va Role cung luc duy nhat trong mot cau lenh sql ben duoi --> no giup may tranh loi lazy:
           + + vi Roles dc tai len cung luc voi user trong bo nho, tranh viec truy cap db them lan nua
           ++ tran N+1; *????
           
          ==> tong ket: method findByUsernameWithRolesin su dung FETCH de chuyen doi co che tai tri hoan(lazy loading) sang tai ngay lap tuc(eager) chi  trong mot cau lenh truy van, dam bao du lieu cua Role, luon co san cho cac lop sau(service, controller, security) ma khong gay la loi kt noi nao*/
        @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.listRoles 
            WHERE u.username = :username    
            """)
        User findByUsernameWithRoles(String username);
}
