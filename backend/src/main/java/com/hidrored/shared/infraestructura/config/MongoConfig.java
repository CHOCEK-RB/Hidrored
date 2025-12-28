package com.hidrored.shared.infraestructura.config;

import com.hidrored.dominio.reportes.modelo.Reporte;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.lang.NonNull;

@Configuration
public class MongoConfig implements ApplicationListener<ContextRefreshedEvent> {

  private final MongoTemplate mongoTemplate;
  private boolean indexEnsured = false;

  public MongoConfig(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
    if (indexEnsured) {
      return;
    }

    if (!mongoTemplate.collectionExists(Reporte.class)) {
      mongoTemplate.createCollection(Reporte.class);
    }

    String collectionName = mongoTemplate.getCollectionName(Reporte.class);

    GeospatialIndex geoIndex = new GeospatialIndex("location");

    mongoTemplate.indexOps(collectionName).ensureIndex(geoIndex);

    this.indexEnsured = true;
  }
}
