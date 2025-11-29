"use client";

import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import CardBody from "react-bootstrap/CardBody";
import Button from "react-bootstrap/Button";
import "./products.css";
import Link from "next/link";
import Image from "react-bootstrap/Image";
import { useState } from "react";
import Offcanvas from "react-bootstrap/Offcanvas";
import Dropdown from "react-bootstrap/Dropdown";

export default function ProductPage() {
  const [selected, setSelected] = useState("Sort by");

  const handleSelected = (option: string) => {
    setSelected(`Sort by ${option}`);
  };

  const [show, setShow] = useState(false);

  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <Container className="my-5 pb-5">
      <div className="row">
        {/* Filter */}
        <div className="col-lg-3 col-md-0">
          {/* Filter desktop */}

          <Button
            variant="light"
            onClick={handleShow}
            className="
                    fs-5 rounded-pill border  d-lg-none d-block mb-3 w-25"
          >
            Filter
          </Button>
          <Offcanvas
            show={show}
            onHide={handleClose}
            placement="start"
            responsive="lg"
          >
            <Offcanvas.Header closeButton></Offcanvas.Header>
            <Offcanvas.Body className="d-flex flex-column border p-3">
              <h5 className="fs-5 pt-2 pb-3">Filters</h5>
              <p className="text-muted medium mb-3">PRODUCT CATEGORY</p>
              <ul className="list-unstyled fs-6 d-flex gap-3 flex-column">
                <li>
                  <input type="radio" name="cat" /> Sales
                </li>
                <li>
                  <input type="radio" name="cat" /> Cannabis
                </li>
                <li>
                  <input type="radio" name="cat" /> Pre-Rolls
                </li>
                <li>
                  <input type="radio" name="cat" /> CBD Oil
                </li>
                <li>
                  <input type="radio" name="cat" /> Extracts
                </li>
                <li>
                  <input type="radio" name="cat" /> Edibles
                </li>
                <li>
                  <input type="radio" name="cat" /> Vape Pens
                </li>
                <li>
                  <input type="radio" name="cat" /> Accessories
                </li>
                <li>
                  <input type="radio" name="cat" /> Bath & Body
                </li>
                <li>
                  <input type="radio" name="cat" /> Bundles
                </li>
                <li>
                  <input type="radio" name="cat" /> Wholesale
                </li>
              </ul>
            </Offcanvas.Body>
          </Offcanvas>
        </div>
        {/* Shop content */}
        <div className="col-lg-9 col-md-12">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2 className="fs-4">Shop</h2>

            <Dropdown>
              {/* Dropdown.Toggle: Thay thế Button cũ */}
              <Dropdown.Toggle
                variant="light"
                id="dropdown-basic"
                className="rounded-pill border fs-6 py-2" // Giữ lại styling
              >
                {selected}
                {/* Biểu tượng mũi tên sẽ được xử lý tự động, nhưng bạn có thể thêm lại icon nếu muốn */}
                <i className="bi bi-chevron-down text-muted ps-2"></i>
              </Dropdown.Toggle>

              {/* Dropdown.Menu: Thay thế ul */}
              <Dropdown.Menu className="border">
                {/* Dropdown.Item: Thay thế li > a */}
                <Dropdown.Item onClick={() => handleSelected("Newest")}>
                  Newest
                </Dropdown.Item>
                <Dropdown.Item onClick={() => handleSelected("Latest")}>
                  Latest
                </Dropdown.Item>
                <Dropdown.Item onClick={() => handleSelected("A - Z")}>
                  A - Z
                </Dropdown.Item>
                <Dropdown.Item onClick={() => handleSelected("Z - A")}>
                  Z - A
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </div>
          <div style={{ maxWidth: "864px" }}>
            <h4 className="fs-5 fw-semibold mt-5" style={{ color: "#17AF26" }}>
              Cannabis
            </h4>
            <p className="mt-4 fs-6 fw-normal" style={{ color: "#717378" }}>
              Here at WestCoastSupply’s “ cannabis section, we showcase the best
              Indica, Hybrid, and Sativa medical cannabis strain selections at
              the best prices online. You can be assured that all our strains go
              through a strict screening process to ensure that all your
              cannabis needs are top-quality. All of our flowers are sourced
              from reputable growers, based in British Columbia, Canada. We have
              hige grade selection comes from growers that produce AAAA+ quality
              cannabis flowers and have many years of experience in the cannabis
              industry. You are guaranteed to be receiving high-quality flowers
              at the best prices online with our unbeatable sales!
            </p>
          </div>
          <h5 className="mt-4 mb-3 fs-3 fw-semibold pt-2">Top Selling</h5>
          {/* product card */}
          <div className="row g-5 align-items-stretch">
            <div className="col-12 col-sm-6 col-md-4 col-lg-4 ">
              <Card className="card text-center h-100 ">
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
                    <span className="text-muted fs-6 fw-semibold">
                      | 135 Reviews
                    </span>
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
            </div>
            <div className="col-12 col-sm-6 col-md-4 col-lg-4 ">
              <Card className="card text-center h-100 ">
                <div
                  className="d-flex justify-content-center align-items-center shadow-sm"
                  style={{ backgroundColor: "#F4F4F4" }}
                >
                  <Image
                    className="card-img-top my-4 "
                    src="/products/product_2.png"
                    alt="product"
                    style={{ width: "170px", height: "170px" }}
                  ></Image>
                </div>
                <CardBody className="d-flex flex-column justify-content-between align-items-center">
                  <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
                    FLOWER
                  </h6>
                  <p className="fw-bold mb-2 fs-6 ">
                    2 Oz Deal Ahi Tuna + Master Tuna
                  </p>
                  <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
                    <i className="bi bi-star-fill text-warning"></i>
                    <span className="fs-6 fw-semibold">4.6/5</span>
                    <span className="text-muted fs-6 fw-semibold">
                      | 135 Reviews
                    </span>
                  </div>
                  <span
                    className="badge bg-light fs-6 fw-semibold "
                    style={{ color: "#05422C" }}
                  >
                    Sativa 100%
                  </span>
                  <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
                    <span className="text-danger fs-5 fw-semibold">
                      $120.00
                    </span>
                    <span className="text-muted fs-5 fw-normal">/ gram</span>
                  </div>
                  <div className="d-flex justify-content-center align-items-center gap-3 my-3">
                    <span className="fs-6 fw-semibold">28g</span>
                    <span className="fs-6 fw-semibold">1/2lb</span>
                    <span className="fs-6 fw-semibold">1/4lb</span>
                  </div>
                  <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5">
                    Add to card
                  </button>
                </CardBody>
              </Card>
            </div>
            <div className="col-12 col-sm-6 col-md-4 col-lg-4 ">
              <Card className="card text-center h-100 ">
                <div
                  className="d-flex justify-content-center align-items-center shadow-sm"
                  style={{ backgroundColor: "#F4F4F4" }}
                >
                  <Image
                    className="card-img-top my-4 "
                    src="/products/product_3.png"
                    alt="product"
                    style={{ width: "170px", height: "170px" }}
                  ></Image>
                </div>
                <CardBody className="d-flex flex-column justify-content-between align-items-center">
                  <h6 className="fw-normal fs-6" style={{ color: "#9D9EA2" }}>
                    CONCENTRATES
                  </h6>
                  <p className="fw-bold mb-2 fs-6 ">
                    Mix And Match Shatter/Budder 28g (4 X 7G)
                  </p>
                  <div className="d-flex  justify-content-center align-items-center gap-2 mb-2">
                    <i className="bi bi-star-fill text-warning"></i>
                    <span className="fs-6 fw-semibold">4.6/5</span>
                    <span className="text-muted fs-6 fw-semibold">
                      | 135 Reviews
                    </span>
                  </div>
                  <span
                    className="badge bg-light fs-6 fw-semibold "
                    style={{ color: "#05422C" }}
                  >
                    Indica 70%
                  </span>
                  <div className="d-flex justify-content-center align-items-center gap-2 mt-2">
                    <span className="text-danger fs-5 fw-semibold">
                      $102.00
                    </span>
                    <span className="text-muted fs-5 fw-normal">/ gram</span>
                  </div>
                  <div className="d-flex justify-content-center align-items-center gap-3 my-3">
                    <span className="fs-6 fw-semibold">28g</span>
                    <span className="fs-6 fw-semibold">1/2lb</span>
                    <span className="fs-6 fw-semibold">1/4lb</span>
                  </div>
                  <button className="btn btn-success text-light w-75 p-2 rounded-pill fs-5">
                    Add to card
                  </button>
                </CardBody>
              </Card>
            </div>
          </div>
        </div>
      </div>
    </Container>
  );
}
