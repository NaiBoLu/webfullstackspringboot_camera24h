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

export default function Header() {
  return (
    <>
      {/* Navbar React bootstrap */}
      <Navbar expand="lg" variant="dark" style={{ backgroundColor: "#05422C" }}>
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
              <NavLink as={Link} href="/about">
                Giới Thiệu
              </NavLink>
              <NavLink as={Link} href="/contact">
                Liên Hệ
              </NavLink>
              <NavLink as={Link} href="/product">
                Sản Phẩm
              </NavLink>
              <NavLink as={Link} href="/client/login">
                Đăng Nhập
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
    </>
  );
}
