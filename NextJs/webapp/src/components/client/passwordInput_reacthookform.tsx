/* xu ly nghiep vu an hien password khi b am vao icon con mat
theo cach thu nhap value lib react hook form chu khong dung
usestate nhu trc
 */
"use client";

import { useReducer, useRef, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";

//goi kieu types cho PasswordInput vao day
import { PasswordInputTypes } from "@/types/PasswordInputTypes";

export default function PasswordInput({ register, className }: any) {
  const [showPassword, setShowPassword] = useState(false);

  /*
     + useRef: tao mot tham chiu den phan tu input de thao tac voi no
     + HTMLInputElement: kieu du lieu cua phan tu input trong ts
     + null: gia tri ban dau cua tham chieu 
    */
  const inputRef = useRef<HTMLInputElement>(null);

  /* tao function xu ly viec mo /tat con mat an hien mat khau */
  const togglePassword = () => {
    setShowPassword((prev) => !prev);
    if (inputRef.current) {
      inputRef.current.type = showPassword ? "text" : "password";
    }
  };

  return (
    <>
      {/* //relative: làm cho phần tử cha làm mốc để đặt vị trí của phần tử con */}
      <div className="relative">
        <input
          //su dung register de react hook form lk dc voiw form passwordInput khi goi den no
          {...register}
          ref={(e) => {
            inputRef.current = e; //gan gia tri cua inputRef
            register.ref(e); //ket noi voi react hook form
          }}
          type={showPassword ? "text" : "password"}
          id="password"
          className="form-control"
          placeholder="Nhập mật khẩu"
          required
        />
        <span onClick={togglePassword} className="toggle-password">
          {showPassword == true && (
            <FontAwesomeIcon icon={faEyeSlash} className="me-2" />
          )}
          {!showPassword == true && (
            <FontAwesomeIcon icon={faEye} className="me-2" />
          )}
        </span>
      </div>
    </>
  );
}
