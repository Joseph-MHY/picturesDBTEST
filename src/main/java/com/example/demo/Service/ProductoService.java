package com.example.demo.Service;

import com.example.demo.model.Producto;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.repository.ProductoRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto saveProducto(String nombre, MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String fileName = nombre + "-" + uuid + ".webp";
        Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);

        // Leer la imagen
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Redimensionar la imagen
        BufferedImage scaledImage = Scalr.resize(originalImage, 500);

        // Guardar la imagen como .webp
        ImageIO.write(scaledImage, "webp", new File(imagePath.toString()));

        Producto producto = new Producto();
        producto.setNameProducto(nombre);
        producto.setImagen("/images/" + fileName); // Guardar la ruta relativa

        return productoRepository.save(producto);
    }

    public Collection<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
}
