import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";


const UpdateAsk = () => {
  const { anum } = useParams();
  const [ask, setAsk] = useState({
    atitle: "",
    acontents: "",
    afile: ""
  });
  const navigate = useNavigate();

  // 기존 데이터를 불러오기

  useEffect(() => {
    const fetchAsk = async () => {
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await axios.get(`/api/asks/${anum}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setAsk(response.data);
      } catch (error) {
        console.error("문의글을 불러오는 중 오류 발생:", error);
      }
    };
    fetchAsk();
  }, [anum]);

  const handleUpdate = async () => {
    try {
      const token = localStorage.getItem('jwtToken');
      await axios.put(`/api/asks/${anum}`, ask, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert("문의글이 성공적으로 수정되었습니다.");
      navigate(`/asks`);  // 수정 후 목록 페이지로 이동
    } catch (error) {
      alert("수정 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setAsk({ ...ask, [e.target.name]: e.target.value });
  };

  return (
    <div>
    <h1>문의글 수정</h1>
    <input
      type="text"
      name="aTitle"
      value={ask.atitle}
      onChange={handleChange}
      placeholder="제목"
    />
    <textarea
      name="aContents"
      value={ask.acontents}
      onChange={handleChange}
      placeholder="내용"
    />
    <input
      type="text"
      name="afile"
      value={ask.afile}
      onChange={handleChange}
      placeholder="파일 URL"
    />
    <button onClick={handleUpdate}>수정 완료</button>
  </div>
  );
};

export default UpdateAsk;
