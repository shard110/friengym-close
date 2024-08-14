import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './AuthContext';

function HomePage() {
  const { user, setUser } = useAuth();

  const handleLogout = () => {
    setUser(null);
    window.location.href = '/';
  };

  return (
    <div className="HomePage">
      {user ? (
        <div>
          <h1>Welcome, {user.name}</h1>
          <button onClick={handleLogout}>Logout</button>
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
