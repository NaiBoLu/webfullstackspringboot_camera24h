import Image from "next/image";
import Link from "next/link";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faUser, faBox } from "@fortawesome/free-solid-svg-icons";

export default function () {
  return (
    <>
      <div className="h-100">
        <div className="sidebar-logo">
          <Link href="#">
            <Image
              src="/IZJP.gif"
              width={50}
              height={50}
              style={{ borderRadius: "50%" }}
              alt="Logo"
            />{" "}
            GAME DESIGN
          </Link>
        </div>

        {/* <!-- Sidebar Navigation --> */}
        <ul className="sidebar-nav">
          <li className="sidebar-header">Tools & Components</li>
          <li className="sidebar-item">
            <Link href="#" className="sidebar-link">
              <FontAwesomeIcon icon={faHouse} className="fa-fw" />
              Dashboard
            </Link>
          </li>
          <li className="sidebar-item">
            <Link href="/admin/accounts" className="sidebar-link">
              <FontAwesomeIcon icon={faUser} className="fa-fw" />
              Accounts
            </Link>
          </li>
          <li className="sidebar-item">
            <Link href="/admin/products" className="sidebar-link">
              <FontAwesomeIcon icon={faBox} className="fa-fw" />
              Products
            </Link>
          </li>
          <li className="sidebar-item">
            <Link href="#" className="sidebar-link">
              <FontAwesomeIcon icon={faBox} className="fa-fw" />
              Products
            </Link>
          </li>
        </ul>
      </div>
    </>
  );
}
