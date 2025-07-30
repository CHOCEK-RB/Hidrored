package com.hidrored.dominio.reportes;

import com.hidrored.dominio.reportes.modelo.Reporte;
import java.util.List;

public interface IReporteRepositoryCustom {

  List<Reporte> executeGeoSearch(double longitude, double latitude, double radiusInRadians);
}
