import React, { createContext, useState, useContext, useEffect } from "react";
import axios from 'axios';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true); 

    const verifySession = async () => {
        try {
            const response = await axios.get(
                'http://localhost:8080/app-user/verify', 
                { withCredentials: true }
            );

            if (response.data) {
                setUser({ 
                    role: response.data.role,
                    username: response.data.username 
                });
            }
        } catch (error) {
            setUser(null);
        } finally {
            setLoading(false); 
        }
    };

    useEffect(() => {
        verifySession();
    }, []);

    const login = async (params) => {
        try {
            const response = await axios.post(
                'http://localhost:8080/login',
                params,
                {
                    withCredentials: true,
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' } 
                }
            );

            const userRole = response.data.role;
            const username = response.data.username;

            setUser({
                role: userRole,
                username: username
            });

            return { success: true, data: response.data };

        } catch (error) {
            if (error.response) {
                if (error.response.status === 401) {
                    return { success: false, error: error.response.data.error };
                } else {
                    return { success: false, error: 'An unexpected server error occurred.' };
                }
            } else if (error.request) {
                return { success: false, error: 'No response from server. Check your network connection.' };
            } else {
                return { success: false, error: 'An unexpected error occurred.' };
            }
        }
    };

    const logout = async () => {
        await axios.post('http://localhost:8080/logout');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
