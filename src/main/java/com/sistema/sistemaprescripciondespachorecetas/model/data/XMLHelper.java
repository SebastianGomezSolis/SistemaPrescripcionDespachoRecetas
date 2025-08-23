package com.sistema.sistemaprescripciondespachorecetas.model.data;

import com.sistema.sistemaprescripciondespachorecetas.model.logic.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;

public class XMLHelper {
    private static XMLHelper instancia;

    private XMLHelper() {}

    public static XMLHelper getInstancia() {
        if (instancia == null) {
            instancia = new XMLHelper();
        }
        return instancia;
    }

    public void guardarAdministrador(DataAdministrador data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Administradores");
            document.appendChild(root);

            for (Administrador administrador : data.getAdministradores()) {
                Element administradorElement = document.createElement("Administrador");

                Element idElement = document.createElement("Id");
                idElement.appendChild(document.createTextNode(administrador.getId()));
                administradorElement.appendChild(idElement);

                Element claveElement = document.createElement("Clave");
                claveElement.appendChild(document.createTextNode(administrador.getClave()));
                administradorElement.appendChild(claveElement);

                root.appendChild(administradorElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarFarmauceuta(DataFarmauceuta data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Farmaceutas");
            document.appendChild(root);

            for (Farmaceuta farmaceuta : data.getFarmaceutas()) {
                Element farmaceutaElement = document.createElement("Farmaceuta");

                Element idElement = document.createElement("Id");
                idElement.appendChild(document.createTextNode(farmaceuta.getId()));
                farmaceutaElement.appendChild(idElement);

                Element claveElement = document.createElement("Clave");
                claveElement.appendChild(document.createTextNode(farmaceuta.getClave()));
                farmaceutaElement.appendChild(claveElement);

                Element nombreElement = document.createElement("Nombre");
                nombreElement.appendChild(document.createTextNode(farmaceuta.getNombre()));
                farmaceutaElement.appendChild(nombreElement);

                root.appendChild(farmaceutaElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarMedicamento(DataMedicamento data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Medicamentos");
            document.appendChild(root);

            for (Medicamento medicamento : data.getMedicamentos()) {
                Element medicamentoElement = document.createElement("Medicamento");

                Element codigoElement = document.createElement("Codigo");
                codigoElement.appendChild(document.createTextNode(medicamento.getCodigo()));
                medicamentoElement.appendChild(codigoElement);

                Element nombreElement = document.createElement("Nombre");
                nombreElement.appendChild(document.createTextNode(medicamento.getNombre()));
                medicamentoElement.appendChild(nombreElement);

                Element descripcionElement = document.createElement("Descripcion");
                descripcionElement.appendChild(document.createTextNode(medicamento.getDescripcion()));
                medicamentoElement.appendChild(descripcionElement);

                root.appendChild(medicamentoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarMedico(DataMedico data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Medicos");
            document.appendChild(root);

            for (Medico medico : data.getMedicos()) {
                Element medicoElement = document.createElement("Medico");

                Element idElement = document.createElement("Id");
                idElement.appendChild(document.createTextNode(medico.getId()));
                medicoElement.appendChild(idElement);

                Element claveElement = document.createElement("Clave");
                claveElement.appendChild(document.createTextNode(medico.getClave()));
                medicoElement.appendChild(claveElement);

                Element nombreElement = document.createElement("Nombre");
                nombreElement.appendChild(document.createTextNode(medico.getNombre()));
                medicoElement.appendChild(nombreElement);

                Element especialidadElement = document.createElement("Especialidad");
                especialidadElement.appendChild(document.createTextNode(medico.getEspecialidad()));
                medicoElement.appendChild(especialidadElement);

                root.appendChild(medicoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarPaciente(DataPaciente data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Pacientes");
            document.appendChild(root);

            for (Paciente paciente : data.getPacientes()) {
                Element pacienteElement = document.createElement("Paciente");

                Element idPacienteElement = document.createElement("Id");
                idPacienteElement.appendChild(document.createTextNode(paciente.getId()));
                pacienteElement.appendChild(idPacienteElement);

                Element nombreElement = document.createElement("Nombre");
                nombreElement.appendChild(document.createTextNode(paciente.getNombre()));
                pacienteElement.appendChild(nombreElement);

                Element fechaNacimientoElement = document.createElement("FechaNacimiento");
                fechaNacimientoElement.appendChild(document.createTextNode(paciente.getFechaNacimiento().toString()));
                pacienteElement.appendChild(fechaNacimientoElement);

                Element telefonoElement = document.createElement("Telefono");
                telefonoElement.appendChild(document.createTextNode(paciente.getTelefono()));
                pacienteElement.appendChild(telefonoElement);

                root.appendChild(pacienteElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarReceta(DataReceta data, String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Recetas");
            document.appendChild(root);

            for (Receta receta : data.getRecetas()) {
                Element recetaElement = document.createElement("Receta");

                Element idReceta = document.createElement("Id");
                idReceta.appendChild(document.createTextNode(receta.getId()));
                recetaElement.appendChild(idReceta);

                if (receta.getPaciente() != null) {
                    Element pacienteElement = document.createElement("Paciente");
                    pacienteElement.setAttribute("Id", receta.getPaciente().getId());
                    pacienteElement.setAttribute("Nombre", receta.getPaciente().getNombre());
                    pacienteElement.setAttribute("FechaNacimiento", receta.getPaciente().getFechaNacimiento().toString());
                    pacienteElement.setAttribute("Telefono", receta.getPaciente().getTelefono());
                    recetaElement.appendChild(pacienteElement);
                }

                if (receta.getMedicamento() != null) {
                    Element medicamentoElement = document.createElement("Medicamento");
                    medicamentoElement.setAttribute("Codigo", receta.getMedicamento().getCodigo());
                    medicamentoElement.setAttribute("Nombre", receta.getMedicamento().getNombre());
                    medicamentoElement.setAttribute("Descripcion", receta.getMedicamento().getDescripcion());
                    recetaElement.appendChild(medicamentoElement);
                }

                Element cantidadElement = document.createElement("Cantidad");
                cantidadElement.appendChild(document.createTextNode(String.valueOf(receta.getCantidad())));
                recetaElement.appendChild(cantidadElement);

                Element indicacionesElement = document.createElement("Indicaciones");
                indicacionesElement.appendChild(document.createTextNode(receta.getIndicaciones()));
                recetaElement.appendChild(indicacionesElement);

                Element duracionElement = document.createElement("Duracion");
                duracionElement.appendChild(document.createTextNode(String.valueOf(receta.getDuracion())));
                recetaElement.appendChild(duracionElement);

                Element fechaEntregaElement = document.createElement("FechaEntrega");
                fechaEntregaElement.appendChild(document.createTextNode(receta.getFechaEntrega().toString()));
                recetaElement.appendChild(fechaEntregaElement);

                Element estadoElement = document.createElement("Estado");
                estadoElement.appendChild(document.createTextNode(receta.getEstado()));
                recetaElement.appendChild(estadoElement);

                root.appendChild(recetaElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataAdministrador cargarAdministrador(String path) {
        DataAdministrador data = new DataAdministrador();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Administrador");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element administradorElement = (Element) nodes.item(i);

                String id = administradorElement.getElementsByTagName("Id").item(0).getTextContent();
                String clave = administradorElement.getElementsByTagName("Clave").item(0).getTextContent();

                data.agregarAdministrador(new Administrador(id, clave));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataFarmauceuta cargarFarmauceuta(String path) {
        DataFarmauceuta data = new DataFarmauceuta();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Farmaceuta");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element farmaceutaElement = (Element) nodes.item(i);

                String id = farmaceutaElement.getElementsByTagName("Id").item(0).getTextContent();
                String clave = farmaceutaElement.getElementsByTagName("Clave").item(0).getTextContent();
                String nombre = farmaceutaElement.getElementsByTagName("Nombre").item(0).getTextContent();

                data.agregarFarmaceuta(new Farmaceuta(id, clave, nombre));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataMedicamento cargarMedicamento(String path) {
        DataMedicamento data = new DataMedicamento();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Medicamento");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element medicamentoElement = (Element) nodes.item(i);

                String codigo = medicamentoElement.getElementsByTagName("Codigo").item(0).getTextContent();
                String nombre = medicamentoElement.getElementsByTagName("Nombre").item(0).getTextContent();
                String descripcion = medicamentoElement.getElementsByTagName("Descripcion").item(0).getTextContent();

                data.agregarMedicamento(new Medicamento(codigo, nombre, descripcion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataMedico cargarMedico(String path) {
        DataMedico data = new DataMedico();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Medico");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element medicoElement = (Element) nodes.item(i);

                String id = medicoElement.getElementsByTagName("Id").item(0).getTextContent();
                String clave = medicoElement.getElementsByTagName("Clave").item(0).getTextContent();
                String nombre = medicoElement.getElementsByTagName("Nombre").item(0).getTextContent();
                String especialidad = medicoElement.getElementsByTagName("Especialidad").item(0).getTextContent();

                data.agregarMedico(new Medico(id, clave, nombre, especialidad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataPaciente cargarPaciente(String path) {
        DataPaciente data = new DataPaciente();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Paciente");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element pacienteElement = (Element) nodes.item(i);

                String id = pacienteElement.getElementsByTagName("Id").item(0).getTextContent();
                String nombre = pacienteElement.getElementsByTagName("Nombre").item(0).getTextContent();
                LocalDate fechaNacimiento = LocalDate.parse(pacienteElement.getElementsByTagName("FechaNacimiento").item(0).getTextContent());
                String telefono = pacienteElement.getElementsByTagName("Telefono").item(0).getTextContent();

                data.agregarPaciente(new Paciente(id, nombre, fechaNacimiento, telefono));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataReceta cargarReceta(String path) {
        DataReceta data = new DataReceta();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));

            NodeList nodes = document.getElementsByTagName("Receta");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element recetaElement = (Element) nodes.item(i);

                String id = recetaElement.getElementsByTagName("Id").item(0).getTextContent();

                // Cargar Paciente
                Paciente paciente = null;
                NodeList pacienteNode = recetaElement.getElementsByTagName("Paciente");
                if (pacienteNode.getLength() > 0) {
                    Element pacienteElement = (Element) pacienteNode.item(0);
                    paciente = new Paciente(
                            pacienteElement.getAttribute("Id"),
                            pacienteElement.getAttribute("Nombre"),
                            LocalDate.parse(pacienteElement.getAttribute("FechaNacimiento")),
                            pacienteElement.getAttribute("Telefono")
                    );
                }

                // Cargar Medicamento
                Medicamento medicamento = null;
                NodeList medicamentoNode = recetaElement.getElementsByTagName("Medicamento");
                if (medicamentoNode.getLength() > 0) {
                    Element medicamentoElement = (Element) medicamentoNode.item(0);
                    medicamento = new Medicamento(
                            medicamentoElement.getAttribute("Codigo"),
                            medicamentoElement.getAttribute("Nombre"),
                            medicamentoElement.getAttribute("Descripcion")
                    );
                }

                int cantidad = Integer.parseInt(recetaElement.getElementsByTagName("Cantidad").item(0).getTextContent());
                String indicaciones = recetaElement.getElementsByTagName("Indicaciones").item(0).getTextContent();
                int duracion = Integer.parseInt(recetaElement.getElementsByTagName("Duracion").item(0).getTextContent());
                LocalDate fechaEntrega = LocalDate.parse(recetaElement.getElementsByTagName("FechaEntrega").item(0).getTextContent());
                String estado = recetaElement.getElementsByTagName("Estado").item(0).getTextContent();

                data.agregarReceta(new Receta(id, paciente, medicamento, cantidad, indicaciones, duracion, fechaEntrega, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
