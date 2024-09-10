import React, { useState, useEffect, useRef, useCallback } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';
import { Link } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import './Mypage.css';
import Footer from './Footer'; // Footer 컴포넌트 import

const Mypage = () => {
    const { user, loading } = useAuth();
    const [userInfo, setUserInfo] = useState(null);
    const [error, setError] = useState('');
    const [image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
    const fileInput = useRef(null);

    const fetchUserInfo = useCallback(async () => {
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
    }, [user]);

    useEffect(() => {
        if (!loading) {
            fetchUserInfo();
        }
    }, [loading, user, fetchUserInfo]);

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
        <div className="page-wrapper">
            <div className="Mypage">
                {error && <p className="error">{error}</p>}
                {userInfo ? (
                    <div>
                        <h2>회원 정보</h2>
                        <div className="avatar-container">
                            <Avatar 
                                src={image + `?t=${new Date().getTime()}`}  // Cache-busting
                                sx={{ width: 200, height: 200 }} 
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
                        </div>
                        <div className="user-info">
                            <p><span>ID:</span> {userInfo.id}</p>
                            <p><span>Name:</span> {userInfo.name}</p>
                            <p><span>Phone:</span> {userInfo.phone}</p>
                            <p><span>Sex:</span> {userInfo.sex}</p>
                            <p><span>Height:</span> {userInfo.height}</p>
                            <p><span>Weight:</span> {userInfo.weight}</p>
                            <p><span>Birth:</span> {userInfo.birth}</p>
                            <p><span>Firstday:</span> {userInfo.firstday}</p>
                            <p><span>Restday:</span> {userInfo.restday}</p>
                        </div>
                        <Button variant="contained" color="primary" component={Link} to="/edit-profile">
                            회원정보 수정
                        </Button>
                    </div>
                ) : (
                    <p>No user info available.</p>
                )}
            </div>
            <Footer /> {/* Footer를 Mypage 본문 외부에 위치시킵니다 */}
        </div>
    );
};

export default Mypage;
