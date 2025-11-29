import "swiper/css";
import "swiper/css/navigation";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation } from "swiper/modules";
import { Card, CardBody, Image } from "react-bootstrap";
import Link from "next/link";

export default function SwiperEducation() {
  return (
    <Swiper
      modules={[Navigation]}
      navigation
      slidesPerView={3}
      spaceBetween={30}
      freeMode={true}
    >
      <SwiperSlide style={{ width: "400px" }}>
        <Card className="text-center h-100 border-0 ">
          <Image
            className="card-img-top object-fit-cover w-100"
            src="/image/Cannabis-research-1-scaled.png"
            alt="product"
            style={{ height: "240px" }}
          ></Image>

          <CardBody className="d-flex flex-column justify-content-between align-items-start px-0 mt-1">
            <span className="text-muted fw-lighter fs-6 ">
              January 24, 2025
            </span>
            <h4 className="fw-bold text-start my-2 lh-base">
              12 Mistakes To Avoid When Buying Cannabis Online
            </h4>
            <p className="fw-small text-muted mb-2 fs-5 text-start mb-5 lh-base">
              Buying cannabis online can be a great way to get your hands on the
              products you need without leaving the comfort of your home. But …
            </p>
            <Link
              style={{ color: "#4ec059" }}
              className="fs-5 mt-auto text-decoration-underline fw-semibold"
              href="#"
            >
              Read More
            </Link>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "400px" }}>
        <Card className="text-center h-100 border-0 ">
          <Image
            className="card-img-top object-fit-cover w-100"
            src="/image/Growing-marijuana-indoors.png"
            alt="product"
            style={{ height: "240px" }}
          ></Image>

          <CardBody className="d-flex flex-column justify-content-between align-items-start px-0 mt-1">
            <span className="text-muted fw-lighter fs-6 ">
              January 20, 2025
            </span>
            <h4 className="fw-bold text-start my-2 lh-base">
              How To Store Cannabis and Keep it Fresh and Potent?
            </h4>
            <p className="fw-small text-muted mb-2 fs-5 text-start mb-5 lh-base">
              Cannabis packaging has advanced dramatically in recent years,
              whether your state has a medicinal marijuana programme, legal
              adult-use weed, or both. Most ...
            </p>
            <Link
              style={{ color: "#4ec059" }}
              className="fs-5 mt-auto text-decoration-underline fw-semibold"
              href="#"
            >
              Read More
            </Link>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "400px" }}>
        <Card className="text-center h-100 border-0 ">
          <Image
            className="card-img-top object-fit-cover w-100"
            src="/image/lol_Cannabis.png"
            alt="product"
            style={{ height: "240px" }}
          ></Image>

          <CardBody className="d-flex flex-column justify-content-between align-items-start px-0 mt-1">
            <span className="text-muted fw-lighter fs-6 ">
              January 19, 2025
            </span>
            <h4 className="fw-bold text-start my-2 lh-base">
              The Ultimate Guide to Checking the Quality of Cannabis – 10
              Industry Leading Tips
            </h4>
            <p className="fw-small text-muted mb-2 fs-5 text-start mb-5 lh-base">
              Quality cannabis is a term used to describe cannabis products that
              meet specific standards of excellence. It is essential to
              understand what quality cannabis means, …
            </p>
            <Link
              style={{ color: "#4ec059" }}
              className="fs-5 mt-auto text-decoration-underline fw-semibold"
              href="#"
            >
              Read More
            </Link>
          </CardBody>
        </Card>
      </SwiperSlide>
      <SwiperSlide style={{ width: "400px" }}>
        <Card className="text-center h-100 border-0 ">
          <Image
            className="card-img-top object-fit-cover w-100"
            src="/image/Cannabis-research-1-scaled.png"
            alt="product"
            style={{ height: "240px" }}
          ></Image>

          <CardBody className="d-flex flex-column justify-content-between align-items-start px-0 mt-1">
            <span className="text-muted fw-lighter fs-6 ">
              January 24, 2023
            </span>
            <h4 className="fw-bold text-start my-2 lh-base">
              12 Mistakes To Avoid When Buying Cannabis Online
            </h4>
            <p className="fw-small text-muted mb-2 fs-5 text-start mb-5 lh-base">
              Buying cannabis online can be a great way to get your hands on the
              products you need without leaving the comfort of your home. But …
            </p>
            <Link
              style={{ color: "#4ec059" }}
              className="fs-5 mt-auto text-decoration-underline fw-semibold"
              href="#"
            >
              Read More
            </Link>
          </CardBody>
        </Card>
      </SwiperSlide>
    </Swiper>
  );
}
