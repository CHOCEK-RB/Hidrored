package com.hidrored.modulos.reportes.dominio;

import com.hidrored.modulos.reportes.dominio.modelo.Reporte;
import java.util.List;

public interface IReporteRepositoryCustom {

  List<Reporte> executeGeoSearch(double longitude, double latitude, double radiusInRadians);
}
