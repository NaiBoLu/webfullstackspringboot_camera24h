"use client";

/* xin chao cac ban minh la body Homepage cua client layout hehehe */
import {
  Carousel,
  Col,
  Container,
  Row,
  Image,
  Tabs,
  Tab,
  Card,
  Button,
} from "react-bootstrap";
import CarouselItem from "react-bootstrap/CarouselItem";
import CarouselCaption from "react-bootstrap/CarouselCaption";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {} from "@fortawesome/free-solid-svg-icons";
import SwiperProduct from "@/components/client/swiperproduct";
import Link from "next/link";
import ChooseProduct from "@/components/client/chooseproduct";
import Referfriendbanner from "@/components/client/referfriendbanner";
import SwiperEducation from "@/components/client/swipereducation";
import OrderBanner from "@/components/client/orderbanner";
import BackToTop from "@/components/client/BackToTop";

/* phan body cua homepage cuar client page layout */
export default function HomePage() {
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

      {/* phan body khieu goi cua e: cc tao nao co ghi vay  */}
      <main className=" my-5 flex-grow-1 ">
        {/* */}

        {/*phần reliable shipping */}
        <Container
          fluid
          style={{ backgroundColor: "#F2F6F4" }}
          className="my-5 py-5 px-5"
        >
          <Row className="text-start py-4">
            <Col lg={4} md={6} className="d-flex align-items-start">
              <Image src="/globe.svg" width={40} height={40} className="me-5" />
              <div>
                <h5 className="fw-bold mb-4 ">Reliable Shipping</h5>
                <p className="text-muted fs-6 fw-semi-bold">
                  Green Society provides Canada Post Xpress Shipping right to
                  your doorstep! You can also opt in for shipping insurance. For
                  orders over $149, shipping is free!
                </p>
              </div>
            </Col>
            <Col lg={4} md={6} className="d-flex align-items-start">
              <Image
                src="/window.svg"
                width={40}
                height={40}
                className="me-5"
              />
              <div>
                <h5 className="fw-bold mb-4 ">You’re Safe With Us</h5>
                <p className="text-muted fs-6 fw-semi-bold">
                  Our secure payment system accepts the most common forms of
                  payments making the checkout process quicker! The payments we
                  accept are debit, all major credit cards, and cryptocurrency.
                </p>
              </div>
            </Col>
            <Col lg={4} md={6} className="d-flex align-items-start">
              <Image src="/file.svg" width={40} height={40} className="me-5" />
              <div>
                <h5 className="fw-bold mb-4 ">Best Quality & Pricing</h5>
                <p className="text-muted fs-6 fw-semi-bold">
                  Here at Green Society, we take pride in the quality of our
                  products and service. Our prices are set to ensure you receive
                  your medication at a reasonable price and safely
                </p>
              </div>
            </Col>
          </Row>
        </Container>

        {/* choose your weed */}
        <Container className="text-start py-5">
          <h1
            className="text-start fw-bold w-75 py-5"
            style={{ fontSize: "60px" }}
          >
            CHOOSE YOUR WEED
          </h1>

          {/* tab */}
          <div>
            <h5 className="pe-4 mb-1 fw-semibold ">Filter by Interest</h5>
            <div>
              <Tabs
                defaultActiveKey="flowers"
                id="uncontrolled-tab-example"
                className="w-75 "
              >
                <Tab eventKey="flowers" title="Flowers">
                  <ChooseProduct></ChooseProduct>
                </Tab>
                <Tab eventKey="mushrooms" title="Mushrooms">
                  <ChooseProduct></ChooseProduct>
                </Tab>
                <Tab eventKey="concentrate" title="Concentrate">
                  <ChooseProduct></ChooseProduct>
                </Tab>
                <Tab eventKey="edibles" title="Edibles">
                  <ChooseProduct></ChooseProduct>
                </Tab>
                <Tab eventKey="shopallweed" title="Shop All Weed">
                  <ChooseProduct></ChooseProduct>
                </Tab>
              </Tabs>
            </div>
          </div>
        </Container>

        {/* Weed education */}
        <Container className="my-5 py-5" style={{ backgroundColor: "f4f4f4" }}>
          <div className="d-flex align-items-start">
            <h2 className="fw-bold">WEED EDUCATION</h2>
            <Link
              style={{ color: "#4ec059" }}
              href="#"
              className=" ms-auto text-decoration-underline fw-semibold"
            >
              Show All
            </Link>
          </div>
          <hr className=" mb-5 border-secondary opacity-50 pb-4"></hr>

          <SwiperEducation></SwiperEducation>
        </Container>

        {/* banner order */}
        <OrderBanner></OrderBanner>
      </main>
    </>
  );
}
