package com.sistema.sistemaprescripciondespachorecetas.datos.dato;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.MedicoConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicoEntity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class MedicoDatos {
    private final Path xmlPath;
    private final JAXBContext ctx;
    private MedicoConector cache;

    public MedicoDatos(String filePath) {
        try {
            this.xmlPath = Path.of(Objects.requireNonNull(filePath));
            this.ctx = JAXBContext.newInstance(MedicoConector.class, MedicoEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando JAXBContext", e);
        }
    }

    public synchronized MedicoConector load() {
        try {
            if (cache != null) { return cache; }

            if (!Files.exists(xmlPath)) {
                cache = new MedicoConector();
                save(cache);
                return cache;
            }
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            cache = (MedicoConector) unmarshaller.unmarshal(xmlPath.toFile());
            if (cache.getMedicos() == null) { cache.setMedicos(new ArrayList<>()); }
            return cache;
        } catch (Exception e) {
            throw new RuntimeException("Error cargando XML: " + xmlPath, e);
        }
    }

    public synchronized void save(MedicoConector data) {
        try {
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            File file = xmlPath.toFile();
            File parent = file.getParentFile();
            if (parent != null) { parent.mkdirs(); }
            java.io.StringWriter sw = new java.io.StringWriter();
            marshaller.marshal(data, sw);

            marshaller.marshal(data, file);

            cache = data;
        } catch (Exception e) {
            throw new RuntimeException("Error guardando XML: " + xmlPath, e);
        }
    }

    public Path getXmlPath() { return xmlPath; }

}
