package com.hidrored.modulos.reportes.dominio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.hidrored.modulos.reportes.dominio.modelo.Reporte;
import com.hidrored.modulos.reportes.dominio.modelo.EstadoReporte;
import java.util.List;

@Repository
public interface IReporteRepository extends MongoRepository<Reporte, String>, IReporteRepositoryCustom {

  List<Reporte> findByUsuarioId(String usuarioId);

  List<Reporte> findByEstado(EstadoReporte estado);

  List<Reporte> findByDistritoIgnoreCase(String distrito);

  List<Reporte> findByProvinciaIgnoreCase(String provincia);

  List<Reporte> findByProvinciaIgnoreCaseAndDistritoIgnoreCase(String provincia, String distrito);

}
