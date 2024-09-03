import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RegisterPage() {
  const [id, setId] = useState('');
  const [pwd, setPwd] = useState('');
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [sex, setSex] = useState('');
  const [error, setError] = useState('');
  const [idError, setIdError] = useState('');
  const [idSuccess, setIdSuccess] = useState(''); // ID 확인 성공 메시지 상태 추가
  const [isIdAvailable, setIsIdAvailable] = useState(true);
  const navigate = useNavigate();

  // ID 중복 확인 함수
  const checkIdAvailability = async () => {
    if (id.length > 0) {
      try {
        const response = await axios.get(`/api/check-id/${id}`);
        if (response.data) {
          setIsIdAvailable(true);
          setIdError('');
          setIdSuccess('사용할 수 있는 아이디입니다.');
        } else {
          setIsIdAvailable(false);
          setIdError('ID가 이미 사용 중입니다.');
          setIdSuccess('');
        }
      } catch (error) {
        setIdError('ID 중복 확인 중 오류가 발생했습니다.');
        setIdSuccess('');
        console.error('ID 중복 확인 중 오류 발생:', error);
      }
    } else {
      setIdError('ID는 빈 칸일 수 없습니다.');
      setIsIdAvailable(false);
      setIdSuccess('');
    }
  };

  const handleRegister = async () => {
    if (!isIdAvailable) {
      setError('회원가입 전 ID 중복 확인을 해주세요.');
      return;
    }

    try {
      // 서버에 JSON 형식으로 회원가입 요청
      const response = await axios.post('/api/register', {
        id,
        pwd,
        name,
        phone,
        sex  // 성별 추가
      });
      console.log('회원가입 성공:', response.data);
      navigate('/login');
    } catch (error) {
      setError('회원가입 실패. 다시 시도해 주세요.');
      console.error('회원가입 실패:', error);
    }
  };

  return (
    <div className="RegisterPage">
      <h2>회원가입</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {idError && <p style={{ color: 'red' }}>{idError}</p>}
      {idSuccess && <p style={{ color: 'green' }}>{idSuccess}</p>} {/* 성공 메시지 표시 */}
      <input
        type="text"
        value={id}
        onChange={(e) => setId(e.target.value)}
        placeholder="사용자 ID"
      />
      <button onClick={checkIdAvailability}>ID 중복 확인</button>
      <input
        type="password"
        value={pwd}
        onChange={(e) => setPwd(e.target.value)}
        placeholder="비밀번호"
      />
      <input
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="이름"
      />
      <input
        type="text"
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        placeholder="전화번호"
      />
      <input
        type="text"
        value={sex}
        onChange={(e) => setSex(e.target.value)}
        placeholder="성별"  // 성별 입력 필드 추가
      />
      <button onClick={handleRegister}>회원가입</button>
    </div>
  );
}

export default RegisterPage;
