import axios from "axios";
import React, { useEffect, useState } from "react";
import CreateAsk from '../components/CreateAsk';
import DeleteAsk from '../components/DeleteAsk';
import UpdateAsk from '../components/UpdateAsk';
import ViewAsk from '../components/ViewAsk';
import Gnb from "../components/customerGnb";

const AskPage = () => {
  const [asks, setAsks] = useState([]);
  const [page, setPage] = useState(1); // 현재 페이지 상태
  const [totalPages, setTotalPages] = useState(0); // 전체 페이지 수
  const [pageSize] = useState(10); // 페이지당 항목 수
  const [selectedAsk, setSelectedAsk] = useState(null); // 선택한 문의글
  const [mode, setMode] = useState("view"); // "create", "view", "update", "delete" 모드 관리

  useEffect(() => {
    fetchAsks(page);
  }, [page]);

  const fetchAsks = async (page) => {
    try {
      const response = await axios.get(
        `/api/asks?page=${page - 1}&size=${pageSize}`
      );
      setAsks(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Error fetching asks:", error);
    }
  };

  const renderPageButtons = () => {
    const buttons = [];
    const startPage = Math.floor((page - 1) / 10) * 10 + 1;
    const endPage = Math.min(startPage + 9, totalPages);

    for (let i = startPage; i <= endPage; i++) {
      buttons.push(
        <button
          key={i}
          onClick={() => setPage(i)}
          style={{ margin: "0 5px", backgroundColor: page === i ? "#007bff" : "#ffffff" }}
        >
          {i}
        </button>
      );
    }
    return buttons;
  };

  const handleFirstPage = () => {
    setPage(1);
  };

  const handleLastPage = () => {
    setPage(totalPages);
  };

  return (
    <div>
        <Gnb />
      <h1>문의글 목록</h1>

      {/* 문의글 작성, 수정, 삭제, 조회 컴포넌트 조건부 렌더링 */}
      {mode === "create" && <CreateAsk onAskCreated={() => fetchAsks(page)} />}
      {mode === "update" && selectedAsk && (
        <UpdateAsk anum={selectedAsk.anum} onAskUpdated={() => fetchAsks(page)} />
      )}
      {mode === "delete" && selectedAsk && (
        <DeleteAsk anum={selectedAsk.anum} onAskDeleted={() => fetchAsks(page)} />
      )}
      {mode === "view" && selectedAsk && <ViewAsk anum={selectedAsk.anum} />}

      <ul>
        {asks && asks.length > 0 ? (
          asks.map((ask) => (
            <li key={ask.anum}>
              <button onClick={() => setSelectedAsk(ask)}>
                <h3>{ask.aTitle}</h3>
                <p>{ask.aContents}</p>
                <p>{new Date(ask.aDate).toLocaleDateString()}</p>
              </button>
            </li>
          ))
        ) : (
          <p>문의글이 없습니다.</p>
        )}
      </ul>

      {/* 페이지 네비게이션 */}
      <div>
        <button onClick={handleFirstPage} disabled={page === 1}>
          {"<<"} 첫 페이지
        </button>
        <button onClick={() => setPage(Math.max(page - 1, 1))} disabled={page === 1}>
          이전
        </button>
        {renderPageButtons()}
        <button onClick={() => setPage(Math.min(page + 1, totalPages))} disabled={page === totalPages}>
          다음
        </button>
        <button onClick={handleLastPage} disabled={page === totalPages}>
          마지막 페이지 {">>"}
        </button>
      </div>

      <button onClick={() => setMode("create")}>문의글 작성</button>
    </div>
  );
};

export default AskPage;
