import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const CreatePostForm = () => {
    const { user } = useAuth(); // AuthContext에서 사용자 정보를 가져옵니다
    const navigate = useNavigate();
    const [poTitle, setpoTitle] = useState('');
    const [poContents, setpoContents] = useState('');
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
        formData.append('poTitle', poTitle);
        formData.append('poContents', poContents);
        formData.append('userId', user.userId);
        if (file) {
            formData.append('file', file);
        }

        try {
            const response = await axios.post('http://localhost:8080/posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${user.token || localStorage.getItem('authToken')}`
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
                    value={poTitle}
                    onChange={(e) => setpoTitle(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Content:</label>
                <textarea
                    value={poContents}
                    onChange={(e) => setpoContents(e.target.value)}
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
