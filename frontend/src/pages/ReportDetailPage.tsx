import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import { type ReporteDTO } from "../components/InteractiveMap";

const ReportDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();
  const [reporte, setReporte] = useState<ReporteDTO | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [newComment, setNewComment] = useState("");

  const fetchReport = async () => {
    try {
      setLoading(true);
      const response = await axios.get(
        `http://localhost:8080/api/reportes/${id}`,
      );
      setReporte(response.data);
    } catch (err) {
      setError("No se pudo cargar el reporte. Es posible que no exista.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReport();
  }, [id]);

  const handleCommentSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newComment.trim() || !user) return;

    try {
      const response = await axios.post(
        `http://localhost:8080/api/reportes/${id}/comentarios`,
        { contenido: newComment },
        { headers: { "X-Usuario-ID": user.id } },
      );
      setReporte(response.data);
      setNewComment("");
    } catch (err) {
      console.error("Error al enviar el comentario:", err);
      alert("No se pudo enviar el comentario.");
    }
  };

  if (loading)
    return <p className="text-white text-center">Cargando reporte...</p>;
  if (error)
    return (
      <p className="text-red-400 bg-red-900 p-4 rounded-md text-center">
        {error}
      </p>
    );
  if (!reporte)
    return <p className="text-white text-center">Reporte no encontrado.</p>;

  return (
    <div className="text-white max-w-4xl mx-auto">
      <Link
        to="/todos-los-reportes"
        className="text-blue-400 hover:underline mb-6 inline-block"
      >
        ← Volver a todos los reportes
      </Link>

      <div className="bg-slate-800 p-8 rounded-lg shadow-xl">
        <h1 className="text-4xl font-bold mb-2">{reporte.titulo}</h1>
        <p className="text-gray-400 mb-6">ID del Reporte: {reporte.id}</p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6 text-center">
          <div className="bg-slate-700 p-3 rounded-md">
            <span className="font-bold">Estado:</span> {reporte.estado}
          </div>
          <div className="bg-slate-700 p-3 rounded-md">
            <span className="font-bold">Tipo:</span> {reporte.tipo}
          </div>
          <div className="bg-slate-700 p-3 rounded-md">
            <span className="font-bold">Prioridad:</span> {reporte.prioridad}
          </div>
        </div>
        {reporte.imagenAdjunta && (
          <div className="my-6">
            <h2 className="text-2xl font-bold mb-3">Imagen Adjunta</h2>
            <a
              href={`http://localhost:8080/uploads/${reporte.imagenAdjunta.url}`}
              target="_blank"
              rel="noopener noreferrer"
            >
              <img
                src={`http://localhost:8080/uploads/${reporte.imagenAdjunta.url}`}
                alt={`Imagen del reporte ${reporte.titulo}`}
                className="rounded-lg shadow-lg max-w-full h-auto cursor-pointer"
              />
            </a>
            <div className="text-sm text-gray-400 mt-2 bg-slate-700 p-3 rounded-md">
              <p>
                <strong>Nombre:</strong> {reporte.imagenAdjunta.nombreArchivo}
              </p>
              <p>
                <strong>Tamaño:</strong>{" "}
                {(reporte.imagenAdjunta.tamanioBytes / 1024 / 1024).toFixed(2)}{" "}
                MB
              </p>
              <p>
                <strong>Tipo:</strong> {reporte.imagenAdjunta.tipoMime}
              </p>
            </div>
          </div>
        )}
        <p className="text-lg mb-6">{reporte.descripcion}</p>

        {/* Sección de Comentarios */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold border-b-2 border-slate-600 pb-2 mb-4">
            Comentarios
          </h2>
          <div className="space-y-4 mb-6">
            {reporte.comentarios.length > 0 ? (
              reporte.comentarios.map((comment) => (
                <div key={comment.id} className="bg-slate-700 p-4 rounded-lg">
                  <p className="text-gray-300">{comment.contenido}</p>
                  <p className="text-xs text-gray-500 mt-2">
                    Por: {comment.usuarioId} -{" "}
                    {new Date(comment.fechaCreacion).toLocaleString()}
                  </p>
                </div>
              ))
            ) : (
              <p className="text-gray-400">
                No hay comentarios aún. ¡Sé el primero en comentar!
              </p>
            )}
          </div>

          {user && (
            <form onSubmit={handleCommentSubmit}>
              <textarea
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
                placeholder="Escribe tu comentario aquí..."
                className="w-full p-3 rounded-md bg-slate-900 border border-slate-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                rows={3}
                required
              />
              <button
                type="submit"
                className="mt-2 px-6 py-2 bg-blue-600 hover:bg-blue-700 rounded-md font-semibold"
              >
                Enviar Comentario
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
};

export default ReportDetailPage;
