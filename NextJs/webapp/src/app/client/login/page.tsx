"use client";
import React, { useState } from "react";
import Link from "next/link";

//import lib fontawesome
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";

//import xu ly an/hien passowrd qua icon con mat
import PasswordInput from "@/components/client/passwordInput";

//import axiosAuth vaof trong form login cua route client
import { login } from "@/axios/axiosAuth";

//import userRouter dieu trang
import { useRouter } from "next/navigation";

//import modal context vao su dung
import { useModal } from "@/contexts/ModalContext";

//import toastContext vao nha
import { useToast } from "@/contexts/ToastContext";

export default function Login() {
  //state trang thai
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");

  //khoi tao userRouter
  const router = useRouter();
  //khoi tao toast context
  const { showToast } = useToast();

  //khoi tao modal context
  const { closeModal } = useModal();
  //state ghi nhan trang thai dang nhap cua nguoi dung khi nhan button login
  const [isLoading, setIsLoading] = useState(false);

  /* method hanh dong xu ly dang nhap cho form login */
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    //ngan chan hanh vi macj dinh cua form
    e.preventDefault();
    setIsLoading(true); //ghi nhan trang thai la dang load
    try {
      //1. goi api axiosAuth login vao
      const token = await login(username, password);

      //show context bao thanh cong
      showToast("Login thanh cong", "success");

      //closeMOdal
      closeModal();

      //2.lam moi lai trang de cap nhat lai giao dien
      router.refresh();
    } catch (error: any) {
      const errorMessage =
        error.response?.data?.message ||
        error.message ||
        "login co van de xme lai oh yead.";
      //show toat context:
      showToast(errorMessage, "danger");
    }
  };

  return (
    <>
      <div className="container d-flex justify-content-center align-items-center min-vh-100 mb-1">
        <div
          className="login-form-box bg-white p-1 p-md-5 rounded-3 shadow-sm w-100"
          style={{ maxWidth: "100%" }}
        >
          <div className="text-center mb-4">
            <FontAwesomeIcon
              icon={faGoogle}
              className="fa-3x"
              style={{ color: "#4285f4" }}
            />
            <h4 className="fw-bold mt-3 mb-1">Log in to your Account</h4>
            <div className="text-muted mb-2" style={{ fontSize: "1rem" }}>
              Welcome back, please enter your details.
            </div>
          </div>
          <button className="btn btn-light w-100 border d-flex align-items-center justify-content-center mb-3 google-btn">
            <FontAwesomeIcon icon={faGoogle} className="me-2" /> Continue with
            Google
          </button>
          <div className="d-flex align-items-center mb-3">
            <hr className="flex-grow-1" />
            <span className="mx-2 text-muted small">OR</span>
            <hr className="flex-grow-1" />
          </div>

          {/* form đk sự kiện */}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Email Address
              </label>
              <input
                type="text"
                className="form-control"
                id="username"
                placeholder="nhập username đăng nhập"
                required
                value={username}
                //kích hoạt sự kiện onchange ghi nhận value thay đổi khi nhập input
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
            <div className="mb-3 position-relative">
              <label htmlFor="password" className="form-label">
                Password
              </label>

              {/* nut password : co icon con mat an hien password  */}
              <PasswordInput
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <div className="d-flex justify-content-between align-items-center mb-3">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="remember"
                />
                <label className="form-check-label" htmlFor="remember">
                  Remember me
                </label>
              </div>
              <a href="#" className="fw-bold small text-decoration-none">
                Forgot Password?
              </a>
            </div>

            {/* nút button login của form login */}
            <button
              type="submit"
              className="btn btn-primary w-100 mb-3"
              style={{ fontWeight: 500 }}
              disabled={isLoading} // ngan nhan nut khi dang load: bao hieu la dang loading
            >
              {isLoading ? "Logging in..." : "Log In"}
            </button>
          </form>

          {/* mục không có tk để lk trang signup */}
          <div className="text-center small mt-2">
            Don&apos;t have an account?
            <Link
              href="/client/signup"
              className="fw-bold text-primary text-decoration-none"
            >
              Sign Up
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}
