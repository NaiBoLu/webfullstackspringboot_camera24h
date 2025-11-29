import { Button, Col, Container, Row } from "react-bootstrap";

export default function () {
  return (
    <Container
      style={{ minHeight: "300px", marginBottom: "-230px" }}
      className="position-relative"
    >
      <div
        className="position-absolute
                      start-50 translate-middle-x p-4 rounded-4 w-100"
        style={{ background: "linear-gradient(to right, #0a3d0a, #3c8d3c)" }}
      >
        <Row className="align-items-center">
          <Col
            md={8}
            className="text-center text-md-start mt-md-0 d-flex flex-column justify-content-center"
            style={{ minHeight: "250px" }}
          >
            <h1
              className="text-white mb-0 ms-5 fw-semibold"
              style={{ fontSize: "3.5rem" }}
            >
              REFER A FRIENDS
            </h1>
            <p className="text-white mb-0 mt-3 ms-5 fs-4 fw-semibold ">
              And get <span className="ms-3 text-warning fw-bold">$30!</span>
            </p>
          </Col>
          <Col md={4} className="text-md-start mt-3 mt-md-0 ">
            <Button
              variant="success"
              className="refer-button rounded-pill"
              style={{ minHeight: "50px", minWidth: "200px" }}
            >
              Refer Here
            </Button>
          </Col>
        </Row>
      </div>
    </Container>
  );
}
