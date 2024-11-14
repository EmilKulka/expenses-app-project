import React, { useState } from 'react';
import {useNavigate, Link} from 'react-router-dom'
import { Form, Button, Card } from 'react-bootstrap';
import { login } from '../../api/Login';


const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    const result = await login(params);
  
    if(result.success) {
      setError(null);
      const userRole = localStorage.getItem("role");
      navigate(userRole === "ADMIN" ? "/admin-dashboard" : "/app-user");
    } else {
      setError(result.error)
    }



    
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: '20rem' }} className="p-4">
        <h2 className="text-center mb-4">Sign in</h2>
        <Form onSubmit={handleLogin}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Control
              type="email"
              placeholder="Enter email"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </Form.Group>
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <Button variant="primary" type="submit" className="w-100">
            Login
          </Button>
        </Form>
        <div className="d-flex justify-content-between mt-3">
          <Link to="/register">Register</Link>
          <Link to="/reset-password">Forgot Password?</Link>
        </div>
      </Card>
    </div>
  );
};

export default LoginForm;
