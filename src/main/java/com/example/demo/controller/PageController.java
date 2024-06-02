package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PageController {

    @GetMapping("/page")
    public String page() {
        return "pagina";
    }

    @GetMapping("/products")
    public String productos() {
        return "productos.html";
    }

    @GetMapping("/images/{nomImagen}")
    public ResponseEntity<Resource> imagen(@PathVariable String nomImagen) {
        // Construir la ruta completa de la imagen estática
        Path imagePath = Paths.get("src/main/resources/static/images/" + nomImagen);
        Resource resource;
        try {
            resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo de contenido según el tipo de imagen
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
