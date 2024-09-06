import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';
import { Link } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';

const Mypage = () => {
    const { user, loading } = useAuth();
    const [userInfo, setUserInfo] = useState(null);
    const [error, setError] = useState('');
    const [image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
    const fileInput = useRef(null);

    const fetchUserInfo = async () => {
        const token = user?.token || localStorage.getItem('authToken');
        if (token) {
            try {
                const response = await axios.get('/api/mypage', {
                    headers: { 
                        Authorization: `Bearer ${token}`
                    }
                });
                setUserInfo(response.data);
                setImage(response.data.photo || "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
            } catch (error) {
                setError('Failed to fetch user info');
            }
        } else {
            setError('No user token found');
        }
    };

    useEffect(() => {
        if (!loading) {
            fetchUserInfo();
        }
    }, [loading, user]);

    const onChange = async (e) => {
        if (e.target.files[0]) {
            const file = e.target.files[0];
            const formData = new FormData();
            formData.append('file', file);

            try {
                await axios.put('/api/user/update-photo', formData, {
                    headers: {
                        'Authorization': `Bearer ${user?.token || localStorage.getItem('authToken')}`,
                        'Content-Type': 'multipart/form-data'
                    }
                });
                // Refresh user info after upload
                fetchUserInfo();
            } catch (error) {
                setError('Failed to upload photo');
            }
        } else {
            setImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div className="Mypage">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {userInfo ? (
                <div>
                    <h2>User Info</h2>
                    <Avatar 
                        src={image + `?t=${new Date().getTime()}`}  // Cache-busting
                        style={{ margin: '40px auto', cursor: 'pointer' }} 
                        sx={{ width: 160, height: 160 }} 
                        onClick={() => fileInput.current.click()} 
                    />
                    <input 
                        type='file' 
                        style={{ display: 'none' }} 
                        accept='image/jpg,image/png,image/jpeg' 
                        name='profile_img' 
                        onChange={onChange} 
                        ref={fileInput}
                    />
                    <p>ID: {userInfo.id}</p>
                    <p>Name: {userInfo.name}</p>
                    <p>Phone: {userInfo.phone}</p>
                    <p>Sex: {userInfo.sex}</p>
                    <p>Height: {userInfo.height}</p>
                    <p>Weight: {userInfo.weight}</p>
                    <p>Birth: {userInfo.birth}</p>
                    <p>Firstday: {userInfo.firstday}</p>
                    <p>Restday: {userInfo.restday}</p>
                    <Link to="/edit-profile">Edit Profile</Link>
                </div>
            ) : (
                <p>No user info available.</p>
            )}
        </div>
    );
};

export default Mypage;

