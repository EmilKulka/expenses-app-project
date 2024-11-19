import './App.css'
import { Routes, Route} from 'react-router-dom';
import ProtectedRoute from "./components/ProtectedRoute";
import PublicRoute from './components/PublicRoute';
import AdminPage from "./components/pages/AdminPage";
import LoginPage from "./components/pages/LoginPage/LoginPage";
import RegisterPage from './components/pages/RegisterPage'
import ErrorPage from "./components/pages/ErrorPage";
import AppUserPage from './components/pages/AppUserPage';
import ForgotPasswordPage from './components/pages/ForgotPasswordPage'

function App() {
  return (
      <Routes>
        <Route path = "/login" element=
        {<PublicRoute>
          <LoginPage/>
        </PublicRoute>
        }
        />
        <Route path = "/register" element=
        {<PublicRoute>
          <RegisterPage/>
        </PublicRoute>
        }
        />
        <Route path = "/reset-password" element=
        {<PublicRoute>
          <ForgotPasswordPage/>
        </PublicRoute>
        }
        />
        <Route path = "/admin-dashboard" element = {
          <ProtectedRoute role = "ADMIN">
            <AdminPage/>
          </ProtectedRoute>
          }
        />
        <Route path = "/" element = {
          <ProtectedRoute role = "USER">
            <AppUserPage/>
          </ProtectedRoute>
        }
        />
        <Route path="*" element={<ErrorPage/>} />
      </Routes>
  )
}

export default App
