import React, { useState, useEffect } from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';

function App() {
    const [message, setMessage] = useState("");

    useEffect(() => {
        axios.get('/api/hello') // 상대 경로 사용
            .then(response => {
                setMessage(response.data);
            })
            .catch(error => {
                if (error.response) {
                    // 서버가 상태 코드로 응답한 경우
                    console.error('Error response:', error.response);
                } else if (error.request) {
                    // 요청이 서버로 보내졌으나 응답이 없는 경우
                    console.error('Error request:', error.request);
                } else {
                    // 설정 문제로 인한 오류
                    console.error('Error message:', error.message);
                }
            });
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <h1 className="App-title">{message}</h1>
            </header>
            <p className="App-intro">
                To get started, edit <code>src/App.js</code> and save to reload.
            </p>
        </div>
    );
}

export default App;