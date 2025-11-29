import "swiper/css";
import "swiper/css/navigation";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation } from "swiper/modules";
import { Card, CardBody, Image } from "react-bootstrap";

export default function SwiperProduct() {
  return (
    <Swiper
      modules={[Navigation]}
      navigation
      slidesPerView={"auto"}
      spaceBetween={20}
      freeMode={true}
    >
      <SwiperSlide style={{ width: "300px" }}>
        <Card className="text-center h-100">
          <div
            className="d-flex justify-content-center align-items-center shadow-sm"
            style={{ backgroundColor: "#F4F4F4" }}
          >
            <Image
              className="card-img-top my-4 "
              src="/products/product_1.png"
              alt="product"
              style={{ width: "170px", height: "170px" }}
            ></Image>
          </div>
          <CardBody className="d-flex flex-column justify-content-between align-items-center">
            <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
              FLOWER
            </h6>
            <p className="fw-bold mb-2 fs-6 ">
              2 Oz Deal Watermelon Zkittles + Purple Gushers
            </p>
            <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
              <i className="bi bi-star-fill text-warning"></i>
              <span className="fs-6 fw-semibold">4.6/5</span>
              <span className="text-muted fs-6 fw-semibold">| 135 Reviews</span>
            </div>
            <span
              className="badge bg-light fs-6 fw-semibold "
              style={{ color: "#05422C" }}
            >
              Sativa 100%
            </span>
            <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
              <span className="text-danger fs-5 fw-semibold">$80.00</span>
              <span className="text-muted fs-5 fw-normal">/ gram</span>
            </div>
            <div className="d-flex justify-content-center align-items-center gap-3 my-3">
              <span className="fs-6 fw-semibold">28g</span>
              <span className="fs-6 fw-semibold">1/2lb</span>
              <span className="fs-6 fw-semibold">1/4lb</span>
            </div>
            <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5 ">
              Add to card
            </button>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "300px" }}>
        <Card className="text-center h-100 ">
          <div
            className="d-flex justify-content-center align-items-center shadow-sm"
            style={{ backgroundColor: "#F4F4F4" }}
          >
            <Image
              className="card-img-top my-4 "
              src="/products/product_1.png"
              alt="product"
              style={{ width: "170px", height: "170px" }}
            ></Image>
          </div>
          <CardBody className="d-flex flex-column justify-content-between align-items-center">
            <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
              FLOWER
            </h6>
            <p className="fw-bold mb-2 fs-6 ">
              2 Oz Deal Watermelon Zkittles + Purple Gushers
            </p>
            <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
              <i className="bi bi-star-fill text-warning"></i>
              <span className="fs-6 fw-semibold">4.6/5</span>
              <span className="text-muted fs-6 fw-semibold">| 135 Reviews</span>
            </div>
            <span
              className="badge bg-light fs-6 fw-semibold "
              style={{ color: "#05422C" }}
            >
              Sativa 100%
            </span>
            <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
              <span className="text-danger fs-5 fw-semibold">$80.00</span>
              <span className="text-muted fs-5 fw-normal">/ gram</span>
            </div>
            <div className="d-flex justify-content-center align-items-center gap-3 my-3">
              <span className="fs-6 fw-semibold">28g</span>
              <span className="fs-6 fw-semibold">1/2lb</span>
              <span className="fs-6 fw-semibold">1/4lb</span>
            </div>
            <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5 ">
              Add to card
            </button>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "300px" }}>
        <Card className="text-center h-100 ">
          <div
            className="d-flex justify-content-center align-items-center shadow-sm"
            style={{ backgroundColor: "#F4F4F4" }}
          >
            <Image
              className="card-img-top my-4 "
              src="/products/product_1.png"
              alt="product"
              style={{ width: "170px", height: "170px" }}
            ></Image>
          </div>
          <CardBody className="d-flex flex-column justify-content-between align-items-center">
            <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
              FLOWER
            </h6>
            <p className="fw-bold mb-2 fs-6 ">
              2 Oz Deal Watermelon Zkittles + Purple Gushers
            </p>
            <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
              <i className="bi bi-star-fill text-warning"></i>
              <span className="fs-6 fw-semibold">4.6/5</span>
              <span className="text-muted fs-6 fw-semibold">| 135 Reviews</span>
            </div>
            <span
              className="badge bg-light fs-6 fw-semibold "
              style={{ color: "#05422C" }}
            >
              Sativa 100%
            </span>
            <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
              <span className="text-danger fs-5 fw-semibold">$80.00</span>
              <span className="text-muted fs-5 fw-normal">/ gram</span>
            </div>
            <div className="d-flex justify-content-center align-items-center gap-3 my-3">
              <span className="fs-6 fw-semibold">28g</span>
              <span className="fs-6 fw-semibold">1/2lb</span>
              <span className="fs-6 fw-semibold">1/4lb</span>
            </div>
            <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5 ">
              Add to card
            </button>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "300px" }}>
        <Card className="text-center h-100 min-width-300">
          <div
            className="d-flex justify-content-center align-items-center shadow-sm"
            style={{ backgroundColor: "#F4F4F4" }}
          >
            <Image
              className="card-img-top my-4 "
              src="/products/product_1.png"
              alt="product"
              style={{ width: "170px", height: "170px" }}
            ></Image>
          </div>
          <CardBody className="d-flex flex-column justify-content-between align-items-center">
            <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
              FLOWER
            </h6>
            <p className="fw-bold mb-2 fs-6 ">
              2 Oz Deal Watermelon Zkittles + Purple Gushers
            </p>
            <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
              <i className="bi bi-star-fill text-warning"></i>
              <span className="fs-6 fw-semibold">4.6/5</span>
              <span className="text-muted fs-6 fw-semibold">| 135 Reviews</span>
            </div>
            <span
              className="badge bg-light fs-6 fw-semibold "
              style={{ color: "#05422C" }}
            >
              Sativa 100%
            </span>
            <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
              <span className="text-danger fs-5 fw-semibold">$80.00</span>
              <span className="text-muted fs-5 fw-normal">/ gram</span>
            </div>
            <div className="d-flex justify-content-center align-items-center gap-3 my-3">
              <span className="fs-6 fw-semibold">28g</span>
              <span className="fs-6 fw-semibold">1/2lb</span>
              <span className="fs-6 fw-semibold">1/4lb</span>
            </div>
            <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5 ">
              Add to card
            </button>
          </CardBody>
        </Card>
      </SwiperSlide>
    </Swiper>
  );
}
