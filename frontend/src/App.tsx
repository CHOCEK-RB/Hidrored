import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import MapPage from "./pages/MapPage";
import AuthPage from "./pages/AuthPage";
import AllReportsPage from "./pages/AllReportsPage";
import MyReportsPage from "./pages/MyReportsPage";
import CreateRegisterPage from "./pages/CreateRegisterPage";
import { AuthProvider } from "./context/AuthContext";
import { ProtectedRoute } from "./components/ProtectedRoute";

const ProfilePage = () => <h2>Mi Perfil</h2>;

function App() {
  return (
    <AuthProvider>
      {" "}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<HomePage />} />
            <Route path="mapa" element={<MapPage />} />
            <Route path="auth" element={<AuthPage />} />{" "}
            <Route
              path="crear-reporte"
              element={
                <ProtectedRoute>
                  <CreateRegisterPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="reportes"
              element={
                <ProtectedRoute>
                  <MyReportsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="todos-los-reportes"
              element={
                <ProtectedRoute>
                  <AllReportsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="perfil"
              element={
                <ProtectedRoute>
                  <ProfilePage />
                </ProtectedRoute>
              }
            />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
