import React, { useState } from "react";
import Modal from "../modal";
import { Field, FieldProps, Form, Formik, FormikHelpers } from "formik";
import * as yup from "yup";
import Input from "../input";
import Button from "../button";
import { useNavigate } from "react-router-dom";
import { BASE_URL } from "../../utils";

interface RegistrationRequest{
  emailAddress: string,
  name: string,
  phoneNumber: string,
  password: string,
  userType: string
}

interface IProps {
  type?: "CREATE" | "UPDATE";
  initialValue?: RegistrationRequest;
}

function RegisterForm({ type = "CREATE", initialValue }: IProps) {
  const [isOpen, setOpen] = useState(false);
  const [message, setMessage] = useState<string>();
  const navigate = useNavigate();
  const handleNavigate = () => {
    navigate(`/home`);
  }

  const closeModal = () => {
    setOpen(false);
  };


  const initialValues: RegistrationRequest = initialValue ?? {
    emailAddress: "",
    name: "",
    phoneNumber: "",
    password: "",
    userType:"CUSTOMER"
  };

  const onSubmit = async (request: RegistrationRequest, helpers: FormikHelpers<RegistrationRequest>) => {
    helpers.setSubmitting(false);
    const loginResponse = await fetch(BASE_URL + "/api/v1/register", {
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
    setMessage("Success - You can close the dialog then login");
  };

  return (
    <div>
      <Button onClick={() => setOpen(true)}>Register</Button>

      <Modal isOpen={isOpen} close={closeModal}>
        <div className="w-screen max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
          <h4 className="text-center text-xl font-semibold mb-6">Register</h4>

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
                      label="Email Address"
                      placeholder="Email"
                      error={meta.touched && meta.error}
                      {...field}
                    />
                  )}
                </Field>

                <Field name="name">
                  {({ field, meta }: FieldProps) => (
                    <Input
                      label="Name"
                      placeholder="Name"
                      error={meta.touched && meta.error}
                      {...field}
                    />
                  )}
                </Field>

                <Field name="phoneNumber">
                  {({ field, meta }: FieldProps) => (
                    <Input
                      label="Phone Number"
                      placeholder="Phone Number"
                      error={meta.touched && meta.error}
                      {...field}
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
                    />
                  )}
                </Field>
                {
                    message && <p className={message.startsWith("Success")? "text-green-500" : "text-red-500"}>{message}</p>
                  }
                {/* Buttons */}
                <div className="flex justify-center gap-4 mt-6">
                  <Button type="submit" className="w-full" variant="SOLID">
                    Register
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

export default RegisterForm;
