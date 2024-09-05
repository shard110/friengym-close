import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CreatePostForm = () => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [file, setFile] = useState(null);
    const [userId, setUserId] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate(); 

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('post', JSON.stringify({ poTitle: title, poContents: content })); 
        formData.append('userId', userId);
        if (file) {
            formData.append('file', file);
        }

        try {
            const response = await axios.post('http://localhost:8080/posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            console.log(response.data);
            setMessage('Post created successfully!'); // 성공 메시지
            alert('Post created successfully!'); // 성공 시 알림창
            setTimeout(() => {
                navigate('/posts'); // 2초 후 이동
            }, 2000);
        } catch (error) {
            console.error('An unexpected error occurred:', error);
            setMessage('An unexpected error occurred.');
            alert('An unexpected error occurred.'); // 실패 시 알림창
        }
    }; // 수정된 부분: 닫는 괄호 추가

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>ID (임시 DB에 저장된 아이디 입력)</label>
                <input
                    type="text"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                    required
                />
            </div>
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
            {message && <p>{message}</p>} {/* 메시지 상태를 UI에 렌더링 */}
        </form>
    );
};

export default CreatePostForm;
