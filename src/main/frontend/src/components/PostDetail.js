import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './PostDetail.css'; // CSS 파일 import

export default function PostDetail() {
  const [post, setPost] = useState({
    poTitle: "",
    poContents: "",
    username: "",
    fileUrl: "", // 파일 URL 추가
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { poNum } = useParams();

  useEffect(() => {
    const loadPost = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/posts/${poNum}`);
        setPost(result.data);
      } catch (error) {
        setError("Failed to fetch post details.");
      } finally {
        setLoading(false);
      }
    };

    loadPost();
  }, [poNum]);

  const deletePost = async () => {
    try {
      await axios.delete(`http://localhost:8080/posts/${poNum}`);
      navigate("/");
    } catch (error) {
      setError("Failed to delete post.");
    }
  };

  const downloadFile = async () => {
    try {
      // 파일 다운로드를 위해 URL과 파일 이름 설정
      const response = await axios.get(`http://localhost:8080/files/${post.fileUrl}`, {
        responseType: 'blob', // 파일 데이터를 blob 형태로 받음
      });

      // 다운로드 링크 생성
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', post.fileUrl); // 파일 이름 설정
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      setError("Failed to download file.");
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="container">
      <div className="post-detail-card">
        <div className="card-header">Details of Post ID: {poNum}</div>
        <div className="card-body">
          <h2 className="title">{post.poTitle}</h2>
          <h5>By {post.username}</h5>
          <p>{post.poContents}</p>
          {post.fileUrl && (
            <button className="button download-button" onClick={downloadFile}>
              Download File
            </button>
          )}
          <div className="button-group">
            <Link to={`/edit/${poNum}`} className="button edit-button">
              Edit
            </Link>
            <button className="button delete-button" onClick={deletePost}>
              Delete
            </button>
          </div>
          <Link className="button back-button" to="/">
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}
