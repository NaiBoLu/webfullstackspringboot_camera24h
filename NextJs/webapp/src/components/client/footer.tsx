import Link from "next/link";
import { Col, Container, Image, Row } from "react-bootstrap";

export default function Footer() {
  return (
    <>
      <footer
        className="mt-auto  text-start text-white"
        style={{
          backgroundColor: "#01100B",
          paddingTop: "300px",
          paddingBottom: "30px",
        }}
      >
        <Container className="pt-5">
          <Row>
            {/* TOP SHELF */}
            <Col lg={4}>
              <h1 className="mb-4">TOP SHELF</h1>
              <p className="w-75 lh-lg" style={{ color: "#9D9EA2" }}>
                #1 Canadian top rated online dispensary that meets the customers
                needs in every single medical marijuana aspect. The team here at
                TopShelfBC is heavily involved in the Canadian cannabis industry
                for over 15 years. We strive to provide the top quality
                products, service and care at the lowest prices you’ll ever
                find.
              </p>
            </Col>
            {/* QUICK LINK, CONTACT US, MORE */}
            <Col lg={8}>
              <h2 className="mb-4">QUICK LINK</h2>
              <Row style={{ color: "#9D9EA2" }}>
                <Col lg={6} className="d-flex flex-column gap-3">
                  <span>Track Your Order</span>
                  <span>Shop All</span>
                  <span>Flower</span>
                  <span>Edibles</span>
                  <span>Concentrates</span>
                  <span>Refunds</span>
                </Col>
                <Col lg={6} className="d-flex flex-column gap-3">
                  <span>Mushrooms</span>
                  <span>Promotions / Bundles</span>
                  <span>Support</span>
                  <span>Reward</span>
                  <span>Blog</span>
                  <span>Shipping Faq</span>
                </Col>
              </Row>
              <h2 className="my-4">CONTACT US</h2>
              <Row style={{ color: "#9D9EA2" }}>
                <Col lg={6} className="d-flex flex-column gap-3">
                  <span>info@topshelfbc.cc</span>
                </Col>
              </Row>
              <h2 className="my-4">MORE</h2>
              <Row style={{ color: "#9D9EA2" }}>
                <Col lg={6} className="d-flex flex-column gap-3">
                  <span>Buy weed online in Canada</span>
                  <span>Buy weed online in New Brunswick</span>
                  <span>Buy weed online in Prince Edward Island</span>
                  <span>Buy weed online in Northwest Territories</span>
                  <span>Buy weed online in Saskatchewan</span>
                </Col>
                <Col lg={6} className="d-flex flex-column gap-3">
                  <span>Buy weed online in Manitoba</span>
                  <span>Buy weed online in Quitebec</span>
                  <span>Buy weed online in British Columbia</span>
                  <span>Buy weed online in Ontario</span>
                  <span>Buy weed online in Alberta</span>
                </Col>
              </Row>

              {/* visa icon */}
              <div className=" my-5 d-flex gap-4">
                <Image src="/file.svg" width={40} height={40}></Image>
                <Image src="/globe.svg" width={40} height={40}></Image>
                <Image src="/vercel.svg" width={40} height={40}></Image>
                <Image src="/window.svg" width={40} height={40}></Image>
              </div>
            </Col>
          </Row>

          {/* copy right bootom footer */}
          <hr className="my-4 border-white opacity-50" />
          <Container
            style={{ color: "#9D9EA2" }}
            className="d-flex justify-content-between"
          >
            {/* Nội dung bên trái */}
            <p className="pt-3">© 2025 Top Shelf BC. All Rights Reserved.</p>

            {/* Nội dung bên phải */}
            <div className="d-flex gap-3 pt-3">
              <Link href="#">Out Of Stock</Link>
              <Link href="#">Privacy Policy</Link>
              <Link href="#">Terms & Conditions</Link>
            </div>
          </Container>
        </Container>
      </footer>
    </>
  );
}
