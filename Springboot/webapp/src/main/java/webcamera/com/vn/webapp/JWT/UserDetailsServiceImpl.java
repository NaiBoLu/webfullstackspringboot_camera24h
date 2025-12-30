package webcamera.com.vn.webapp.JWT;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webcamera.com.vn.webapp.repository.UserRepository;

/*JWT IV - Lớp này lấy user từ database khi cần xác thực(role, permissions)
 -> mục tiêu:
  + chuyển đỏi thong tin ngừi dùng từ csdl thành đối tượng mà spring security có thể sử dụng để xác thực và ủy quyền
  + quy định các thông tin về roles, permission lấy từ csdl sau đó spring security
  sử dụng để phân quyền sau khi token đc các thực thành công

  -> tại sao lại có lớp này
   + nếu khong có lớp này spring security không biết cách lấy thông tin người dùng từ đâu, đấn dến xác thực người dùng thát bại
   + nếu có lớp này, bạn có thể xác thực người dùng từ database, tùy biến cách lấy dữ liệu và phân quyền

   ===> kết luận: Lớp này(file này) là bắt buộc nếu bạn muốn xác thực phân quyền người  dùng từ database, nó giúp bảo mật, kiểm soát truy cập phân quyền hiệu quả
*/

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;


    /*@Transactional: readOnly = true la annotation giup spring chi doc value, khong thuc thi update/delete/create.... value 
    => neu khong @Transactional(readOnly=true) thi dieu gi se xay ra
    + loi lazy: lazyInitializationException 
    + tranh loi tai role tu database:  chap nhan null
    + hieu nang: hibernate theo doi entity khong can thiet
    + security: hanh vi khong on dinh */ 
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       webcamera.com.vn.webapp.entity.User user = userRepo.findByUsernameWithRoles(username);
       if(user == null || user.getUsername() == null){
        throw new UsernameNotFoundException("Username khong tim thay");
       }


       /*LAY VAI TRO -> CALL API VA LAY DC DANH SACH VAI TRO ROLE DA DC  GAN CHO USER
       >> Y NGHIA TUNG DONG CODE<<<
       -> tg tac csdl lay role cua user de phan quyen
       #1 GrantedAuthority: 
        + la interface cot loi spring security, dai dien cho mot quyen(permission)  hoac mot role vai tro da dc cap cho mot user nao do
        + spring security sd cac toi tuowng trong class GrantedAuthority de thuc hien viec uy quyen(vd: user co role va co quyen ntn )

       #2  user.getListRoles()
        + method trong entity user dc dung de truy van list role no t ra ve mot List<Role> trong do moi doi tuong role co thong tin vai tro  rieng biet

       #3 stream():
        + phuong thuc chuyen doi LIst<role> thanh mot luong du lieu(Stream) 
        + Stream cung cap mot chuoi cac thao tac cho phep ban xu ly du lieu theo phong cach khaibao(declaratice0 va chuc nang giup ma ngan gon va de doc hom(thay the vong lap for truyen thong) )

       #4 map(role -> new SimpleGrantedAuthority(role.getName()))
        + day la  thao tac chuyen doi (transformation) chinh
        + chuyen doi ra sao:
         ++ no duyet qua tung phan(role) trong stream
         ++ voi moi role, no thuc hien mot ham lambda 
         ++ ham nay tao  ra mot doi tuong SimpleGrantedAuthority(no la lop trien khai cua GrantedAuthority), su dung te nvai tro(role.getName) lam chuoi quyen han(vd: role.getName la admin, no se tao ra new SimpleGrantedAuthority("ADMIN"))

        #5  collect(Collectors.toList());
         + day la thao tac ket thuc cua stream
         + no  thu nhap tat cac ket qua dc chuyen doi(tuc la cac doi tuong SimpleGrantedAuthority ) va dong goi chung lai vao mot List<GrantedAuthority> de thuc hien uy quyen phan quyen cho user do

        #6/ return new User(user.getUsername(), user.getPassword(), authorities);
   }
         + dong nay tao ra va tra ve doi tuong user cuar spring security m dat ka nit trueb jgau cya giao dien UserDetail 
         + new User(..): khoi tao moi doi tuong user cua spring security
         + ts1: user.getUsertname: kiem tra ten user
         + ts2: user.getPassword: kiem tra mk
         + ts3: authorities: danh sach cac quyen han

      ===> doi tuong User nay(implement UserDetails) la du lieu ma spri9ng security xac thuc va luu tru thong tin vao trong securityContextHOlder(luu tru phien lam viec cua nguoi dung0)  
         
      */
       List<GrantedAuthority> authorities = user.getListRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
       return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
