import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';

const Mypage = () => {
    const { user, loading } = useAuth();
    const [userInfo, setUserInfo] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUserInfo = async () => {
            const token = user?.token || localStorage.getItem('authToken');
            if (token) {
                try {
                    console.log('Fetching user info with token:', token);
                    const response = await axios.get('/api/mypage', {
                        headers: { 
                            Authorization: `Bearer ${token}`
                        }
                    });
                    console.log('User info fetched successfully:', response.data);
                    setUserInfo(response.data);
                } catch (error) {
                    console.error('Failed to fetch user info:', error);
                    setError('Failed to fetch user info');
                }
            } else {
                setError('No user token found');
            }
        };

        if (!loading) {
            fetchUserInfo();
        }
    }, [loading, user]);

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div className="Mypage">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {userInfo ? (
                <div>
                    <h2>User Info</h2>
                    <p>ID: {userInfo.id}</p>
                    <p>Name: {userInfo.name}</p>
                    <p>Phone: {userInfo.phone}</p>
                    <p>Sex: {userInfo.sex}</p>
                    <p>Height: {userInfo.height}</p>
                    <p>Weight: {userInfo.weight}</p>
                    <p>Birth: {userInfo.birth}</p>
                    <p>Firstday: {userInfo.firstday}</p>
                    <p>Restday: {userInfo.restday}</p>
                    {userInfo.photo && (
                        <img src={`data:image/jpeg;base64,${userInfo.photo}`} alt="Profile" />
                    )}
                </div>
            ) : (
                <p>No user info available.</p>
            )}
        </div>
    );
};

export default Mypage;
