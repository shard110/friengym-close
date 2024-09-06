import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import { useAuth } from './AuthContext';

const EditPost = () => {
  const { poNum } = useParams();
  const [postData, setPostData] = useState({
    poContents: '',
    poTitle: '',
  });
  const [file, setFile] = useState(null);
  const [fileName, setFileName] = useState(''); // 선택한 파일 이름 상태 추가
  const [notification, setNotification] = useState('');
  const navigate = useNavigate();
  const { user } = useAuth();

  useEffect(() => {
    const fetchPostData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/posts/${poNum}`);
        const data = response.data;
        setPostData({
          poContents: data.poContents || '',
          poTitle: data.poTitle || '',
        });
      } catch (error) {
        console.error('Error fetching post data:', error);
      }
    };

    fetchPostData();
  }, [poNum]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const postDataToSend = {
      title: postData.poTitle,
      content: postData.poContents,
    };

    const formData = new FormData();
    formData.append('post', JSON.stringify(postDataToSend)); // JSON 객체를 문자열로 추가
    if (file) {
      formData.append('file', file);
    }

    try {
      await axios.put(`http://localhost:8080/posts/${poNum}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${user.token || localStorage.getItem('authToken')}`
        }
      });
      setNotification('Post updated successfully!'); // 성공 메시지 설정
      setTimeout(() => {
        navigate('/posts'); // 3초 후 postslist 페이지로 이동
      }, 3000);
    } catch (error) {
      console.error('Error updating post:', error);
      setNotification('Failed to update post.'); // 실패 메시지 설정
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Title:</label>
        <input
          type="text"
          value={postData.poTitle}
          onChange={(e) => setPostData({ ...postData, poTitle: e.target.value })}
        />
      </div>
      <div>
        <label>Content:</label>
        <textarea
          value={postData.poContents}
          onChange={(e) => setPostData({ ...postData, poContents: e.target.value })}
        />
      </div>

      <div>
        <label>Reply Count:</label>
        <input
          type="number"
          value={postData.replycnt}
          onChange={(e) => setPostData({ ...postData, replycnt: +e.target.value })}
        />
      </div>
      <div>
        <label>Upload File:</label>
        <input
          type="file"
          onChange={(e) => setFile(e.target.files[0])}
        />
      </div>
      <button type="submit">Update Post</button>
    </form>
  );
};

export default EditPost;