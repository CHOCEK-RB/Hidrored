package com.hidrored.modulos.social.infraestructura.web;

import com.hidrored.modulos.social.aplicacion.AgregarComentarioCommand;
import com.hidrored.modulos.social.aplicacion.AgregarComentarioService;
import com.hidrored.modulos.social.aplicacion.ListarComentariosPorReferenciaService;
import com.hidrored.modulos.social.dominio.Comentario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {

  private final AgregarComentarioService agregarComentarioService;
  private final ListarComentariosPorReferenciaService listarComentariosPorReferenciaService;

  public ComentarioController(AgregarComentarioService agregarComentarioService,
      ListarComentariosPorReferenciaService listarComentariosPorReferenciaService) {
    this.agregarComentarioService = agregarComentarioService;
    this.listarComentariosPorReferenciaService = listarComentariosPorReferenciaService;
  }

  @PostMapping("/{referenciaId}")
  public ResponseEntity<ComentarioResponse> agregarComentario(
      @PathVariable String referenciaId,
      @RequestHeader("X-Usuario-ID") String usuarioId,
      @RequestBody AgregarComentarioRequest request) {
    try {
      AgregarComentarioCommand command = new AgregarComentarioCommand(
          referenciaId,
          "REPORTE", // Hardcoded for now, can be dynamic
          usuarioId,
          request.getContenido());
      Comentario nuevoComentario = agregarComentarioService.agregarComentario(command);
      return new ResponseEntity<>(ComentarioResponse.fromDomain(nuevoComentario), HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{referenciaId}")
  public ResponseEntity<List<ComentarioResponse>> listarComentariosPorReferencia(
      @PathVariable String referenciaId) {
    try {
      List<ComentarioResponse> comentarios = listarComentariosPorReferenciaService.listarComentarios(referenciaId);
      return new ResponseEntity<>(comentarios, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
