// src/components/PostList.js

import axios from 'axios';
import React, { useEffect, useState } from 'react';

const PostList = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1); // 현재 페이지
  const [size] = useState(10); // 페이지당 게시글 수
  const [totalPages, setTotalPages] = useState(0); // 총 페이지 수

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/list/posts?page=${page}&size=${size}`);
        console.log(response.data.content); // API 응답 확인
        setPosts(response.data.content); // 게시글 데이터 설정
        setTotalPages(response.data.totalPages); // 총 페이지 수 설정
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, [page, size]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  // 총 페이지 그룹 수 계산

  // 페이지 버튼 생성
  const renderPageButtons = () => {
    const buttons = [];
    const startPage = Math.floor((page - 1) / 10) * 10 + 1; // 현재 그룹의 시작 페이지
    const endPage = Math.min(startPage + 9, totalPages); // 현재 그룹의 끝 페이지

    for (let i = startPage; i <= endPage; i++) {
      buttons.push(
        <button
          key={i}
          onClick={() => setPage(i)} // 페이지 클릭 시 해당 페이지로 이동
          style={{ margin: '0 5px', backgroundColor: page === i ? '#007bff' : '#ffffff' }} // 현재 페이지 버튼 강조
        >
          {i}
        </button>
      );
    }
    return buttons;
  };

  // 날짜 포맷 함수
  const formatDate = (dateString) => {
    if (!dateString) return '날짜 없음'; // 날짜가 없으면 표시
    const date = new Date(dateString); // ISO 형식의 날짜 문자열을 Date 객체로 변환
    if (isNaN(date.getTime())) return 'Invalid Date'; // 만약 변환이 실패하면 'Invalid Date' 반환

    // 한국 시간대에 맞게 포맷
    return date.toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false, // 24시간 형식
    }).replace(',', ''); // 쉼표 제거
  };

  // 첫 페이지로 이동
  const handleFirstPage = () => {
    setPage(1);
  };

  // 마지막 페이지로 이동
  const handleLastPage = () => {
    setPage(totalPages);
  };

  // 이전 그룹으로 이동

  // 다음 그룹으로 이동

  return (
    <div>
      <h1>게시글 목록</h1>
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>번호</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>제목</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>작성자</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>작성일</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>조회수</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>댓글수</th>
            <th style={{ border: '1px solid #ddd', padding: '8px' }}>내용</th>{/* 내용 열 추가 */}
          </tr>
        </thead>
        <tbody>
          {posts.map(post => (
            <tr key={post.ponum}> {/* 게시글 번호를 key로 사용 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>#{post.ponum}</td> {/* 게시글 번호 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{post.potitle}</td>
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{post.id}</td> {/* 작성자 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{formatDate(post.podate)}</td> {/* 작성일 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{post.viewCnt}</td> {/* 조회수 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{post.replyCnt}</td> {/* 댓글수 */}
              <td style={{ border: '1px solid #ddd', padding: '8px' }}>{post.pocontents || '내용 없음'}</td> {/* 내용이 null인 경우 처리 */}
            </tr>
          ))}
        </tbody>
      </table>
      
      {/* 페이지 네비게이션 */}
      <div>
        <button onClick={handleFirstPage} disabled={page === 1}>{"<<"} 첫 페이지</button>
        <button onClick={() => setPage(Math.max(page - 1, 1))} disabled={page === 1}>이전</button>
        {renderPageButtons()} {/* 현재 페이지 그룹의 페이지 버튼 렌더링 */}
        <button onClick={() => setPage(Math.min(page + 1, totalPages))} disabled={page === totalPages}>다음</button>
        <button onClick={handleLastPage} disabled={page === totalPages}>마지막 페이지 {">>"}</button>
      </div>
    </div>
  );
};

export default PostList;