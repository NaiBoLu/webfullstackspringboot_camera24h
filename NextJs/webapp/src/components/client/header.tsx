"use client";

import Link from "next/link";
import {
  Image,
  Container,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Nav,
  Navbar,
  NavbarBrand,
  NavbarCollapse,
  NavbarToggle,
  NavLink,
} from "react-bootstrap";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {} from "@fortawesome/free-solid-svg-icons";

//import lib modal va cac lib lien quan xu ly modal context cho form login
import Modal from "react-bootstrap/Modal";
import { useModal } from "@/contexts/ModalContext";
//import pathName: hook cua next/navigation giup lay duong dan url hien tai
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";

//import page giao dien cuar MOdal.body cua form login
import Login from "@/app/client/login/page";

//import toast context vao de thong bao
import { useToast } from "@/contexts/ToastContext";

//luu y la de lay dc img ta can thiet lap lay url uploads o api
import { UPLOAD_URL } from "@/constants/urls";
import { getPayloadInfoFromToken } from "@/axios/axiosAuth";

export default function Header() {
  //khoi tao cac compoent cuar modal context da khai bao o class modalcontext
  const { openModal, closeModal, show, modalType } = useModal();
  //state avatar luu anh
  const [avatar, setAvatar] = useState<string | null>(null);
  //state luu role cuar user khi login thanh cong
  const [userRole, setUserRole] = useState<string | null>(null);
  //them state luu tru userid cuar username de hien thi len giao dien
  const [userId, setUserId] = useState<string | null>(null);
  //state ghi nhan trang thai dang nhap co hay khong
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  //khai bao userToast
  const { showToast } = useToast();

  /*useEffect: method xu ly hanh dong hide/show modal context sau khi giao dien dc 
  render thanh cong
  -> pathname giup khi dong form login thi route Link nos doi sang trang moi*/
  const pathName = usePathname();

  /* hhamxu ly dang xuat log out khoi he thong */
  const handleLogout = () => {
    //xoa toan bo thong tin luu tren localstorage
    localStorage.removeItem("token");
    localStorage.removeItem("avatar");
    localStorage.removeItem("userId");

    showToast("Đăng xuất thành công!", "success");

    //ep trinh duyet load lai trang tu server de xoa sach state cu
    window.location.href = "/";
    setIsLoggedIn(false);
  };

  /* method CheckAuth: tụ reload page lại de update token sau khi login thanh cong tranh refresh trang thu cong*/
  const checkAuth = () => {
    if (typeof window !== "undefined") {
      const token = localStorage.getItem("token");
      const saveAvatar = localStorage.getItem("avatar");
      const saveId = localStorage.getItem("userId");

      /*cap nhat trang thai dang nhap neu co token
       -> true -> !true = false -> !false = true 
      <=> !!true = true, !!false = false
      -> tai sao lam vay? tai sao dung !! 
       + hieu the nay vd string dung token thi no tra ve la chuoi string 
       + con neu la null thi token tra ve null
       --> vay van de la cho nay muon ktra la co toekn hay khong: true or false 
       khong phai can qua nhieu bc la xac din kieu gi roi xem co gia tri gi khong moi 
       xet true false .. viec dung !! se tra ve dung muc dich, no giup don dep value 
       thua thanh value co g ia tri boolean chinh xacs mucj dich muon ktra
       --> tuc o day ktra token co hay khong khong can quan tam ket qua trav ve la gia tri
      */
      setIsLoggedIn(!!token);

      //lay thong tin tong hop tu token va avatar , user_id tu localstorage de hien thi len giao dien
      const roles = getPayloadInfoFromToken();
      if (roles && token) {
        setIsLoggedIn(true);
        setUserRole(roles);
        setAvatar(saveAvatar);
        setUserId(saveId);
      } else {
        //truowng hop: khong co toekn hoac token het han
        if (token) {
          //co token ma het han
          localStorage.removeItem("token");
          localStorage.removeItem("avatar");
          localStorage.removeItem("userId");
          setIsLoggedIn(false);
        }
        setIsLoggedIn(false);
        setUserRole(null);
        setAvatar(null);
        setUserId(null);
      }
    }
  };

  /* useEffect lang nghe thay doi token */
  useEffect(() => {
    checkAuth(); //goi method checkAuth de cap nhat trang thai dang nhap
    //lang nghe su kien de cap nhat giao dien tuc thi khi login/logout thanh cong0
    window.addEventListener("storage", checkAuth);
    return () => window.removeEventListener("storage", checkAuth);
    //
  }, []);

  /* useEffect de dong modal khi chuyen trang */
  useEffect(() => {
    //chi dong modal khi ngui dung thuc su chuyen sang tang khac
    if (show) {
      closeModal();
    }
  }, [pathName]);

  /***ham kiem tra role la admin/cashier de bat tat url sang trang admin page***/
  const canAccessAdminPage =
    userRole?.includes("admin") || userRole?.includes("cashier");

  return (
    <>
      {/* Navbar React bootstrap */}
      <Navbar
        sticky="top"
        expand="lg"
        variant="dark"
        style={{ backgroundColor: "#05422C" }}
      >
        <Container>
          <NavbarBrand as={Link} href="/" className="fw-bold text-warning">
            👑 Royal Neko
          </NavbarBrand>
          <NavbarToggle aria-controls="navbarNav" />

          <NavbarCollapse id="navbarNav">
            <Nav
              className="ms-auto"
              style={{ fontSize: "1.1rem", cursor: "pointer" }}
            >
              <NavLink as={Link} href="/">
                Trang chủ
              </NavLink>
              <NavLink as={Link} href="/client/about">
                Giới Thiệu
              </NavLink>
              <NavLink as={Link} href="/client/contact">
                Liên Hệ
              </NavLink>
              <NavLink as={Link} href="/client/products">
                Sản Phẩm
              </NavLink>

              {/* xu ly handle event click button dawngnhap -> modal context form login */}
              {!isLoggedIn ? (
                <NavLink as={Link} href="#">
                  <span onClick={() => openModal("loginForm")}>Đăng Nhập</span>
                </NavLink>
              ) : (
                <Dropdown
                  align="end"
                  className="border rounded text-white p-1"
                  style={{ cursor: "pointer" }}
                >
                  <DropdownToggle as="div">
                    <Image
                      alt="avatar"
                      //ghep url server voi ten file anh lay tu localstorage luu o state
                      src={
                        avatar
                          ? `${UPLOAD_URL}/${avatar}`
                          : "https://i2.wp.com/vdostavka.ru/wp-content/uploads/2019/05/no-avatar.png?fit=512%2C512&ssl=1"
                      }
                      roundedCircle
                      width="30px"
                      height="30px"
                      style={{
                        objectFit: "cover",
                        border: "1px solid #ffc107",
                      }}
                    />
                  </DropdownToggle>
                  <DropdownMenu>
                    <DropdownItem href="/">Profile</DropdownItem>
                    <DropdownItem onClick={handleLogout}>Logout</DropdownItem>

                    {/* DIEU KIEN QUAN TRONG: CHI HIEN ADMINPAGE NEU ROLE LA ADMIN HOAC CASHIER */}
                    {canAccessAdminPage && (
                      <DropdownItem href="/admin">Admin page</DropdownItem>
                    )}
                  </DropdownMenu>
                </Dropdown>
              )}
            </Nav>
          </NavbarCollapse>
        </Container>
      </Navbar>p

      {/* //  modals form login cua  react bootstrap */}
      <Modal show={show && modalType == "loginForm"} onHide={closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>Login Form</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Login />
        </Modal.Body>
      </Modal>
    </>
  );
}
