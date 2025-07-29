package com.hidrored.dominio.reportes;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hidrored.dominio.reportes.modelo.EstadoReporte;
import com.hidrored.dominio.reportes.modelo.Reporte;

import java.util.List;

@Repository
public interface IReporteRepository extends MongoRepository<Reporte, String> {

  List<Reporte> findByUsuarioId(String usuarioId);

  List<Reporte> findByEstado(EstadoReporte estado);

}
