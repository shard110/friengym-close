import React, { useState } from 'react';
import axios from 'axios';

const Login = () => {
    const [id, setId] = useState('');
    const [pwd, setPwd] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', {
                id,
                pwd
            });
            alert(response.data);
        } catch (error) {
            alert(error.response.data);
        }
    };

    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <div>
                    <label htmlFor="id">ID:</label>
                    <input
                        type="text"
                        id="id"
                        value={id}
                        onChange={(e) => setId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="pwd">Password:</label>
                    <input
                        type="password"
                        id="pwd"
                        value={pwd}
                        onChange={(e) => setPwd(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
