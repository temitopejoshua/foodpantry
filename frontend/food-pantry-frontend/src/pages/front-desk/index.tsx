import React, { useEffect, useState } from 'react';
import Container from "../../components/container";
import Card, { CardBody } from "../../components/card";
import Table from "../../components/table";
import Thead from "../../components/table/Thead";
import Th from "../../components/table/Th";
import TBody from "../../components/table/TBody";
import Tr from "../../components/table/Tr";
import Td from "../../components/table/Td";
import RequestStatusComponent from "../../components/requestStatus";
import DonationRequestForm from '../../components/requestForm/donate';
import { RequestStatus } from '../../types';
import { BASE_URL, USER_TYPE } from '../../utils';
import { useNavigate } from "react-router-dom";

interface IProps { }

interface Donation {
  id: number;
  status: RequestStatus;
  quantity: number;
  foodType: string;
  donatedBy: string;
  dateDonated: string;
}

function FrontDeskHomePage(props: IProps) {
  const [donations, setDonations] = useState<Donation[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [name, setName] = useState<string | null>();
  
  const fetchDonations = async () => {

    setName(localStorage.getItem("customerName"));
    try {
      const response = await fetch(BASE_URL + '/api/v1/donations', {
        method: 'GET',
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch donations: ${response.statusText}`);
      }

      const data = await response.json();
      setDonations(data.content);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {


    fetchDonations();
  }, []);

  return (
    <Container className="py-10">
      <Card>
        <CardBody>
          <div className="flex justify-between gap-10 items-center">
            <h4>Welcome Front-Desk, {name}</h4>
            <DonationRequestForm fetchDonation={fetchDonations}/>
          </div>

          <div className="mt-7">
            {loading ? (
              <p>Loading donations...</p>
            ) : error ? (
              <p className="text-red-500">Error: {error}</p>
            ) : (
              <Table>
                <Thead>
                  <Th>Date Donated</Th>
                  <Th>Quantity (in LBS)</Th>
                  <Th>Food Type</Th>
                  <Th>Donated By</Th>
                  <Th>Status</Th>
                </Thead>
                <TBody>
                  {donations.map((donation) => (
                    <Tr key={donation.id}>
                      <Td>{donation.dateDonated}</Td>
                      <Td>{donation.quantity}</Td>
                      <Td>{donation.foodType}</Td>
                      <Td>{donation.donatedBy}</Td>
                      <Td><RequestStatusComponent status={donation.status} /></Td>
                    </Tr>
                  ))}
                </TBody>
              </Table>
            )}
          </div>
        </CardBody>
      </Card>
    </Container>
  );
}

export default FrontDeskHomePage;
