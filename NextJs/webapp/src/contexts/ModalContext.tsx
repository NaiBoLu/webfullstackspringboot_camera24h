/* THIET KE FORM CONTEXT TRONG NEXTJS
 I/ khai niem 
  -> context (react context api) là một cơ chế chính thức cảu react 
  -> nó cho phép mày chia sẽ dử liệu cho toàn cục(ai dùng cũng đc)
  -> nó giúp liên kết các components với nhau mà không cần truyền props nhiều cấp 

 II/ trc khi có context thì ta giải quyết vấn đề ntn
  ->  mún truyền dữ liệu từ compoent a -> b thì nó phải truyền props thông qua c <d styleName={}></d>
  -> kỹ thuật truyền như vậy gọi props drilling (truyền props nhiều cấp)
  -> hạn chế: rườm rà, khó quản lý, viết code dài dòng

  III/ chuẩn đẻ mà mày tạo contexxt là gí
   1/ createContext: tọa context 
   2/ provider: lớp bao bọc compoents con để chia sẽ dử liệu
   3/ useContext: hook đẻ các components con truy cập dữ liều từ provider
*/

"use client";
//import lib chuan su dung khai bao quan ly tap trung chuan context
import React, { createContext, useContext, useState, ReactNode } from "react";

//import kieu du lieu cua chuan modal context vao
import { ModalContextType } from "@/types/ModalContextTypes";

/* qui trinh 1: tao context */
const ModalContext = createContext<ModalContextType | null>(null);

/* qui trinh 2: taoj modal provider boc compoent con lai 
 + ReactNode: kiểu dữ liệu đại diện cho các phần tử React có thể được render
 leen giao dien man hinh
  ++ vd: string " helo"
     number 123
        ...
*/
export function ModalProvider({ children }: { children: ReactNode }) {
  //state trang thai show
  const [show, setShow] = useState(false);
  //state modalType: de xac minh modal nao dg mo
  const [modalType, setModalType] = useState<string | null>(null);

  //method xu ly trang openmodal: open modal mo modal form mun mo
  const openModal = (type: string) => {
    setModalType(type); // luu loai model
    setShow(true);
  };

  //method closeModal
  const closeModal = () => {
    setShow(false);
    //reset lai modalType khi dong modal
    setModalType(null);
  };

  return (
    <ModalContext.Provider value={{ show, modalType, openModal, closeModal }}>
      {/* //dung the children de load cac compoent con vao body web dong */}
      {children}
    </ModalContext.Provider>
  );
}

/*qui trinh 3: tao hook dieu phoi xu ly
 -> dung trong cac compoents con de lay du mau form tu context */
export function useModal() {
  const context = useContext(ModalContext);

  if (!context) {
    throw new Error("useMOdal phai dc su dung ben trong provider");
  }
  return context;
}
