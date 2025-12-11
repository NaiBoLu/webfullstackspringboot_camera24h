"use client";

import React from "react";
import { useRouter } from "next/navigation";
import {
  Button,
  Card,
  Col,
  Container,
  Row,
  Image,
  Badge,
} from "react-bootstrap";

export default function ProductDetailPage() {
  return (
    <Container className="py-5">
      <Row>
        <Col
          lg={6}
          className="d-flex align-items-center flex-column  rounded pb-3"
        >
          <Image
            src="/products/product_1.png"
            alt="Product Image"
            width={400}
            height={400}
            rounded
            className="p-3"
            style={{ backgroundColor: "#F4F4F4" }}
          />
          <div
            className="d-flex justify-content-center mt-5 gap-2 p-2"
            style={{ backgroundColor: "#F4F4F4" }}
          >
            <Image
              src="/products/product_1.png"
              alt="Product Image"
              width={80}
              height={80}
            />
            <Image
              src="/products/product_1.png"
              alt="Product Image"
              width={80}
              height={80}
            />
            <Image
              src="/products/product_1.png"
              alt="Product Image"
              width={80}
              height={80}
            />
            <Image
              src="/products/product_1.png"
              alt="Product Image"
              width={80}
              height={80}
            />
          </div>
        </Col>
        <Col lg={6} style={{ backgroundColor: "white" }}>
          <p
            className="text-muted fs-bold text-uppercase"
            style={{ letterSpacing: "4px" }}
          >
            {" "}
            Concentrates
          </p>
          <h3 className="fs-2 w-75">
            {" "}
            Mix And Match Shatter/Budder 28g (4 x 7g)
          </h3>

          <div className="my-2">
            <Badge
              bg="light"
              className="me-2 fs-6 fw-semibold fs-5"
              style={{ color: "#05422C" }}
            >
              Indica
            </Badge>
            <Badge
              bg="light"
              className="fs-6 fw-semibold"
              style={{ color: "#05422C" }}
            >
              Sativa 100%
            </Badge>
          </div>

          <div className="d-flex justify-content-between align-items-center  mt-3">
            <div>
              <del className="text-muted fw-semibold fs-5 me-3">$200.00</del>
              <span className="text-danger fw-semibold fs-4">$120.00</span>
            </div>
            <div className="d-flex  align-items-center gap-2">
              <i className="bi bi-star-fill text-warning fs-4"></i>
              <span className="fs-6 fw-semibold">4.6/5</span>
              <span className="text-muted fw-semibold"> | 135 Reviews</span>
            </div>
          </div>
          <div className="p-3 border text-muted rounded my-3">
            <h6>🌀 Effects</h6>
            <p>Calming, Creative, Happy, Relaxing, Sleepy, Uplifting</p>
            <h6>❤️ May Relieve</h6>
            <p>
              Anxiety, Arthritis, Chronic Pain, Depression, Fatigue,
              Inflammation, Insomnia
            </p>
            <h6>🌸 Aromas</h6>
            <p>Chemical, Citrus, Earthy, Pungent, Sour</p>
          </div>

          <p className="text-uppercase text-muted my-3 small">Description</p>
          <p className="text-muted small">
            Jungle Diamonds is a slightly indica dominant hybrid strain (60%
            indica/40% sativa) created through crossing the infamous Slurricane
            X Gorilla Glue #4 strains.
          </p>

          <div className="my-5 d-flex">
            <Button
              type="button"
              variant="success"
              className="rounded-pill p-3 ms-auto"
            >
              {" "}
              Add to Cart | $242.00
            </Button>
          </div>
        </Col>
      </Row>
    </Container>
  );
}
