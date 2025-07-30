import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import L from "leaflet";
import axios from "axios";
import InteractiveMap from "../components/InteractiveMap";
import type { ReporteDTO } from "../types";

const MapPage: React.FC = () => {
  const navigate = useNavigate();
  const [allReports, setAllReports] = useState<ReporteDTO[]>([]);
  const [displayedReports, setDisplayedReports] = useState<ReporteDTO[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [selectedLocation, setSelectedLocation] = useState<L.LatLng | null>(
    null,
  );

  useEffect(() => {
    const fetchReports = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await axios.get("http://localhost:8080/api/reportes");
        setAllReports(response.data);
        setDisplayedReports(response.data);
      } catch (err) {
        console.error("Error al obtener los reportes:", err);
        setError("No se pudieron cargar los reportes.");
      } finally {
        setLoading(false);
      }
    };
    fetchReports();
  }, []);

  const handleMapClick = (coords: L.LatLng) => {
    setSelectedLocation(coords);
  };

  const handleGoToCreateReport = () => {
    if (selectedLocation) {
      const { lat, lng } = selectedLocation;
      navigate("/crear-reporte", { state: { lat, lng } });
    }
  };

  const handleSearchNearby = async () => {
    if (!selectedLocation) return;
    try {
      setLoading(true);
      setError(null);
      const { lat, lng } = selectedLocation;
      const response = await axios.get(
        `http://localhost:8080/api/reportes/cercanos`,
        {
          params: { lat, lng, radioKm: 1 },
        },
      );
      setDisplayedReports(response.data);
      setSelectedLocation(null);
    } catch (err) {
      console.error("Error al buscar reportes cercanos:", err);
      setError("No se pudo realizar la búsqueda.");
    } finally {
      setLoading(false);
    }
  };

  const clearSearch = () => {
    setDisplayedReports(allReports);
  };

  return (
    <div className="h-full flex flex-col text-white">
      <div className="flex justify-between items-center mb-4">
        <div>
          <h1 className="text-3xl font-bold">
            Mapa Interactivo de Incidencias
          </h1>
          <p>
            Haz clic en el mapa para registrar una incidencia o buscar cerca.
          </p>
        </div>
        {displayedReports.length !== allReports.length && (
          <button
            onClick={clearSearch}
            className="bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Mostrar Todos
          </button>
        )}
      </div>

      {loading && <p className="text-center">Cargando...</p>}
      {error && (
        <p className="text-red-400 bg-red-900 p-3 rounded-md mb-4">{error}</p>
      )}

      <div className="flex-grow rounded-lg shadow-lg overflow-hidden">
        <InteractiveMap
          reports={displayedReports}
          onMapClick={handleMapClick}
          selectedLocation={selectedLocation}
          onGoToCreateReport={handleGoToCreateReport}
          onSearchNearby={handleSearchNearby}
        />
      </div>
    </div>
  );
};

export default MapPage;
