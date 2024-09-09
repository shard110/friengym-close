import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const EditPost = () => {
  const { poNum } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState({
    poTitle: '',
    poContents: '',
    file: null
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/posts/${poNum}`);
        setPost({
          poTitle: response.data.potitle,
          poContents: response.data.pocontents,
          file: null
        });
      } catch (err) {
        setError('Failed to fetch post details');
      } finally {
        setLoading(false);
      }
    };

    fetchPost();
  }, [poNum]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPost((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    setPost((prev) => ({ ...prev, file: e.target.files[0] }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('poTitle', post.poTitle);
    formData.append('poContents', post.poContents);
    if (post.file) {
      formData.append('file', post.file);
    }

    try {
      await axios.put(`http://localhost:8080/posts/${poNum}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        }
      });
      setSuccess('Post updated successfully!');
      setTimeout(() => {
        navigate('/posts'); // /posts 페이지로 리다이렉트
      }, 2000); // 2초 후 리다이렉트
    } catch (err) {
      setError('Failed to update post');
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Update Post</h1>
      {success && <div className="success-message">{success}</div>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            name="poTitle"
            value={post.poTitle}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Contents:</label>
          <textarea
            name="poContents"
            value={post.poContents}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>File:</label>
          <input
            type="file"
            onChange={handleFileChange}
          />
        </div>
        <button type="submit">Update Post</button>
      </form>
    </div>
  );
};

export default EditPost;
