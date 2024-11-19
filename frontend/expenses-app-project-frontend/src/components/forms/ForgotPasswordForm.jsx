import React, {useState} from 'react'
import { Form, Button, Card, Alert } from 'react-bootstrap'; 
import { Link } from 'react-router-dom';
import { resetAppUserPassword } from '../../api/ResetPassword';

const ResetPasswordForm = () => {
    const [email, setEmail] = useState('');
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState(null);

    const handleAppUserResetPassword = async (e) => {
        e.preventDefault();

        const requestBody =  {
            "email": email,
            "oldPassword": oldPassword,
            "newPassword": newPassword
        };

        const result = await resetAppUserPassword(requestBody); 

        if (result.success) {
            setMessage(result.data);
        } else {
            setError(result.error);
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
          <Card style={{ width: '20rem' }} className="p-4">
            <h2 className="text-center mb-4">Reset password</h2>
            <Form onSubmit={handleAppUserResetPassword}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Control
                  type="text"
                  placeholder="Enter e-mail"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicOldPassword">
                <Form.Control
                  type="password"
                  placeholder="Enter old password"
                  value={oldPassword}
                  onChange={(e) => setOldPassword(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicNewPassword">
                <Form.Control
                  type="password"
                  placeholder="Enter new password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                />
              </Form.Group>
              {error && <p style={{ color: 'red' }}>{error}</p>}
              {message && <Alert variant="success" onClose={() => window.location.reload()} dismissible><p>{message}</p></Alert>}
              <Button variant="primary" type="submit" className="w-100">
                Submit
              </Button>
            </Form>
            <div className="d-flex justify-content-between mt-3">
              <Link to="/login">Sign in</Link>
            </div>
          </Card>
        </div>
      );
}

export default ResetPasswordForm;