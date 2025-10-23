# 📌 Sistema de Prescripción y Despacho de Recetas  

Este proyecto implementa un **Sistema de Prescripción y Despacho de Recetas Médicas**, desarrollado bajo principios de **arquitectura en N-capas** y el **patrón MVC (Modelo-Vista-Controlador)**.  

---

## 🏗️ Arquitectura del Sistema  

El sistema está diseñado con una estructura modular que facilita la **escalabilidad, mantenibilidad y reutilización del código**.  
### 🔹 Capas principales  
1. **Interfaz de Usuario (MVC)**  
   - **Vista:** componentes gráficos de interacción con el usuario.  
   - **Controlador:** gestiona eventos y coordina el flujo de datos entre la vista y la lógica.
     

2. **Lógica**  
   - Implementa el procesamiento central de las recetas (validaciones, cálculos y reglas de prescripción).  

3. **Datos**  
   - Manejo de persistencia de información utilizando **MySQL** como base de datos.  

4. **Modelos**  
   - Sirve como puente de datos entre la interfaz y las demás capas.  

5. **Base de Datos**  
   - Implementada en una base de datos MySQL para almacenamiento estructurado y consultas eficientes.

---

## 📊 Diagrama de Arquitectura  

La siguiente representación muestra la relación entre las capas y componentes:  

<img width="440" height="824" alt="image" src="https://github.com/user-attachments/assets/a60c9181-b7b7-4d98-beef-bc02553ed971" />

---

## 👥 Tipos de Usuarios y Funcionalidades  

El sistema contempla **tres tipos de usuarios**, cada uno con permisos y responsabilidades específicas:  

### 🔹 1. Médico  
- Registrar y prescribir recetas para los pacientes.  
- Consultar el historial médico del paciente.  
- Validar disponibilidad de medicamentos antes de emitir la receta.  

### 🔹 2. Farmacéutico  
- Consultar recetas emitidas por los médicos.  
- Confirmar la entrega de medicamentos al paciente.  
- Actualizar el estado de las recetas (pendiente, despachada, rechazada).  

### 🔹 3. Administrador  
- Gestionar usuarios del sistema (crear, editar y eliminar cuentas).  
- Administrar el inventario de medicamentos (agregar, actualizar o dar de baja).  
- Consultar reportes del sistema (recetas generadas, entregas realizadas, control de stock).  

---

## ⚙️ Tecnologías Utilizadas  

- **Lenguaje:** Java  
- **Interfaz gráfica:** JavaFX (con FXML)  
- **Arquitectura:** N-capas  
- **Patrón de diseño:** MVC  
- **Base de datos:** MySQL

---

## 🚀 Ejecución del Proyecto  

1. Clonar el repositorio:  
   ```bash
   git clone https://github.com/SebastianGomezSolis/SistemaPrescripcionDespachoRecetas.git
   ```
2. Importar en un IDE como **IntelliJ IDEA** o **Eclipse**.  
3. Ejecutar la clase `Application.java`.  

---

## 🎯 Objetivo del Sistema  

El sistema busca **automatizar la prescripción y despacho de recetas médicas**, ofreciendo:  
- Registro y consulta de pacientes y medicamentos.  
- Generación y validación de recetas.  
- Persistencia en MySQL.
- Creacion de nuevos medicos y farmaceutas.
- Diferentes vistas y permisos según el tipo de usuario.  
- Interfaz intuitiva bajo el patrón MVC.

---

## 👥 Autores

- Sebastián Gómez Solís - [@SebastianGomezSolis](https://github.com/SebastianGomezSolis)
- Cinthya Barahona Guevara - [@aashh16](https://github.com/aashh16)
- Aslhi Gutierrez Romero - [@BCinthya](https://github.com/BCinthya)

---
