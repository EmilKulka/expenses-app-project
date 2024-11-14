import React from 'react';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../api/Logout'; // adjust the path as needed

function AdminPage() {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate("/login");
  };

  return (
    <div>
      <h1>THIS IS ADMIN PAGE</h1>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default AdminPage;
