import React from 'react';
import { Navigate } from 'react-router-dom';

function PublicRoute({ children }) {
  const userRole = localStorage.getItem("role");

  if (userRole) {
    return <Navigate to="/" replace />;  
  }

  return children;
}

export default PublicRoute;
