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
    private JAXBContext ctx;
    private MedicoConector cache;

    public MedicoDatos(String filePath) {
        try {
            this.xmlPath = Path.of(Objects.requireNonNull(filePath));
            System.out.println("[DEBUG] MedicoDatos inicializando con ruta: " + this.xmlPath);

            // Intentar inicializar JAXBContext
            initializeJAXBContext();

            System.out.println("[DEBUG] MedicoDatos inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[ERROR] Error inicializando MedicoDatos: " + e.getMessage());
            throw new RuntimeException("Error inicializando MedicoDatos con ruta: " + filePath, e);
        }
    }

    private void initializeJAXBContext() {
        try {
            this.ctx = JAXBContext.newInstance(MedicoConector.class, MedicoEntity.class);
            System.out.println("[DEBUG] JAXBContext inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[ERROR] Error creando JAXBContext: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inicializando JAXBContext", e);
        }
    }

    public synchronized MedicoConector load() {
        try {
            System.out.println("[DEBUG] Intentando cargar XML desde: " + xmlPath);

            if (cache != null) {
                System.out.println("[DEBUG] Retornando datos del cache");
                return cache;
            }

            if (!Files.exists(xmlPath)) {
                System.out.println("[DEBUG] Archivo no existe, creando nuevo con datos vacíos");
                cache = new MedicoConector();
                if (cache.getMedicos() == null) {
                    cache.setMedicos(new ArrayList<>());
                }
                save(cache);
                return cache;
            }

            if (ctx == null) {
                System.err.println("[ERROR] JAXBContext es null, reintentando inicialización");
                initializeJAXBContext();
            }

            System.out.println("[DEBUG] Deserializando XML...");
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            cache = (MedicoConector) unmarshaller.unmarshal(xmlPath.toFile());

            if (cache.getMedicos() == null) {
                System.out.println("[DEBUG] Lista de médicos era null, inicializando vacía");
                cache.setMedicos(new ArrayList<>());
            }

            System.out.println("[DEBUG] XML cargado correctamente, " + cache.getMedicos().size() + " médicos encontrados");
            return cache;

        } catch (Exception e) {
            System.err.println("[ERROR] Error cargando XML: " + e.getMessage());
            e.printStackTrace();

            // Intento de recuperación
            System.out.println("[DEBUG] Intentando crear estructura de datos vacía como fallback");
            cache = new MedicoConector();
            cache.setMedicos(new ArrayList<>());
            return cache;
        }
    }

    public synchronized void save(MedicoConector data) {
        try {
            System.out.println("[DEBUG] Intentando guardar XML en: " + xmlPath);

            if (ctx == null) {
                System.err.println("[ERROR] JAXBContext es null, reintentando inicialización");
                initializeJAXBContext();
            }

            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            File file = xmlPath.toFile();
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                System.out.println("[DEBUG] Creando directorios padre: " + parent.getAbsolutePath());
                boolean created = parent.mkdirs();
                System.out.println("[DEBUG] Directorios creados: " + created);
            }

            // Test con StringWriter primero
            java.io.StringWriter sw = new java.io.StringWriter();
            marshaller.marshal(data, sw);
            System.out.println("[DEBUG] XML generado correctamente (" + sw.toString().length() + " caracteres)");

            // Ahora guardar al archivo
            marshaller.marshal(data, file);
            cache = data;

            System.out.println("[DEBUG] XML guardado exitosamente en: " + file.getAbsolutePath());
            System.out.println("[DEBUG] Archivo existe después de guardar: " + file.exists());

        } catch (Exception e) {
            System.err.println("[ERROR] Error guardando XML: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error guardando XML: " + xmlPath, e);
        }
    }

    public Path getXmlPath() {
        return xmlPath;
    }

    // Método para testing/debug
    public boolean isContextValid() {
        return ctx != null;
    }

    public void clearCache() {
        this.cache = null;
        System.out.println("[DEBUG] Cache limpiado");
    }
}