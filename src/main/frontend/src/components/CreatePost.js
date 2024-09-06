import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const CreatePostForm = () => {
    const { user } = useAuth(); // AuthContext에서 사용자 정보를 가져옵니다
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [file, setFile] = useState(null);
    const [message, setMessage] = useState('');

    // Redirect to login page if not authenticated
    if (!user) {
        navigate('/login');
        return null; // Return null while navigating
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        const postObject = {
            title,
            content,
            userId: user.userId // userId를 추가
        };
        formData.append('post', JSON.stringify(postObject));  // JSON 문자열로 추가
        if (file) {
            formData.append('file', file);
        }

        try {
            // 요청 헤더에 인증 토큰을 포함
            const response = await axios.post('http://localhost:8080/posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${user.token || localStorage.getItem('authToken')}` // 수정됨
                }
            });
            console.log(response.data);
            setMessage('Post created successfully!');
            alert('Post created successfully!');
            setTimeout(() => {
                navigate('/posts');
            }, 2000);
        } catch (error) {
            console.error('An unexpected error occurred:', error);
            setMessage('An unexpected error occurred.');
            alert('An unexpected error occurred.');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Title:</label>
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Content:</label>
                <textarea
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>File:</label>
                <input
                    type="file"
                    onChange={(e) => setFile(e.target.files[0])}
                />
            </div>
            <button type="submit">Create Post</button>
            {message && <p>{message}</p>}
        </form>
    );
};

export default CreatePostForm;
