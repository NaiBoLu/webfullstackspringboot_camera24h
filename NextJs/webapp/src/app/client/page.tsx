"use client";

/* xin chao cac ban minh la body Homepage cua client layout hehehe */
import { Carousel } from "react-bootstrap";
import CarouselItem from "react-bootstrap/CarouselItem";
import CarouselCaption from "react-bootstrap/CarouselCaption";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {} from "@fortawesome/free-solid-svg-icons";

/* phan body cua homepage cuar client page layout */
export default function Homaepage() {
  return (
    <>
      {/* Carousel */}
      <Carousel fade interval={3000} pause={false}>
        <CarouselItem>
          <img
            className="d-block w-100"
            src="https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=1400&q=80"
            alt="Slide 1"
            style={{ height: "70vh", objectFit: "cover" }}
          />
          <CarouselCaption>
            <h3>Hoàng Gia Restaurant 👑</h3>
            <p>Ẩm thực tinh tế, không gian sang trọng.</p>
          </CarouselCaption>
        </CarouselItem>

        <CarouselItem>
          <img
            className="d-block w-100"
            src="https://images.unsplash.com/photo-1528605248644-14dd04022da1?auto=format&fit=crop&w=1400&q=80"
            alt="Slide 2"
            style={{ height: "70vh", objectFit: "cover" }}
          />
          <CarouselCaption>
            <h3>Hương vị đẳng cấp</h3>
            <p>Mỗi món ăn là một câu chuyện 😋</p>
          </CarouselCaption>
        </CarouselItem>

        <CarouselItem>
          <img
            className="d-block w-100"
            src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=1400&q=80"
            alt="Slide 3"
            style={{ height: "70vh", objectFit: "cover" }}
          />
          <CarouselCaption>
            <h3>Trải nghiệm đặc biệt</h3>
            <p>Ẩn mình giữa lòng thành phố 🌆</p>
          </CarouselCaption>
        </CarouselItem>
      </Carousel>

      {/* Banner */}
      <section
        className="text-center text-white py-5"
        style={{
          backgroundImage:
            'url("https://images.unsplash.com/photo-1517816428104-7970b1e2de10?auto=format&fit=crop&w=1400&q=80")',
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundBlendMode: "overlay",
          backgroundColor: "rgba(5, 66, 44, 0.8)", // overlay tím royal mờ mờ
        }}
      >
        <div className="container">
          <h1 className="fw-bold display-5 text-warning">
            ✨ Royal Kingdom of Devs ✨
          </h1>
          <p className="lead mb-4">
            Nơi Onii-chan và Neko-chan code trong ánh hào quang 💫
          </p>
          <a href="/about" className="btn btn-warning fw-semibold">
            Khám phá ngay 💎
          </a>
        </div>
      </section>

      {/* phan body khieu goi cua e: lol va hai cap vu */}
      <main className="container my-5 flex-grow-1">
        <section style={{ padding: "20px" }}>
          <h2>🧋 Menu của quán</h2>
          <ul>
            <li>Trà sữa trân châu</li>
            <li>Trà đào cam sả</li>
            <li>Matcha latte</li>
            <li>Trà sữa Oolong</li>
            <li>Trà sữa socola</li>
          </ul>
        </section>
      </main>
    </>
  );
}
