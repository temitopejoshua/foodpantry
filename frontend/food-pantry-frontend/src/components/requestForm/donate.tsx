import React, { useState } from 'react';
import { Icon } from "@iconify/react";
import Button from "../button";
import Modal from "../modal";
import { Field, FieldProps, Form, Formik, FormikHelpers } from "formik";
import Input from "../input";
import Select from "../select";
import { BASE_URL, USER_TYPE } from '../../utils';

interface IProps {
  type?: "CREATE" | "UPDATE"
  initialValue?: DonationRequest,
  fetchDonation: () => void;
}
interface DonationRequest {
  foodType: string,
  quantity: number
}

function DonationRequestForm({ type = "CREATE", initialValue, fetchDonation }: IProps) {
  const [isOpen, setOpen] = useState<boolean>(false);
  const [message, setMessage] = useState<string>();

  const closeModal = () => {
    setOpen(false);
  }

  const initialValues: DonationRequest = initialValue ?? {
    foodType: "",
    quantity: 0
  }

  const onSubmit = async (request: DonationRequest, helpers: FormikHelpers<DonationRequest>) => {
    helpers.setSubmitting(true);
    const loginResponse = await fetch(BASE_URL + "/api/v1/donations/donate", {
      method: 'PUT',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request)
    });

    const responseJSON = await loginResponse.json();
    helpers.setSubmitting(false)
    if (loginResponse.status !== 200) {
      setMessage(responseJSON.message);
      return;
    }
    fetchDonation();
    closeModal();


  }

  return (
    <>
    <div hidden={USER_TYPE === "EMPLOYEE"}>
    <Button className="pl-3 gap-1" onClick={() => setOpen(true)}>
        <>
          <Icon icon="mdi:plus" width={24} height={24} />
          <span>Donate</span>
        </>
      </Button>
    </div>
     

      <Modal isOpen={isOpen} close={closeModal}>
        <div className="w-screen max-w-5xl p-5">
          <h4>
            Donate Food
          </h4>

          <Formik
            initialValues={initialValues}
            onSubmit={onSubmit}
            enableReinitialize
          >
            {({ isSubmitting, isValid, values }) => (
              <Form className="mt-5">
                <div className="grid grid-cols-3 gap-x-5 gap-y-4">
                  <Field name="quantity">
                    {({ field, meta }: FieldProps) => (
                      <Input
                        label="Quantity"
                        placeholder="Quantity in pounds"
                        error={meta.touched && meta.error}
                        type='number'
                        {...field}
                      />
                    )}
                  </Field>

                  <Select
                    label="Food Type"
                    placeholder="Select food Type"
                    name="foodType"
                    options={["RICE", "BEANS", "POTATOES", "CHICKEN", "BREAD", "CEREAL"].map(v => ({ label: v, value: v }))}
                  />
                </div>
                {
                  message && <p className="text-red-500">{message}</p>
                }
                <div className="flex items-center gap-4 justify-start mt-10">
                  <Button type="button" onClick={closeModal}>
                    Close
                  </Button>
                  <Button variant="SOLID" type="submit">
                    {isSubmitting ? "Loading..." : "Donate"}
                  </Button>
                </div>
              </Form>
            )}
          </Formik>
        </div>
      </Modal>
    </>
  );
}

export default DonationRequestForm;
