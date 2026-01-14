export interface UbicacionDTO {
  latitud: number;
  longitud: number;
  direccion: string;
}

export interface ImagenAdjuntaDTO {
  url: string;
  nombreArchivo: string;
  tipoMime: string;
  tamanioBytes: number;
  fechaSubida: string;
}

export interface ComentarioDTO {
  id: string;
  usuarioId: string;
  contenido: string;
  fechaCreacion: string;
}

export interface HistorialCambioDTO {
  fechaCambio: string;
  descripcion: string;
  usuarioIdCambio: string;
}

export interface ReporteDTO {
  id: string;
  usuarioId: string;
  titulo: string;
  descripcion: string;
  estado: string;
  tipo: string;
  prioridad: string;
  fechaCreacion: string;
  fechaActualizacion: string;
  distrito?: string;
  provincia?: string;
  ubicacion: UbicacionDTO;
  historialCambios: HistorialCambioDTO[];
  imagenAdjunta: ImagenAdjuntaDTO | null;
}
