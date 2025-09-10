package com.sistema.sistemaprescripciondespachorecetas.datos.dato;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.AdministradorConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.AdministradorEntity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class AdministradorDatos {
    private final Path xmlPath;
    private final JAXBContext ctx;
    private AdministradorConector cache;

    public AdministradorDatos(String filePath) {
        try {
            this.xmlPath = Path.of(Objects.requireNonNull(filePath));
            this.ctx = JAXBContext.newInstance(AdministradorConector.class, AdministradorEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
