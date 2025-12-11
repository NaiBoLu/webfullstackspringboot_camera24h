"use client";

import { Form, Button, Card, Container } from "react-bootstrap";
import Link from "next/link";

export default function SignupPage() {
  return (
    <div>
      <Container className="d-flex justify-content-center align-items-center vh-100">
        <Card className="glass p-4" style={{ width: "400px" }}>
          <h3 className="text-center fw-bold mb-4 text-white">
            Create Account
          </h3>

          <Form>
            <Form.Group className="mb-3">
              <Form.Label className="text-white">Username</Form.Label>
              <Form.Control type="text" placeholder="Enter username" />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label className="text-white">Email</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label className="text-white">Password</Form.Label>
              <Form.Control type="password" placeholder="Enter password" />
            </Form.Group>

            <Button
              variant="success"
              type="submit"
              className="w-100 btnGradient"
            >
              Sign Up
            </Button>

            <p className="text-center mt-3 text-white">
              Already have an account?{" "}
              <Link href="/client/login" className="link">
                Login
              </Link>
            </p>
          </Form>
        </Card>
      </Container>
    </div>
  );
}
