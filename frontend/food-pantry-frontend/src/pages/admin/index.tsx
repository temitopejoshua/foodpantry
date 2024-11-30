import React, { useEffect, useState } from 'react';
import Container from "../../components/container";
import Card, { CardBody } from "../../components/card";
import Table from "../../components/table";
import Thead from "../../components/table/Thead";
import Th from "../../components/table/Th";
import TBody from "../../components/table/TBody";
import Tr from "../../components/table/Tr";
import Td from "../../components/table/Td";
import { BASE_URL } from '../../utils';

interface IProps { }

interface User {
  name: string;
  emailAddress: string;
  userType: string;
  lastLoggedInDate: string;
}

function HomePage(props: IProps) {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchDonations = async () => {    
    try {
      const response = await fetch(BASE_URL + '/api/v1/users', {
        method: 'GET',
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch donations: ${response.statusText}`);
      }

      const data = await response.json();
      setUsers(data.content);
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
            <h4>User List</h4>
          </div>

          <div className="mt-7">
            {loading ? (
              <p>Loading donations...</p>
            ) : error ? (
              <p className="text-red-500">Error: {error}</p>
            ) : (
              <Table>
                <Thead>
                  <Th>Customer Name</Th>
                  <Th>Customer Email</Th>
                  <Th>User Type</Th>
                  <Th>Last Logged In Date</Th>
                </Thead>
                <TBody>
                  {users.map((user) => (
                    <Tr key={user.emailAddress}>
                      <Td>{user.name}</Td>
                      <Td>{user.emailAddress}</Td>
                      <Td>{user.userType}</Td>
                      <Td>{user.lastLoggedInDate ? user.lastLoggedInDate : <p className='text-red-500'>Never logged In</p>}</Td>
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

export default HomePage;
