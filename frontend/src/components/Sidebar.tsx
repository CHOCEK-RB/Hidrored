import React from "react";
import { NavLink, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const WaterDropIcon = () => (
  <svg
    className="w-8 h-8 text-blue-400"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M12 2.69l5.66 5.66a8 8 0 11-11.32 0L12 2.69z"
    ></path>
  </svg>
);

const HomeIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
    ></path>
  </svg>
);

const MapIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13v-6m0 6h6m-6 0l6-3m-6 3l-6-3m6 3l6 3m-6-3l-6-3m6 3v-6m0 0l6-3m-6 3L3 7m6 6h6m6 0l-5.447 2.724A1 1 0 0115 16.382V5.618a1 1 0 011.447.894L21 7m-6 6v6m0-6h-6"
    ></path>
  </svg>
);

const ReportIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
    ></path>
  </svg>
);

const ListIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M4 6h16M4 10h16M4 14h16M4 18h16"
    ></path>
  </svg>
);

const ProfileIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
    ></path>
  </svg>
);

const LogoutIcon = () => (
  <svg
    className="w-6 h-6 mr-3"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
    ></path>
  </svg>
);

const Sidebar: React.FC = () => {
  const { user, logout } = useAuth();

  const activeLinkClass =
    "flex items-center p-4 text-white bg-blue-500 rounded-lg shadow-md";
  const inactiveLinkClass =
    "flex items-center p-4 text-gray-300 hover:bg-blue-900 hover:text-white rounded-lg transition-colors duration-200";

  return (
    <div className="w-64 h-screen bg-blue-950 text-white flex flex-col shadow-lg sticky top-0">
      <div className="flex items-center justify-center p-6 border-b border-blue-900">
        <WaterDropIcon />
        <span className="ml-3 text-2xl font-bold">Hidrored</span>
      </div>

      <nav className="flex-1 px-4 py-6">
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? activeLinkClass : inactiveLinkClass
          }
        >
          <HomeIcon />
          <span>Inicio</span>
        </NavLink>
        <NavLink
          to="/mapa"
          className={({ isActive }) =>
            (isActive ? activeLinkClass : inactiveLinkClass) + " mt-2"
          }
        >
          <MapIcon />
          <span>Mapa de Incidencias</span>
        </NavLink>
        <NavLink
          to="/reportes"
          className={({ isActive }) =>
            (isActive ? activeLinkClass : inactiveLinkClass) + " mt-2"
          }
        >
          <ReportIcon />
          <span>Mis Reportes</span>
        </NavLink>
        <NavLink
          to="/todos-los-reportes"
          className={({ isActive }) =>
            isActive ? activeLinkClass : inactiveLinkClass
          }
        >
          <ListIcon />
          <span>Todos los Reportes</span>
        </NavLink>
        <NavLink
          to="/perfil"
          className={({ isActive }) =>
            (isActive ? activeLinkClass : inactiveLinkClass) + " mt-2"
          }
        >
          <ProfileIcon />
          <span>Mi Perfil</span>
        </NavLink>
      </nav>

      <div className="p-4 border-t border-blue-900">
        {user ? (
          <div>
            <p className="text-center text-sm text-gray-400 mb-2">
              Bienvenido,
            </p>
            <p className="text-center font-bold text-white mb-4">
              {user.nombre}
            </p>
            <button
              onClick={logout}
              className="w-full flex items-center justify-center p-3 text-gray-300 bg-red-800 hover:bg-red-700 rounded-lg transition-colors"
            >
              <LogoutIcon />
              <span>Cerrar Sesión</span>
            </button>
          </div>
        ) : (
          <Link
            to="/auth"
            className="w-full block text-center p-3 bg-green-600 hover:bg-green-500 rounded-lg transition-colors"
          >
            Acceder
          </Link>
        )}
      </div>
    </div>
  );
};
export default Sidebar;
