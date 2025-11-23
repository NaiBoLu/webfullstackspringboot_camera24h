import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignOut,
  faCog,
  faUser,
  faBell,
  faEnvelope,
  faSignOutAlt,
} from "@fortawesome/free-solid-svg-icons";

import Image from "next/image";
import Link from "next/link";

export default function Nav({
  onToggleSidebar,
}: {
  onToggleSidebar: () => void;
}) {
  return (
    <>
      {/* <!-- Button for sidebar toggle --> */}
      <button
        className="btn led-toggler"
        type="button"
        onClick={onToggleSidebar}
        data-bs-theme="dark"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      {/* <!--search admin page--> */}
      <input
        type="text"
        className="form-control w-25 ms-3"
        placeholder="Search..."
      />
      <div className="dropdown ms-auto">
        <button className="btn btn-outline-light led-toggler">
          <FontAwesomeIcon icon={faBell} className="fa-fw" />
        </button>
        <button className="btn btn-outline-light led-toggler">
          <FontAwesomeIcon icon={faEnvelope} className="fa-fw" />
        </button>
        <button
          className="btn btn-secondary dropdown-toggle led-toggler"
          type="button"
          id="avatarMenu"
          data-bs-toggle="dropdown"
        >
          <Image
            src="/user.jpg"
            width={30}
            height={30}
            alt="User Avatar"
            style={{ borderRadius: "50%" }}
          />
        </button>
        <ul
          className="dropdown-menu dropdown-menu-end"
          aria-labelledby="avatarMenu"
        >
          <li>
            <Link className="dropdown-item" href="#">
              {/* cau truc su dung lib sontawesome trong nextjs */}
              <FontAwesomeIcon icon={faUser} fixedWidth className="me-2" />
              Profile
            </Link>
          </li>
          <li>
            <Link className="dropdown-item" href="#">
              {/* cau truc su dung lib sontawesome trong nextjs */}
              <FontAwesomeIcon icon={faCog} fixedWidth className="me-2" />
              Settings
            </Link>
          </li>
          <li>
            <Link className="dropdown-item" href="#">
              {/* cau truc su dung lib sontawesome trong nextjs */}
              <FontAwesomeIcon
                icon={faSignOutAlt}
                fixedWidth
                className="me-2"
              />
              Logout
            </Link>
          </li>
        </ul>
      </div>
    </>
  );
}
