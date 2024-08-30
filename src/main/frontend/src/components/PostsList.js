import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";  // Link 컴포넌트 import

export default function PostsList() {
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(1); // 현재 페이지 상태
  const [size] = useState(10); // 페이지당 항목 수
  const [totalPages, setTotalPages] = useState(0); // 전체 페이지 수

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch(`http://localhost:8080/posts?page=${page}&size=${size}`);
        if (!response.ok) {
          throw new Error("Failed to fetch posts");
        }
        const data = await response.json();
        
        setPosts(data.posts); // 게시글 목록 설정
        setTotalPages(Math.ceil(data.pageInfo.total / size)); // 전체 페이지 수 계산
      } catch (error) {
        console.error(error);
      }
    };

    fetchPosts();
  }, [page, size]); // 페이지나 페이지 크기 변경 시 데이터 새로 가져오기

  // 페이지 변경 핸들러
  const handlePageChange = (newPage) => {
    if (newPage > 0 && newPage <= totalPages) {
      setPage(newPage);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Posts List</h2>
      <ul className="list-group">
        {posts.map((post) => (
          <li key={post.poNum} className="list-group-item">
            <h5>{post.poTitle}</h5>
            <p>{post.poContents}</p>
            <small>By {post.username}</small>
            <Link to={`/post/${post.poNum}`} className="btn btn-outline-info mt-2">
              View Details
            </Link>
          </li>
        ))}
      </ul>
      <div className="mt-4">
        <nav>
          <ul className="pagination">
            <li className="page-item">
              <button 
                className="page-link" 
                onClick={() => handlePageChange(page - 1)}
                disabled={page === 1}
              >
                Previous
              </button>
            </li>
            {[...Array(totalPages)].map((_, index) => (
              <li key={index} className={`page-item ${page === index + 1 ? 'active' : ''}`}>
                <button 
                  className="page-link" 
                  onClick={() => handlePageChange(index + 1)}
                >
                  {index + 1}
                </button>
              </li>
            ))}
            <li className="page-item">
              <button 
                className="page-link" 
                onClick={() => handlePageChange(page + 1)}
                disabled={page === totalPages}
              >
                Next
              </button>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
}