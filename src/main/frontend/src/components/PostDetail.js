import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './PostDetail.css'; // CSS 파일 import
import { useAuth } from './AuthContext'; // 인증 컨텍스트 추가

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
  const { user } = useAuth(); // 인증 정보 가져오기

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
      await axios.delete(`http://localhost:8080/posts/${poNum}`, {
        headers: {
          'Authorization': `Bearer ${user.token || localStorage.getItem('authToken')}` // 인증 헤더 추가
        }
      });

      navigate("/posts");
    } catch (error) {
      setError("Failed to delete post.");
    }
  };

  const downloadFile = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/files/${post.fileUrl}`, {
        responseType: 'blob', // 파일 데이터를 blob 형태로 받음
      });

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
            <div className="file-info">
              <p>첨부파일 :  {post.fileUrl}</p> {/* 파일 이름 표시 */}
              <button className="button download-button" onClick={downloadFile}>
                Download File
              </button>
            </div>
          )}
          {user && user.username === post.username && ( // 로그인한 사용자와 작성자가 일치하는 경우만 삭제 버튼 표시
          <div className="button-group">
            <Link to={`/edit/${poNum}`} className="button edit-button">
              Edit
            </Link>
            <button className="button delete-button" onClick={deletePost}>
              Delete
            </button>        
          </div>
          )}
          <Link className="button back-button" to="/posts">
            Back to Posts
          </Link>
        </div>
      </div>
    </div>
  );
}
