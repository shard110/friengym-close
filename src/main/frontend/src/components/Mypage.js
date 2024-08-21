import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';

const Mypage = () => {
    const { user } = useAuth();
    const [userInfo, setUserInfo] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                if (!user || !user.token) {
                    throw new Error('User not authenticated or token missing');
                }
    
                const response = await axios.get(`/api/mypage`, {
                    headers: {
                        Authorization: `Bearer ${user.token}`
                    }
                });
                setUserInfo(response.data);
            } catch (error) {
                setError('Failed to fetch user info');
                console.error('Failed to fetch user info', error);
            }
        };
        console.log('User from context:', user);
        fetchUserInfo();
    }, [user]);

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
                    <img src={`data:image/jpeg;base64,${userInfo.photo}`} alt="Profile" />
                </div>
            ) : (
                <p>Loading user info...</p>
            )}
        </div>
    );
};

export default Mypage;
