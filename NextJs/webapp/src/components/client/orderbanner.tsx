import { Button, Container, Form } from "react-bootstrap";

export default function OrderBanner() {
  return (
    <Container
      className="position-relative"
      style={{ minHeight: "450px", marginBottom: "-250px" }}
    >
      <div
        className="position-absolute top-0 start-0 w-100 h-100 rounded-4 py-4"
        style={{ background: "linear-gradient(to right, #0a3d0a, #3c8d3c)" }}
      >
        <div className="ms-5 w-75">
          <h1
            className="text-white mt-5 w-75 mb-0 fw-semibold"
            style={{ fontSize: "3.5rem" }}
          >
            UNLOCK 20% OFF YOUR FIRST ORDER
          </h1>
          <p className="text-white opacity-50  mt-3 fs-4 fw-normal ">
            Reveal coupon code by entering your email
          </p>
          <hr className=" mb-5 border-white "></hr>
        </div>

        {/* form email */}
        <div className="d-flex custom-input-group  w-75 ms-5">
          {/* 1. Ô Input (Phần lớn) */}
          <Form.Control
            style={{ backgroundColor: "transparent", color: "white" }}
            type="email"
            placeholder="Email Address"
            className=" me-0 flex-grow-1 rounded-pill text-white border-1 fs-5 custom-placeholder" // me-0 để loại bỏ margin mặc định nếu có
          />

          {/* 2. Nút (Phần nhỏ) */}
          <Button
            variant="success"
            className="rounded-pill ms-5 fs-6"
            style={{ minHeight: "60px", minWidth: "200px" }}
          >
            Reveal coupon
          </Button>
        </div>
      </div>
    </Container>
  );
}
