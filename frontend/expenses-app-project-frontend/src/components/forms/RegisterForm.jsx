import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Form, Button, Card, Alert } from 'react-bootstrap'; // Add Alert here
import { register } from '../../api/Register';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState(null);

    const handleRegister = async (e) => {
        e.preventDefault();

        const body =  {
            "userName": username,
            "email": email,
            "password": password
        };

        const result = await register(body); // Await here to handle the result correctly

        if (result.success) {
            setError(null);
            setMessage(result.data);
        } else {
            setError(result.error);
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
          <Card style={{ width: '20rem' }} className="p-4">
            <h2 className="text-center mb-4">Sign up</h2>
            <Form onSubmit={handleRegister}>
              <Form.Group className="mb-3" controlId="formBasicUsername">
                <Form.Control
                  type="text"
                  placeholder="Enter username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Control
                  type="text"
                  placeholder="Enter e-mail"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
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
              {message && <Alert variant="success" dismissible><p>{message}</p></Alert>}
              <Button variant="primary" type="submit" className="w-100">
                Sign up
              </Button>
            </Form>
            <div className="d-flex justify-content-between mt-3">
              <Link to="/login">Sign in</Link>
            </div>
          </Card>
        </div>
      );
}

export default RegisterForm;
