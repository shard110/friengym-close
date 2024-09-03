import React, { useState } from "react";

const UpdateAsk = ({ anum }) => {
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [password, setPassword] = useState("");

  const handleUpdate = async () => {
    try {
      alert("문의글이 성공적으로 수정되었습니다.");
    } catch (error) {
      alert("비밀번호가 일치하지 않습니다.");
    }
  };

  return (
    <div>
      <h1>문의글 수정</h1>
      <input type="text" placeholder="제목" value={title} onChange={(e) => setTitle(e.target.value)} />
      <textarea placeholder="내용" value={contents} onChange={(e) => setContents(e.target.value)} />
      <input type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button onClick={handleUpdate}>수정</button>
    </div>
  );
};

export default UpdateAsk;
