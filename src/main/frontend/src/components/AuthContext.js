import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem('authToken');
            if (token) {
                try {
                    const response = await axios.get('/api/mypage', {
                        headers: { 
                            Authorization: `Bearer ${token}`
                        }
                    });
                    console.log('User fetched in AuthProvider:', response.data);
                    setUser(response.data);
                } catch (error) {
                    console.error('Failed to fetch user info in AuthProvider:', error);
                    setUser(null);
                    localStorage.removeItem('authToken');
                }
            }
            setLoading(false);
        };

        fetchUser();
    }, []);

    const login = (userData) => {
        setUser(userData);
        localStorage.setItem('authToken', userData.token);
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem('authToken');
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
