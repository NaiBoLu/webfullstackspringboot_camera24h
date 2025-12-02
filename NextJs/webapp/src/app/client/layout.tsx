"use client";

import "./client.css";

import Header from "@/components/client/header";
import Footer from "@/components/client/footer";
import BackToTop from "@/components/client/BackToTop";

export default function RootClientLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      {/* phan header */}
      <Header />

      {/* phan body */}
      {children}

      {/* phan footer */}
      <Footer />
    </>
  );
}
