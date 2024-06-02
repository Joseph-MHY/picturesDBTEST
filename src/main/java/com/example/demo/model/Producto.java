package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('producto_id_producto_seq'::regclass)")
    @Column(name = "id_producto", nullable = false)
    private Integer id;

    @Column(name = "name_producto", nullable = false, length = 40)
    private String nameProducto;

    @Column(name = "imagen", nullable = false, length = Integer.MAX_VALUE)
    private String imagen;

}