import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RegisterPage() {
  const [id, setId] = useState('');
  const [pwd, setPwd] = useState('');
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [sex, setSex] = useState('');  // 성별 입력 필드 추가
  const [error, setError] = useState(''); // 에러 메시지를 저장할 상태 추가
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      // 서버에 JSON 형식으로 회원가입 요청
      const response = await axios.post('/api/register', {
        id,
        pwd,
        name,
        phone,
        sex  // 성별 추가
      });
      console.log('Registration successful', response.data);
      navigate('/login'); // 회원가입 후 로그인 페이지로 이동
    } catch (error) {
      // 에러 발생 시 메시지 설정
      setError('Registration failed. Please try again.');
      console.error('Registration failed', error);
    }
  };

  return (
    <div className="RegisterPage">
      <h2>Register</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>} {/* 에러 메시지 표시 */}
      <input
        type="text"
        value={id}
        onChange={(e) => setId(e.target.value)}
        placeholder="User ID"
      />
      <input
        type="password"
        value={pwd}
        onChange={(e) => setPwd(e.target.value)}
        placeholder="Password"
      />
      <input
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="Name"
      />
      <input
        type="text"
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        placeholder="Phone"
      />
      <input
        type="text"
        value={sex}
        onChange={(e) => setSex(e.target.value)}
        placeholder="Sex"  // 성별 입력 필드 추가
      />
      <button onClick={handleRegister}>Register</button>
    </div>
  );
}

export default RegisterPage;
