import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from './AuthContext';

function LoginPage() {
  const [id, setId] = useState('');
  const [pwd, setPwd] = useState('');
  const [error, setError] = useState(''); // 에러 메시지를 저장할 상태 추가
  const navigate = useNavigate();
  const { setUser } = useAuth();

  const handleLogin = async () => {
    try {
      // 서버에 JSON 형식으로 로그인 요청
      const response = await axios.post('/api/login', {
        id,
        pwd
      });
      const user = response.data;
      setUser(user);
      navigate('/');
    } catch (error) {
      // 에러 발생 시 메시지 설정
      setError('Login failed. Please check your credentials and try again.');
      console.error('Login failed', error);
    }
  };

  return (
    <div className="LoginPage">
      <h2>Login</h2>
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
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default LoginPage;
