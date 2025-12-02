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
import { useEffect } from "react";

//import page giao dien cuar MOdal.body cua form login
import Login from "@/app/client/login/page";

export default function Header() {
  //khoi tao cac compoent cuar modal context da khai bao o class modalcontext
  const { openModal, closeModal, show, modalType } = useModal();

  /*useEffect: method xu ly hanh dong hide/show modal context sau khi giao dien dc 
  render thanh cong
  -> pathname giup khi dong form login thi route Link nos doi sang trang moi*/
  const pathName = usePathname;
  useEffect(() => {
    //hide an form login khi bam dau x, button clode hay bam vung ngaoi form r
    closeModal;
  }, [pathName]);

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
              <NavLink as={Link} href="#">
                <span onClick={() => openModal("loginForm")}>Đăng Nhập</span>
              </NavLink>

              <Dropdown
                align="end"
                className="border rounded text-white p-1"
                style={{ cursor: "pointer" }}
              >
                <DropdownToggle as="div">
                  <Image
                    alt="avatar"
                    src="https://i.pravatar.cc/41"
                    roundedCircle
                    width="30px"
                    height="30px"
                  />
                </DropdownToggle>
                <DropdownMenu>
                  <DropdownItem href="/">Profile</DropdownItem>
                  <DropdownItem href="/">Logout</DropdownItem>
                  <DropdownItem href="/">Settings</DropdownItem>
                </DropdownMenu>
              </Dropdown>
            </Nav>
          </NavbarCollapse>
        </Container>
      </Navbar>

      {/* //  modals form login cua  react bootstrap */}
      <Modal show={show && modalType == "loginForm"} onHide={closeModal}>
        <Modal.Header>
          <Modal.Title>Login Form</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Login />
        </Modal.Body>
      </Modal>
    </>
  );
}
