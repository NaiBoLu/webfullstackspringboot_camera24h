"use client";
import Link from "next/link";
import { Image } from "react-bootstrap";

export default function BackToTop() {
  return (
    <>
      <Link href="/client">
        <Image
          alt=""
          style={{
            position: "fixed",
            right: 30,
            top: "90%",
          }}
          width={50}
          height={50}
          src="/up-arrow.png"
        ></Image>
      </Link>
    </>
  );
}
