import React from "react";
import { Link } from "react-router-dom";
import { type ReporteDTO } from "../types";

interface ReportCardProps {
  reporte: ReporteDTO;
}

const ReportCard: React.FC<ReportCardProps> = ({ reporte }) => {
  const getPrioridadColor = (prioridad: string) => {
    switch (prioridad) {
      case "URGENTE":
        return "bg-red-500";
      case "ALTA":
        return "bg-orange-500";
      case "MEDIA":
        return "bg-yellow-500";
      case "BAJA":
        return "bg-green-500";
      default:
        return "bg-gray-500";
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden transform hover:-translate-y-1 transition-transform duration-300 flex flex-col">
      <div className="p-5 flex-grow">
        <div className="flex justify-between items-start">
          <h3 className="text-xl font-bold text-slate-800 mb-2">
            {reporte.titulo}
          </h3>
          <span
            className={`px-3 py-1 text-xs font-semibold text-white rounded-full ${getPrioridadColor(reporte.prioridad)}`}
          >
            {reporte.prioridad}
          </span>
        </div>
        <p className="text-sm text-gray-500 mb-1">
          <strong>Tipo:</strong> {reporte.tipo}
        </p>
        <p className="text-sm text-gray-500 mb-3">
          <strong>Estado:</strong> {reporte.estado}
        </p>
        <p className="text-gray-700 mb-4 line-clamp-3">{reporte.descripcion}</p>
      </div>
      <div className="bg-gray-50 px-5 py-3 mt-auto">
        <Link
          to={`/reporte/${reporte.id}`}
          className="text-blue-600 hover:text-blue-800 font-semibold text-sm"
        >
          Ver Detalles y Comentarios →
        </Link>
      </div>
    </div>
  );
};

export default ReportCard;
