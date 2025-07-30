import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import { type ReporteDTO } from "../types";
import ReportCard from "../components/ReportCard";

const MyReportsPage: React.FC = () => {
  const { user } = useAuth();
  const [reportes, setReportes] = useState<ReporteDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!user) {
      setLoading(false);
      return;
    }

    const fetchMyReports = async () => {
      try {
        setLoading(true);
        setError(null);

        const response = await axios.get(
          "http://localhost:8080/api/reportes/mis-reportes",
          {
            headers: {
              "X-Usuario-ID": user.id,
            },
          },
        );

        setReportes(response.data);
      } catch (err) {
        console.error("Error al obtener mis reportes:", err);
        setError(
          "No se pudieron cargar tus reportes. Inténtalo de nuevo más tarde.",
        );
      } finally {
        setLoading(false);
      }
    };

    fetchMyReports();
  }, [user]);

  if (loading) {
    return <p className="text-white text-center">Cargando tus reportes...</p>;
  }

  if (error) {
    return <p className="text-red-400 bg-red-900 p-3 rounded-md">{error}</p>;
  }

  return (
    <div className="text-white">
      <h1 className="text-4xl font-bold mb-6">Mis Reportes</h1>

      {reportes.length === 0 ? (
        <div className="bg-slate-800 p-6 rounded-lg text-center">
          <p className="text-lg">Aún no has creado ningún reporte.</p>
          <p className="text-gray-400 mt-2">
            ¡Ve al mapa interactivo para registrar tu primera incidencia!
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {reportes.map((reporte) => (
            <ReportCard key={reporte.id} reporte={reporte} />
          ))}
        </div>
      )}
    </div>
  );
};

export default MyReportsPage;
