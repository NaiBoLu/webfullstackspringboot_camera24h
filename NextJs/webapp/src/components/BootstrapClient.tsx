/* File nayf giups import lib boootstrap.min.js vào dự án nexts khắc phục tình trạng báo lỗi khi
sử dụng lib js của bootstrap là window í not defined
 => file layout.tsx là một server component theo mặc định tỏng nextjs
 -> code trong server component đc thực thi trên máy chủ là nodejs
 -> mà môi trường nodejs nó không có đối tượng document để khởi tạo các tính các tính năng tương 
 tác với eleement trong html
 ===> chính vì thế ta cần khai báo riêng  đẻ nextjs hiểu  và khởi tạo các tính năng của lib js của 
 bootstrap 
*/

/*
 => Trong Next.js 13+ (App Router), mặc định mọi file React là Server Component, nghĩa là:
    + Chạy trên server.
    + Không có access tới browser API (window, document, localStorage,…).
    + Không dùng được các hook client như useState, useEffect, useRef.
    ==> Vậy 'use client' dùng để làm gì?
        ✔ Nó biến file đó thành Client Component, có nghĩa là:
        ✔ Được xài useState, useEffect, useRef
        ✔ Truy cập window, document, localStorage
        ✔ Chạy code phía browser
        ✔ Xử lý sự kiện người dùng (onClick, onChange, …)
    */
"use client";

import { useEffect } from "react";

export default function BootstrapClient() {
  useEffect(() => {
    //su dung require() hoac dynamic import de tai js trong trinh duyet
    require("bootstrap/dist/js/bootstrap.bundle.min.js");
  }, []);

  return null;
}
