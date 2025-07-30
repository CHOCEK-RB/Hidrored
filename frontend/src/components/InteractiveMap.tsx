import React, { useEffect, useRef } from "react";
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  useMapEvents,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";

import { type ReporteDTO } from "../types";

const defaultIcon = L.icon({
  iconUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png",
  iconRetinaUrl:
    "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon-2x.png",
  shadowUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});
L.Marker.prototype.options.icon = defaultIcon;
const temporaryPinIcon = new L.DivIcon({
  className: "custom-div-icon",
  html: `<div style="background-color: #2563eb; width: 2rem; height: 2rem; border-radius: 50% 50% 50% 0; transform: rotate(-45deg); border: 3px solid #fff; box-shadow: 0 0 5px rgba(0,0,0,0.5);"></div><div style="width: 0; height: 0; border-left: 6px solid transparent; border-right: 6px solid transparent; border-top: 10px solid #2563eb; position: absolute; top: 28px; left: 13px;"></div>`,
  iconSize: [30, 42],
  iconAnchor: [15, 42],
});

interface InteractiveMapProps {
  reports: ReporteDTO[];
  onMapClick: (coords: L.LatLng) => void;
  selectedLocation: L.LatLng | null;
  onGoToCreateReport: () => void;
  onSearchNearby: () => void;
}

const MapEvents: React.FC<{ onClick: (coords: L.LatLng) => void }> = ({
  onClick,
}) => {
  useMapEvents({
    click(e) {
      onClick(e.latlng);
    },
  });
  return null;
};

const TemporaryPin: React.FC<{
  position: L.LatLng;
  onCreate: () => void;
  onSearch: () => void;
}> = ({ position, onCreate, onSearch }) => {
  const markerRef = useRef<L.Marker>(null);
  useEffect(() => {
    markerRef.current?.openPopup();
  }, []);

  return (
    <Marker position={position} icon={temporaryPinIcon} ref={markerRef}>
      <Popup autoClose={false} closeOnClick={false}>
        <div className="text-center">
          <p className="font-semibold mb-2">¿Qué deseas hacer?</p>
          <button
            onClick={onCreate}
            className="w-full bg-blue-600 text-white text-sm font-bold py-2 px-4 rounded-lg hover:bg-blue-700 mb-2 transition-colors"
          >
            Crear Reporte Aquí
          </button>
          <button
            onClick={onSearch}
            className="w-full bg-gray-200 text-gray-800 text-sm font-bold py-2 px-4 rounded-lg hover:bg-gray-300 transition-colors"
          >
            Buscar Cerca (1km)
          </button>
        </div>
      </Popup>
    </Marker>
  );
};

const InteractiveMap: React.FC<InteractiveMapProps> = ({
  reports,
  onMapClick,
  selectedLocation,
  onGoToCreateReport,
  onSearchNearby,
}) => {
  const mapCenter: L.LatLngExpression = [-16.42, -71.517];

  return (
    <MapContainer
      center={mapCenter}
      zoom={15}
      style={{ height: "100%", width: "100%", borderRadius: "8px" }}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />

      {reports.map((report) => (
        <Marker
          key={report.id}
          position={[report.ubicacion.longitud, report.ubicacion.latitud]}
        >
          <Popup>
            <h3 className="font-bold text-lg">{report.titulo}</h3>
            <p className="text-sm text-gray-600">
              <strong>Tipo:</strong> {report.tipo}
            </p>
            <p className="text-sm text-gray-600">
              <strong>Prioridad:</strong> {report.prioridad}
            </p>
            <p className="mt-2">{report.descripcion}</p>
          </Popup>
        </Marker>
      ))}

      {selectedLocation && (
        <TemporaryPin
          position={selectedLocation}
          onCreate={onGoToCreateReport}
          onSearch={onSearchNearby}
        />
      )}

      <MapEvents onClick={onMapClick} />
    </MapContainer>
  );
};

export default InteractiveMap;
