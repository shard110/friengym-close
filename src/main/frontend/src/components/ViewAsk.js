import axios from "axios";
import React, { useState } from "react";

const ViewAsk = ({ anum }) => {
  const [password, setPassword] = useState("");
  const [ask, setAsk] = useState(null);

  const handleView = async () => {
    try {
      const response = await axios.post("/api/asks/check-password", null, {
        params: { anum, password }
      });
      setAsk(response.data);
    } catch (error) {
      alert("비밀번호가 일치하지 않습니다.");
    }
  };

  return (
    <div>
      <h1>문의글 조회</h1>
      {ask ? (
        <div>
          <h3>{ask.aTitle}</h3>
          <p>{ask.aContents}</p>
        </div>
      ) : (
        <div>
          <input type="password" placeholder="비밀번호 입력" value={password} onChange={(e) => setPassword(e.target.value)} />
          <button onClick={handleView}>조회</button>
        </div>
      )}
    </div>
  );
};

export default ViewAsk;
