package com.example.demo.Service;


import jakarta.persistence.EntityNotFoundException;
import com.example.demo.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.repository.ProductoRepository;

import java.io.IOException;
import java.util.Collection;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto getProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    public Producto saveProducto(String nombre, MultipartFile file) throws IOException {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setImagen(file.getBytes());
        return productoRepository.save(producto);
    }

    public Collection<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
}
