package com.example.demo.controller;

import com.example.demo.Service.ProductoService;
import com.example.demo.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<String> uploadProducto(@RequestParam("nombre") String nombre,
            @RequestParam("imagen") MultipartFile imagen) {
        try{
            productoService.saveProducto(nombre, imagen);
            return new ResponseEntity<>("Producto guardado con éxito", HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>("Error al guardar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<Resource> getProductoImagen(@PathVariable Long id) {
        try {
            Producto producto = productoService.getProducto(id);
            System.out.println(producto.getId() + "\n" + producto.getNameProducto() + "\n" + producto.getImagen());
            Path imagePath = Paths.get("src/main/resources/static" + producto.getImagen());
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() || resource.isReadable()){
                System.out.println("imagen disponible");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                System.out.println("imagen no disponible");
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Collection<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public Producto getProducto(@PathVariable Long id) {
        return productoService.getProducto(id);
    }
}
