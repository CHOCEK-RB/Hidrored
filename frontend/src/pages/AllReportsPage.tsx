import React, { useState, useEffect } from "react";
import axios from "axios";
import { type ReporteDTO } from "../types";
import ReportCard from "../components/ReportCard";

const GEODATA = {
  Arequipa: [
    "Arequipa (Cercado)",
    "Alto Selva Alegre",
    "Cayma",
    "Cerro Colorado",
    "Characato",
    "Chiguata",
    "Jacobo Hunter",
    "José Luis Bustamante y Rivero",
    "La Joya",
    "Mariano Melgar",
    "Miraflores",
    "Mollebaya",
    "Paucarpata",
    "Pocsi",
    "Polobaya",
    "Quequeña",
    "Sabandía",
    "Sachaca",
    "San Juan de Siguas",
    "San Juan de Tarucani",
    "Santa Isabel de Siguas",
    "Santa Rita de Siguas",
    "Socabaya",
    "Tiabaya",
    "Uchumayo",
    "Vitor",
    "Yanahuara",
    "Yarabamba",
    "Yura",
  ],
  Camaná: [
    "Camaná",
    "José María Quimper",
    "Mariano Nicolás Valcárcel",
    "Mariscal Cáceres",
    "Nicolás de Piérola",
    "Ocoña",
    "Quilca",
    "Samuel Pastor",
  ],
  Islay: [
    "Mollendo",
    "Cocachacra",
    "Deán Valdivia",
    "Islay",
    "Mejía",
    "Punta de Bombón",
  ],
};

const AllReportsPage: React.FC = () => {
  const [reportes, setReportes] = useState<ReporteDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filtroProvincia, setFiltroProvincia] = useState<string>("Arequipa");
  const [filtroDistrito, setFiltroDistrito] = useState<string>("");

  const distritosDisponibles =
    GEODATA[filtroProvincia as keyof typeof GEODATA] || [];

  useEffect(() => {
    const fetchReports = async () => {
      try {
        setLoading(true);
        setError(null);
        const params = new URLSearchParams();
        if (filtroProvincia) params.append("provincia", filtroProvincia);
        if (filtroDistrito) params.append("distrito", filtroDistrito);

        const response = await axios.get(`http://localhost:8080/api/reportes`, {
          params,
        });
        setReportes(response.data);
      } catch (err) {
        setError("No se pudieron cargar los reportes.");
      } finally {
        setLoading(false);
      }
    };
    fetchReports();
  }, [filtroProvincia, filtroDistrito]);

  const handleProvinciaChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setFiltroProvincia(e.target.value);
    setFiltroDistrito("");
  };

  return (
    <div className="text-white">
      <div className="flex flex-wrap justify-between items-center mb-6 gap-4">
        <h1 className="text-4xl font-bold">Todos los Reportes</h1>
        <div className="flex flex-wrap items-center space-x-4">
          <div className="flex items-center space-x-2">
            <label htmlFor="provincia-filtro" className="text-sm font-medium">
              Provincia:
            </label>
            <select
              id="provincia-filtro"
              value={filtroProvincia}
              onChange={handleProvinciaChange}
              className="bg-slate-700 border border-slate-600 rounded-md px-3 py-2 text-white"
            >
              <option value="">-- Todas --</option>
              {Object.keys(GEODATA)
                .sort()
                .map((p) => (
                  <option key={p} value={p}>
                    {p}
                  </option>
                ))}
            </select>
          </div>
          <div className="flex items-center space-x-2">
            <label htmlFor="distrito-filtro" className="text-sm font-medium">
              Distrito:
            </label>
            <select
              id="distrito-filtro"
              value={filtroDistrito}
              onChange={(e) => setFiltroDistrito(e.target.value)}
              className="bg-slate-700 border border-slate-600 rounded-md px-3 py-2 text-white"
              disabled={!filtroProvincia}
            >
              <option value="">-- Todos --</option>
              {distritosDisponibles.sort().map((d) => (
                <option key={d} value={d}>
                  {d}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {loading && <p className="text-center py-8">Cargando reportes...</p>}
      {error && (
        <p className="text-red-400 bg-red-900 p-3 rounded-md">{error}</p>
      )}

      {!loading &&
        !error &&
        (reportes.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {reportes.map((reporte) => (
              <ReportCard key={reporte.id} reporte={reporte} />
            ))}
          </div>
        ) : (
          <p className="text-center text-gray-400 mt-8">
            No se encontraron reportes para el filtro seleccionado.
          </p>
        ))}
    </div>
  );
};

export default AllReportsPage;
