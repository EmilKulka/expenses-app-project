import React from 'react';
import { Navigate } from 'react-router-dom';
import ErrorPage from './pages/ErrorPage';

function ProtectedRoute({children, role}) {
    const userRole = localStorage.getItem("role");
    

    if (!userRole) {
        return <Navigate to="/login" replace />;
      }
    
    if(userRole !== role) {
        return <ErrorPage/>
    }

    return children;
}
export default ProtectedRoute;