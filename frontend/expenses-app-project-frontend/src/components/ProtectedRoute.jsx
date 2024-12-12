import React from 'react';
import { Navigate } from 'react-router-dom';
import ErrorPage from './pages/ErrorPage';
import { useAuth } from '../AuthContext';

function ProtectedRoute({ children, role }) {
    const { user, loading } = useAuth();

    if (loading) {
        return <div>Loading...</div>; 
    }

    if (!user) {
        return <Navigate to="/login" replace />;
    }

    if (user.role !== role) {
        return <ErrorPage />;
    }

    return children;
}

export default ProtectedRoute;
