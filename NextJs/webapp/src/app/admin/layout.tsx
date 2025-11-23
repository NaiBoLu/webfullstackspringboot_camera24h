"use client";

import React, { useState } from "react";

//import admin.css vao trong file admin layout.tsx
import "./admin.css";

import Sidebar from "@/components/admin/Sidebar";
import Nav from "@/components/admin/Nav";

export default function RootAdminLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  //su dung useStae de quan ly trang thai dong/mo cua sidebar
  const [isCollapsed, setIsCollapsed] = useState(false);

  /*method xu ly  bat /tat sidebar cho Nav */
  const toggleSidebar = () => {
    //dao chieu trang thai khi nhan button da 3-
    setIsCollapsed(!isCollapsed);
  };

  return (
    <>
      <div className="wrapper">
        {/* <!-- Sidebar --> */}
        <aside id="sidebar" className={isCollapsed ? "collapsed" : ""}>
          <Sidebar />
        </aside>
        {/* <!-- Sidebar end --> */}

        {/* <!-- Main Component --> */}
        <div className="main">
          {/* <!--thanh navbar--> */}
          <nav className="navbar navbar-expand px-3 border-bottom">
            <Nav onToggleSidebar={toggleSidebar} />
          </nav>
          {/* <!--thanh navbar end--> */}

          <main className="content px-3 py-2">
            {/* <!--body table products --> */}
            <div className="container">{children}</div>
            {/* <!--body table end --> */}
          </main>
        </div>
      </div>
    </>
  );
}
