package com.hidrored.infraestructura.reportes;

import com.hidrored.dominio.reportes.IReporteRepositoryCustom;
import com.hidrored.dominio.reportes.modelo.Reporte;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IReporteRepositoryImpl implements IReporteRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  public IReporteRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Reporte> executeGeoSearch(double longitude, double latitude, double radiusInRadians) {
    Point center = new Point(longitude, latitude);
    Circle circle = new Circle(center, radiusInRadians);

    Query query = new Query(Criteria.where("location").withinSphere(circle));

    return mongoTemplate.find(query, Reporte.class);
  }
}
