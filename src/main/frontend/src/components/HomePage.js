import React from 'react';
import { Link, useNavigate  } from 'react-router-dom';
import { useAuth } from './AuthContext';

function HomePage() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="HomePage">
      {user ? (
        <div>
          <h1>Welcome, {user.name}</h1>
          <button onClick={handleLogout}>Logout</button>
          <Link to="/mypage">
            <button>My Page</button>
          </Link>
        </div>
      ) : (
        <div>
          <h1>Welcome to the App</h1>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
        </div>
      )}
    </div>
  );
}

export default HomePage;
