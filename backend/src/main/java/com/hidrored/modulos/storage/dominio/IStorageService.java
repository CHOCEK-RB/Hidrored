package com.hidrored.modulos.storage.dominio;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String guardar(MultipartFile file);

  void eliminar(String url);
}
