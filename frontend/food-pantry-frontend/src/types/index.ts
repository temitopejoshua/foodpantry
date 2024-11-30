import exp from "constants";

export interface IValue {
  label: string;
  value: string;
  amount: number;
  selected: boolean;
}
export type RequestStatus = "APPROVED" | "PENDING" | "EXECUTED" | "REJECTED" | "DISTRIBUTED";

export interface IRequest {
  name: string;
  email: string;
  email1: string;
  requestStatus: RequestStatus;
  values: IValue[];
}

export interface ISelectValue {
  value: any;
  label: string | React.ReactNode | Date;
}

export interface IApproval {
  id: number;
  name: string;
  username: string;
  status: RequestStatus;
  message: string;
}
export interface LoginRequest{
  emailAddress: string;
  password: string;
}

export interface DonationResponse{
    status: RequestStatus,
    quantity: number,
    foodType: string,
    donatedBy: string,
    dateDonated: string
}