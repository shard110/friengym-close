import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./PostDetail.css"; // CSS 파일 import
import { useAuth } from "./AuthContext"; // 인증 컨텍스트 추가
import CommentList from "./CommentList"; // 댓글 목록 컴포넌트 import
import CommentCreate from "./CommentCreate"; // 댓글 추가 컴포넌트 import

export default function PostDetail() {
  const [post, setPost] = useState({
    id:"",
    poTitle: "",
    poContents: "",
    name: "",
    createdDate: "",
    updatedDate: "", // 수정 날짜 필드 추가
    fileUrl: "", // 파일 URL 추가
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [commentsKey, setCommentsKey] = useState(Date.now()); // 댓글 목록을 새로 고칠 때 사용하는 키
  const navigate = useNavigate();
  const { poNum } = useParams();
  const { user, loading: authLoading } = useAuth(); // 인증 정보 가져오기

  // 날짜 및 시간 포맷 함수 추가
  const formatDateTime = (dateString) => {
    const date = new Date(dateString);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit",
    })}`;
  };

  useEffect(() => {
    const loadPost = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/posts/${poNum}`);
        setPost({
          id: result.data.id, // `id` 필드를 추가
          poTitle: result.data.poTitle,
          poContents: result.data.poContents,
          name: result.data.name,
          createdDate: result.data.createdDate,
          updatedDate: result.data.updatedDate || result.data.createdDate, // 수정된 날짜가 없으면 작성 날짜로 설정, // 수정 날짜 설정
          fileUrl: result.data.fileUrl,
        });
      } catch (error) {
        setError("게시글을 가져오는 데 실패했습니다.");
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
          Authorization: `Bearer ${
            user?.token || localStorage.getItem("authToken")
          }`, // 인증 헤더 추가
        },
      });
      navigate("/posts");
    } catch (error) {
      setError("게시글 삭제에 실패했습니다.");
    }
  };

  const downloadFile = async () => {
    if (!post.fileUrl) {
      setError("No file to download.");
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/files/${post.fileUrl}`,
        {
          responseType: "blob", // 파일 데이터를 blob 형태로 받음
        }
      );

      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", post.fileUrl); // 파일 이름 설정
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("파일 다운로드에 실패했습니다.", error);
      setError("파일 다운로드에 실패했습니다.");
    }
  };

  const refreshComments = () => {
    setCommentsKey(Date.now()); // 새로운 키로 강제 새로 고침
  };

  if (authLoading || loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="container">
      <div className="post-detail-card">
        <div className="card-header">No. {poNum}</div>
        <div className="card-body">
          <h2 className="title">{post.poTitle}</h2>
          <h5>By {post.name}</h5>
          <p>{post.poContents}</p>
          <p className="date">
          {post.updatedDate !== post.createdDate ? `Updated on: ${formatDateTime(post.updatedDate)}` : `Created on: ${formatDateTime(post.createdDate)}`}
          </p>
          {post.fileUrl && (
            <div className="file-info">
              <p>첨부파일: {post.fileUrl}</p>
              <button className="button download-button" onClick={downloadFile}>
                Download File
              </button>
            </div>
          )}
          {user && user.id === post.id && (
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
      <CommentList poNum={poNum} key={commentsKey} />
      <CommentCreate poNum={poNum} refreshComments={refreshComments} />
    </div>
  );
}
