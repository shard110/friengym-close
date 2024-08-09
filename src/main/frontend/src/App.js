import React from 'react';
import Login from './components/Login';
import Register from './components/Register';

function App() {
    return (
        <div>
            <h1>Welcome to My App</h1>
            <div>
                <Login />
                <Register />
            </div>
        </div>
    );
}

export default App;
