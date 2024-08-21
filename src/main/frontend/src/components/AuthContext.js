import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

// AuthContext 생성
const AuthContext = createContext();

// AuthProvider 컴포넌트
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        // 로컬 스토리지에서 토큰을 가져와 사용자의 인증 상태를 확인
        const token = localStorage.getItem('authToken');
        if (token) {
            axios.get('/api/mypage', { headers: { Authorization: `Bearer ${token}` } })
                .then(response => setUser(response.data))
                .catch(() => {
                    setUser(null);
                    localStorage.removeItem('authToken');
                });
        }
    }, []);

    // 로그인 함수
    const login = (userData) => {
        setUser(userData);
        localStorage.setItem('authToken', userData.token); // 토큰 저장
    };

    // 로그아웃 함수
    const logout = () => {
        setUser(null);
        localStorage.removeItem('authToken');
    };

    // AuthContext.Provider를 사용하여 context를 공급
    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

// AuthContext를 사용하여 값에 접근하는 훅
export const useAuth = () => useContext(AuthContext);
