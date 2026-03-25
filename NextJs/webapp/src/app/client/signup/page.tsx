"use client";
//import lib react hook form
import { useForm, SubmitHandler } from "react-hook-form";

import PasswordInput from "@/components/client/passwordInput_reacthookform";

//import lib fontAwesome
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";

//import Toast context
import { useToast } from "@/contexts/ToastContext";

// ✅ Đúng — dùng next/navigation cho App Router
import { useRouter } from "next/navigation";

//import types cho Signup page
import { RegisterTypes } from "@/types/SignupTypes";

export default function SignupPage() {
  //khai bao state tu useToast trong ToastContext truyen vao bien state
  const { showToast } = useToast();
  //dinh nghia router chuyen trang
  const router = useRouter();

  /***react hook form : useForm ham chinh quan ly ca form*****/
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError, //them setError de set loi tu server vao tung field
  } = useForm<RegisterTypes>();

  /***dinh nghia ham onSubmit cho form****/
  const onSubmit: SubmitHandler<RegisterTypes> = async (data) => {
    try {
      //tien hanh goi api
    } catch (errror: any) {
      //truong hop server tra ve thanh cong nhung logic ben trog co loi
      //showToast(res)
    }
  };

  return (
    <div
      className="container-fluid py-1"
      style={{ minHeight: "100vh", marginBottom: "50px" }}
    >
      <div
        className="bg-white p-4 p-md-5 rounded-3 shadow-sm w-100"
        style={{ maxWidth: " 100%", margin: "0 auto" }}
      >
        <div className="text-center mb-4">
          <FontAwesomeIcon
            icon={faGoogle}
            style={{ height: "54px", color: "red" }}
          />
          <h3 className="fw-bold mt-3 mb-1">Create an Account</h3>
          <div className="text-muted mb-2" style={{ fontSize: "1rem" }}>
            Sign up now get started with an account
          </div>
        </div>

        <div className="d-flex justify-content-center">
          <button className="btn btn-light w-20 border d-flex align-items-center justify-content-center mb-3">
            <FontAwesomeIcon
              icon={faGoogle}
              style={{ height: "20px", width: "20px", marginRight: 8 }}
            />
            Sign up with google
          </button>
        </div>

        <div className="d-flex align-items-center mb-3">
          <hr className="flex-grow-1" />
          <span className="mx-2 text-muted small">OR</span>
          <hr className="flex-grow-1" />
        </div>

        {/* form nhap thong tin register account */}
        <form onSubmit={handleSubmit(onSubmit)}>
          {/* nhap username */}
          <div className="row mb-3 align-items-center">
            <label
              htmlFor="username"
              className="col-md-3 col-form-label text-md-end"
            >
              User Name
            </label>
            <div className="col-md-9">
              <input
                type="text"
                className={`form-control ${errors.username} ? 'is-invalid' : ''`}
                id="username"
                placeholder="Enter Username"
                /*su dung ky thuat spread cua js/ts de thu nhap value cua input
                thay the onChange={(e) => setState(e.target.value)} de thu nhap 
                value nhap tu o input tren ban phim */
                {...register("username", { required: " Username not empty " })}
              />

              {/* cho hien thi loi validation tu server hoac client ra ngay cho form input dang nhap luon */}
              {errors.username && (
                <div className="invalid-feedback d-block">
                  {errors.username.message}
                </div>
              )}
            </div>
          </div>

          {/* nhap password */}
          <div className="row mb-3 align-items-center position-relative">
            <label
              htmlFor="password"
              className="col-md-3 col-form-label text-md-end"
            >
              Password
              <span className="text-danger">*</span>
            </label>
            <div className="col-md-9 position-relative">
              {/* nut password: co icon la con mat an/hien passowrd
               => luu y: de chuyen value va ghi nhan value thay doi voiw {...register}
               kieu cach thu nhap value cua  react hook form thay the cho onchange */}
              <PasswordInput
                register={register("password", {
                  required: "Password not empty!",
                })}
                className={`form-control ${errors.password ? "is-invalid" : ""}`}
              />

              {/* cho hien thi loi validation tu server hoac client ra ngay cho form input dang nhap luon */}
              {errors.password && (
                <div className="invalid-feedback d-block">
                  {errors.password.message}
                </div>
              )}
            </div>
          </div>

          {/* confirm password */}
          <div className="row mb-3 align-items-center position-relative">
            <label
              htmlFor="confirmPassword"
              className="col-md-3 col-form-label text-md-end "
            >
              Confirm Password
              <span className="text-danger">*</span>
            </label>
            <div className="col-md-9 position-relative">
              <PasswordInput
                register={register("confirm_password", {
                  required: "Confirm password not empty!",
                  validate: (value, formValues) => {
                    if (value !== formValues.password) {
                      return "The verification password does not match";
                    }
                  },
                })}
                className={`form-control ${errors.confirm_password ? "is-invalid" : ""}`}
              />
            </div>
          </div>

          {/* nhap email */}
          <div className="row mb-3 align-items-center">
            <label
              htmlFor="email"
              className="col-md-3 col-form-label text-md-end"
            >
              Email Address
              <span className="text-danger">*</span>
            </label>
            <div className="col-md-9">
              <input
                type="email"
                className={`form-control ${errors.email} ? "is-invalid" : ""`}
                id="email"
                placeholder="hiepdamtreem_taichodien@gmail.com"
                {...register("email", {
                  required: "email not empty",
                  pattern: {
                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    message: "Email is not in the correct format",
                  },
                })}
              />

              {errors.email && (
                <div className="invalid-feedback d-block">
                  {errors.email.message}
                </div>
              )}
            </div>
          </div>

          {/* button create account */}
          <div className="row mb-3">
            <div className="offset-md-3 col-md-9">
              <button
                type="submit"
                className="btn btn-primary w-30 mb-3"
                style={{ fontWeight: 500 }}
              >
                Create Account
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
