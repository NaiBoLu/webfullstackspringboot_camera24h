"use client";

import { createContext, ReactNode, useContext, useState } from "react";

//import types cuar toastcontext
import { ToastContextTypes } from "@/types/ToastContextTypes";
import { Toast, ToastContainer } from "react-bootstrap";

/**************QUI TRINH TAOJ TOASTCONTEXT THOG BAO******************* */

/* 1 - QUI TRINH 1: tao mot vung chua context de luu tru trang */
const ToastContext = createContext<ToastContextTypes | undefined>(undefined);

/* 2 - QUI TRINH 2: tao provider de xu ly ben trong toastcontext
 -> vai tro provider: la noi chua cacs state va method xu ly su kien cua Toastcontext
 -> phan bo bo tri cau truc thiet ke bo tri xu ly su kien cho toast
*/
const ToastProvider = ({ children }: { children: ReactNode }) => {
  /* + tao state: show(an/hien cho toast)
       + tao mstToast(messenger toast thong bao ra ben ngoai) 
       + variant(bien tthe ngu canh mau sac hong bao thanh/that bai: mau color */
  const [show, setShow] = useState(false);
  const [msgToast, setMsgToast] = useState("");
  const [variant, setVariant] = useState("primary");

  //tiep tuc xu ly hien thi toast: msg thong bao noi dung voi mau sac variant tuong ung: vd thanh cong la success mau xanh....
  const showToast = (msg: string, variant: string = " primary"): void => {
    setShow(true);
    setMsgToast(msg);
    setVariant(variant);
  };

  //tra ve g iao dien cua ToastProvider mong muon
  return (
    <ToastContext.Provider value={{ showToast }}>
      {/* //bien children de nhan cac component con ben ngoai truyen vao */}
      {children}
      <ToastContainer
        position="top-start"
        className="p-3"
        style={{ zIndex: 1200000 }}
      >
        <Toast
          show={show}
          onClose={() => setShow(false)}
          bg={variant}
          delay={3000}
          autohide
        >
          <Toast.Header>
            <strong className="me-auto">Notification</strong>
          </Toast.Header>
          {/* // noi dung thong bao cua toast */}
          <Toast.Body>{msgToast}</Toast.Body>
        </Toast>
      </ToastContainer>
    </ToastContext.Provider>
  );
};
export default ToastProvider;

/*******************qui trinh 3: tao hooks de su dung toast nay****************** */
export const useToast = () => {
  //tao hook su dung
  const context = useContext(ToastContext);
  //kiem tra an toan: neu dung useToast ngoai ToastProvider se bao loi ngay
  if (!context) {
    throw new Error("useToast must be used within a ToastProvider");
  }
  return context;
};
