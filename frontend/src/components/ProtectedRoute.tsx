import React from "react";
import { useAuth } from "../context/AuthContext";
import { Link } from "react-router-dom";

const LockIcon = () => (
  <svg
    className="w-16 h-16 mx-auto text-red-500"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
    ></path>
  </svg>
);

const NotLoggedIn: React.FC = () => {
  return (
    <div className="bg-white p-10 rounded-lg shadow-lg text-center max-w-md mx-auto">
      <LockIcon />
      <h2 className="text-3xl font-bold text-slate-800 mt-4 mb-2">
        Acceso Restringido
      </h2>
      <p className="text-gray-600 mb-6">
        Para acceder a esta sección, necesitas iniciar sesión o crear una
        cuenta.
      </p>
      <div className="flex justify-center gap-4">
        <Link
          to="/auth"
          className="bg-blue-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-blue-700 transition-colors"
        >
          Iniciar Sesión
        </Link>
        <Link
          to="/auth"
          className="bg-gray-200 text-gray-800 font-bold py-2 px-6 rounded-lg hover:bg-gray-300 transition-colors"
        >
          Registrarse
        </Link>
      </div>
    </div>
  );
};

export const ProtectedRoute: React.FC<{ children: React.JSX.Element }> = ({
  children,
}) => {
  const { user } = useAuth();

  if (!user) {
    return <NotLoggedIn />;
  }

  return children;
};
