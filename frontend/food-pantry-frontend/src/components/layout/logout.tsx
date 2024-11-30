import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import Button from '../button';

function Logout() {
    const [isAuthenticated, setAuthenticated] = useState<string | null>();
    const navigate = useNavigate();

    const handleNavigate = () => {
        navigate(`/`);
    }

    useEffect(() => {
        const chechAuthenticated = async () => {
            const authenticated = localStorage.getItem("isAuthenticated");
            setAuthenticated(authenticated);
        };
        chechAuthenticated();
    }, []);



    const handleLogout = () => {
        localStorage.clear();
        setAuthenticated("false")
        handleNavigate();
    }

    return (
        <div className="flex items-center mr-20">
            {isAuthenticated && <Button
                className="text-black font-medium hover:underline hover:text-gray-700"
                onClick={() => handleLogout()}
            >
                Logout
            </Button>}

        </div>
    );
}

export default Logout;
