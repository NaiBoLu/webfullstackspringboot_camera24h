/***CAU HINH AXIOS CALL API CHO CLIENT PAGE QUA GIAO THUC HTTP******/
import axios from "axios";
//import cac file cau hinh url api quan ly tap trung tu constants/urls.tsx
import { CLIENT_URL } from "@/constants/urls";

const axiosClient = axios.create({
  //lay dia chi api tu backend -> dia chi root api
  baseURL: `${CLIENT_URL}`,
  headers: {
    //content-type: la du lieu mong doi tu server tra ve duoi dang json
    "Content-Type": "application/json",
  },

  /*cho phep cookies cung request neu server yeu cau
  => neu khong bat withcredentials true thi nos khong gui dc token cos 
  phan quyenf authorization*/
  withCredentials: true,
});

/**CAU HINH REQU8EST INTERCEPTOR*
 * => giai quyet van de token khong dc gui kem khi goi api,
 * => cau hinh nay tu dong danh chan yeu cau trc khi no gui di
 * giups kiem tra token luu trong localstorage khi login thanh cong
 * trc do
 * => bao mat: giup may quan ly tap trung cac token
 * => tranh loi server: xac minh token dung chuan: Bearer chuoi_token
 * qua do filter bao mat moi xac thuc dc token do vaf tra ve value cho user
 * khi call api
 *
 */

//==============xu ly gui token di(request)======
axiosClient.interceptors.request.use(
  (config) => {
    //typeof window la kiem tra browser co ton tai window(localstorage) khong
    if (typeof window !== "undefined") {
      const token = localStorage.getItem("token");
      //neu  token co ton tai
      if (token) {
        //khoi tao headers neu chua co
        config.headers = config.headers || {};
        //gan authoriztion header theo chuan: "Bearer token(7)"
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error),
);

//===================xu ly ket qua tra ve (response)=====================
axiosClient.interceptors.response.use(
  (response) => {
    //neu api tra ve thanh cong, cu de no di tiep
    return response;
  },
  (error) => {
    //day la noi xu ly loi tap trung
    if (error.response && error.response.status === 401) {
      //neu server tra ve 401(Unauthorized - token het han hoac sai token)
      console.error(
        "Token het han hoac khong hop le, vui long kiem  tra lai....",
      );

      if (typeof window !== "undefined") {
        localStorage.removeItem("token"); //xoa token rac
        window.location.href = "/login"; //chuyen ve trang login
      }
    }
    return Promise.reject(error);
  },
);

export default axiosClient;
