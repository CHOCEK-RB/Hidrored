import React from "react";

const IconCard: React.FC<{
  icon: React.JSX.Element;
  title: string;
  children: React.ReactNode;
}> = ({ icon, title, children }) => (
  <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300">
    <div className="flex items-center mb-4">
      <div className="bg-blue-100 p-3 rounded-full">{icon}</div>
      <h3 className="ml-4 text-xl font-semibold text-slate-800">{title}</h3>
    </div>
    <p className="text-gray-600">{children}</p>
  </div>
);

const ReportIcon = () => (
  <svg
    className="w-6 h-6 text-blue-600"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
    ></path>
  </svg>
);
const TrackIcon = () => (
  <svg
    className="w-6 h-6 text-blue-600"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
    ></path>
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
    ></path>
  </svg>
);
const ImproveIcon = () => (
  <svg
    className="w-6 h-6 text-blue-600"
    fill="none"
    stroke="currentColor"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"
    ></path>
  </svg>
);

const HomePage: React.FC = () => {
  return (
    <div className="text-slate-800">
      <div className="text-center bg-white p-10 rounded-lg shadow-lg mb-8">
        <h1 className="text-5xl font-extrabold mb-3">Bienvenido a Hidrored</h1>
        <p className="text-lg text-gray-600 max-w-3xl mx-auto">
          Tu plataforma comunitaria para reportar y dar seguimiento a
          incidencias del servicio de agua y desagüe en Arequipa. Juntos
          mejoramos nuestro entorno.
        </p>
      </div>

      <div className="mb-8">
        <h2 className="text-3xl font-bold mb-6 text-center text-white">
          Nuestro Propósito
        </h2>
        <div className="grid md:grid-cols-3 gap-8">
          <IconCard icon={<ReportIcon />} title="Centralización de Reportes">
            Proporcionar un canal único y accesible para que los usuarios puedan
            informar sobre problemas de manera eficiente.
          </IconCard>
          <IconCard icon={<TrackIcon />} title="Transparencia y Seguimiento">
            Ofrecer a los usuarios la capacidad de rastrear el estado de sus
            reportes en tiempo real y recibir actualizaciones.
          </IconCard>
          <IconCard icon={<ImproveIcon />} title="Mejora de la Gestión">
            Ayudar a las autoridades a recibir información detallada y
            localizada de los problemas para una respuesta más rápida.
          </IconCard>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
