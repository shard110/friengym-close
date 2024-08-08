import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Dashboard() {
    const [data, setData] = useState('');
    
    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get('/api/dashboard', {
            headers: { Authorization: `Bearer ${token}` }
        })
        .then(response => setData(response.data))
        .catch(error => console.error('Error fetching dashboard data:', error));
    }, []);

    return (
        <div>
            <h2>Dashboard</h2>
            <p>{data}</p>
        </div>
    );
}

export default Dashboard;
