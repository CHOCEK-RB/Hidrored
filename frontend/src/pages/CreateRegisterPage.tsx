import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

const CreateRegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { user } = useAuth();
  const { lat, lng } = location.state || { lat: 0, lng: 0 };

  const [formData, setFormData] = useState({
    titulo: "",
    descripcion: "",
    tipo: "FUGA",
    prioridad: "MEDIA",
    latitud: lat,
    longitud: lng,
    direccion: "",
  });
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) {
      setError("Debes iniciar sesión para crear un reporte.");
      return;
    }
    setError(null);

    const submissionData = new FormData();

    submissionData.append("titulo", formData.titulo);
    submissionData.append("descripcion", formData.descripcion);
    submissionData.append("tipo", formData.tipo);
    submissionData.append("prioridad", formData.prioridad);
    submissionData.append("latitud", formData.latitud.toString());
    submissionData.append("longitud", formData.longitud.toString());
    submissionData.append("direccion", formData.direccion);

    if (selectedFile) {
      submissionData.append("imagenFile", selectedFile);
    }

    try {
      const response = await axios.post("/api/reportes", submissionData, {
        headers: {
          "Content-Type": "multipart/form-data",
          usuarioId: user.id,
        },
      });

      console.log("Reporte creado con éxito!", response.data);
      alert("Reporte creado con éxito!");
      navigate("/mapa");
    } catch (err: any) {
      console.error("Error al crear el reporte:", err);
      setError(err.response?.data?.message || "No se pudo crear el reporte.");
    }
  };

  if (lat === 0 && lng === 0) {
    return (
      <div className="bg-white p-8 rounded-lg shadow-md text-center">
        <h2 className="text-2xl font-bold text-red-600 mb-4">
          Error: Ubicación no especificada
        </h2>
        <p className="text-gray-700">
          Para crear un reporte, primero debes hacer doble clic en el mapa para
          seleccionar una ubicación.
        </p>
        <button
          onClick={() => navigate("/mapa")}
          className="mt-4 bg-blue-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-blue-700"
        >
          Ir al Mapa
        </button>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-lg">
      <h1 className="text-3xl font-bold mb-6">
        Crear Nuevo Reporte de Incidencia
      </h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        {error && <p className="text-red-500 text-sm">{error}</p>}

        <div>
          <label
            htmlFor="titulo"
            className="block text-sm font-medium text-gray-700"
          >
            Título del Reporte
          </label>
          <input
            type="text"
            name="titulo"
            id="titulo"
            required
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
          />
        </div>

        <div>
          <label
            htmlFor="descripcion"
            className="block text-sm font-medium text-gray-700"
          >
            Descripción Detallada
          </label>
          <textarea
            name="descripcion"
            id="descripcion"
            required
            rows={4}
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
          ></textarea>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label
              htmlFor="tipo"
              className="block text-sm font-medium text-gray-700"
            >
              Tipo de Incidencia
            </label>
            <select
              name="tipo"
              id="tipo"
              value={formData.tipo}
              onChange={handleChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
            >
              <option value="FUGA">Fuga de Agua</option>
              <option value="TUBERIA_ROTA">Tubería Rota</option>
              <option value="DESBORDE">Desborde de Desagüe</option>
              <option value="OTROS">Otros</option>
            </select>
          </div>
          <div>
            <label
              htmlFor="prioridad"
              className="block text-sm font-medium text-gray-700"
            >
              Prioridad
            </label>
            <select
              name="prioridad"
              id="prioridad"
              value={formData.prioridad}
              onChange={handleChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
            >
              <option value="BAJA">Baja</option>
              <option value="MEDIA">Media</option>
              <option value="ALTA">Alta</option>
              <option value="URGENTE">Urgente</option>
            </select>
          </div>
        </div>

        <div>
          <label
            htmlFor="direccion"
            className="block text-sm font-medium text-gray-700"
          >
            Dirección de Referencia
          </label>
          <input
            type="text"
            name="direccion"
            id="direccion"
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
          />
        </div>
        <div>
          <label
            htmlFor="imagenFile"
            className="block text-sm font-medium text-gray-700"
          >
            Adjuntar Imagen (Opcional)
          </label>
          <input
            type="file"
            name="imagenFile"
            id="imagenFile"
            onChange={handleFileChange}
            accept="image/*" // Acepta solo archivos de imagen
            className="mt-1 block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
          />
        </div>
        <div className="bg-gray-100 p-4 rounded-md">
          <h3 className="font-semibold text-gray-800">
            Ubicación Seleccionada
          </h3>
          <p className="text-sm text-gray-600">
            Latitud: {lat.toFixed(6)}, Longitud: {lng.toFixed(6)}
          </p>
        </div>

        <button
          type="submit"
          className="w-full flex justify-center py-3 px-4 border-transparent rounded-md shadow-sm text-lg font-medium text-white bg-blue-600 hover:bg-blue-700"
        >
          Enviar Reporte
        </button>
      </form>
    </div>
  );
};

export default CreateRegisterPage;
