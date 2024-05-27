package com.example.demo.controller;

import com.example.demo.Service.ProductoService;
import com.example.demo.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getProductoImagen(@PathVariable Long id) {
        try {
            Producto producto = productoService.getProducto(id);
            String nombreImagen = producto.getNombre() + "-" + UUID.randomUUID() + ".webp"; // Utiliza el nombre del producto como nombre de la imagen
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreImagen + "\"")
                    .body(producto.getImagen());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Producto>> getAllProductos() {
        Collection<Producto> productos = productoService.getAllProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
