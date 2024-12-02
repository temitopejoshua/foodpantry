import React, { useState } from "react";
import Modal from "../modal";
import { Field, FieldProps, Form, Formik, FormikHelpers } from "formik";
import Input from "../input";
import {LoginRequest } from "../../types";
import Button from "../button";
import { useNavigate } from "react-router-dom";
import { BASE_URL } from "../../utils";

interface IProps {
  type?: "CREATE" | "UPDATE";
  initialValue?: LoginRequest;
}

function LoginForm({ type = "CREATE", initialValue }: IProps) {
  const [isOpen, setOpen] = useState<boolean>(false);
  const [message, setMessage] = useState<string>();
  const navigate = useNavigate();
  
  const handleNavigate = () => {
    if(localStorage.getItem("userType") === "EMPLOYEE"){
      if(localStorage.getItem("role") === "FRONT_DESK"){
        navigate(`/front-desk/home`);
        return;
      }else{
        navigate(`/admin/home`);
      }
      
      return;
    }
    navigate(`/customer/home`);
  }

  const closeModal = () => {
    setOpen(false);
  };

  const initialValues: LoginRequest = initialValue ?? {
    emailAddress: "",
    password:""
  };

  const onSubmit = async (request: LoginRequest, helpers: FormikHelpers<LoginRequest>) => {
    helpers.setSubmitting(false);
    const loginResponse = await fetch(BASE_URL + "/api/v1/login", {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request)
    });

    const responseJSON = await loginResponse.json();
    if(loginResponse.status !==200){
      setMessage(responseJSON.message);
      return;
    }
    localStorage.setItem("isAuthenticated", "true");
    localStorage.setItem("customerName", responseJSON.name);
    localStorage.setItem("userType", responseJSON.userType);
    localStorage.setItem("role", responseJSON.role);

      handleNavigate();

  };
  const handleOpen = async() => {
    setOpen(true);
    if(localStorage.getItem("isAuthenticated")){
      handleNavigate();
    }
  }

  return (
    <div>
      <Button onClick={() => handleOpen()}>Login</Button>

      <Modal isOpen={isOpen} close={closeModal}>
        <div className="w-screen max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
          <h4 className="text-center text-xl font-semibold mb-6">Login</h4>

          <Formik
            initialValues={initialValues}
            onSubmit={onSubmit}
            enableReinitialize
          >
            {({ isSubmitting, isValid }) => (
              <Form className="flex flex-col gap-4
              ">
                {/* Email Field */}
                <Field name="emailAddress">
                  {({ field, meta }: FieldProps) => (
                    <Input
                      label="Email"
                      placeholder="Email"
                      error={meta.touched && meta.error}
                      {...field}
                      required
                      type="email"
                    />
                  )}
                </Field>
                {/* Password Field */}
                <Field name="password">
                  {({ field, meta }: FieldProps) => (
                    <Input
                      label="Password"
                      placeholder="Password"
                      error={meta.touched && meta.error}
                      type="password"
                      {...field}
                      required
                    />
                  )}
                </Field>
                  {
                    message && <p className="text-red-500">{message}</p>
                  }

                {/* Buttons */}
                <div className="flex justify-center gap-4 mt-6">
                  <Button type="submit" className="w-full" variant="SOLID">
                    Login
                  </Button>
                </div>
              </Form>
            )}
          </Formik>
        </div>
      </Modal>
    </div>
  );
}

export default LoginForm;
