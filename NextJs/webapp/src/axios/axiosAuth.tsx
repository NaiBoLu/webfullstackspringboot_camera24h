/* CẤU HÌNH AXIOS CHO PHẦN XỬ LÝ AUTHENTICATION */
import { jwtDecode } from "jwt-decode";

//import jwtdecode giai ma token

export async function login(username: string, password: string) {
  const response = await fetch("http://localhost:8080/api/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userName: username,
      passWord: password,
    }),
  });

  //kiem tra response nos co ton tai khong
  if (!response.ok) {
    throw new Error("Login failed. wrong username or password");
  }

  //ghi nhan dang nh nhjap thanh cong
  const data = await response.json();

  if (typeof window !== "undefined") {
    //1. luu thong tin token localstorage
    localStorage.setItem("token", data.token);
    //2. luu avatar vao loalstorage
    localStorage.setItem("avatar", data.avatar);
    //3.lluu id user nguoi dung vao localstorage
    localStorage.setItem("userId", data.userId);

    /*phats tin hieu: tu kich hoat su kien sotrage de cac compoent  khac cap nhat 
    giup viec sau khi login thanh cong sinh ra token -> thi storage update de moi
    reqeust sau biet la co token roi tranh request trang thu cong */
    window.dispatchEvent(new Event("storage"));
  }

  return data.token;
}

/* giair ma token lay payload infor mation cuar user va kem theo avatar, id cua
 user gui kem trong token khi login thnah cong */
export function getPayloadInfoFromToken() {
  if (typeof window !== "undefined") {
    const token = localStorage.getItem("token");
    if (!token) return null;
    try {
      const decoded: any = jwtDecode(token);
      //gia su backen ban luu key la 'roles' hoac role trong payload thi deu lay dc
      return decoded.roles || decoded.role || null;
    } catch (error) {
      console.error("Invalid token:", error);
      return null;
    }
  }
  return null;
}
