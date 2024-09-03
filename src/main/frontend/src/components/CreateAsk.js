import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // 페이지 이동을 위한 useNavigate 사용

const CreateAsk = ({ onAskCreated }) => {
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [password, setPassword] = useState("");
  const [fileUrl, setFileUrl] = useState(""); // 파일 URL 상태 추가
  const navigate = useNavigate(); // useNavigate 훅으로 페이지 이동

  const handleSubmit = async () => {
    const formData = new FormData();
    formData.append("aTitle", title);
    formData.append("aContents", contents);
    formData.append("password", password);
    formData.append("fileUrl", fileUrl); // 서버에서 예상하는 필드 이름으로 사용
  
    try {
      await axios.post("/api/asks", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      alert("문의글이 성공적으로 작성되었습니다.");
      onAskCreated(); // 문의글 작성 후 목록 갱신
      navigate('/asks'); // 문의글 작성 후 AskPage로 이동
    } catch (error) {
      console.error("문의글 작성 중 오류가 발생했습니다.", error.response?.data || error.message);
    }
  };

  return (
    <div>
      <h1>문의글 작성</h1>
      <input
        type="text"
        placeholder="제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <textarea
        placeholder="내용"
        value={contents}
        onChange={(e) => setContents(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <input
        type="text"
        placeholder="파일 URL"
        value={fileUrl}
        onChange={(e) => setFileUrl(e.target.value)} // 파일 URL 입력
      />
      <button onClick={handleSubmit}>작성</button>
    </div>
  );
};

export default CreateAsk;
