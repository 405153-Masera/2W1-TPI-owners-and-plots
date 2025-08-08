# TPI - Trabajo Práctico Integrador

Este proyecto forma parte del Trabajo Práctico Integrador (TPI) desarrollado durante el año 2024, correspondiente a las asignaturas **Laboratorio de Computación IV**, **Programación IV** y **Metodología de Sistemas** de la carrera **Tecnicatura Universitaria en Programación** en la **Universidad Tecnológica Nacional - Facultad Regional Córdoba**.

<div align="center"> 
  <h1>👤 Gestión de Lotes y Propietarios 👤</h1>
</div>

## ✍ Descripción

El microservicio de Gestión de Lotes y Propietarios permite administrar los lotes y sus propietarios dentro de la aplicación. Brinda a los administradores la capacidad de gestionar los datos de los lotes, como su asignación y estado, así como la información personal de los propietarios y la asignación de los lotes a estos. Es utilizado por la mayoría de los demás microservicios, siendo una parte esencial en el proyecto para la correcta gestión de los lotes y sus propietarios.

Este sistema incluye funcionalidades para:

- Consultar la lista completa de lotes (plots), asignar o actualizar propietarios, y gestionar detalles de la información de los lotes.
  
- Consultar la lista completa de propietarios (owners), asignar lotes a propietarios, y gestionar los detalles de la información personal de los propietarios.
  
- Consultas personalizadas para obtener información sobre lotes y propietarios, tanto para otros grupos como para nuestro propio grupo en microservicios como OwnersPlots.

- Gestionar la disponibilidad de lotes y realizar acciones de asignación o liberación de lotes.
  
- Consultar información relacionada con los estados de los lotes, como si están ocupados o disponibles.
  
- Generar relaciones entre lotes y propietarios a través de consultas específicas que conectan ambas entidades.

- Consultar estadísticas y generar informes relacionados con lotes y propietarios, como el conteo por estado de propietario (activo, inactivo, etc.).
  
-Proteger la información relacionada con lotes y propietarios, asegurando que solo usuarios autorizados puedan acceder o modificar datos sensibles mediante un sistema de autenticación y autorización.


### Funcionalidades principales:

Alta de propietarios:

Permite crear un nuevo propietario con información personal, tipo de documento, situación fiscal, y otros datos relevantes.
Consulta de propietarios:

Permite obtener la lista completa de propietarios o buscar propietarios específicos por ID. También permite obtener los propietarios asociados a un lote.
Actualización de información de propietarios:

Permite actualizar los datos de un propietario, como su nombre, situación fiscal, tipo de propietario, entre otros.
Gestión de lotes:

Permite crear, actualizar y consultar lotes. Incluye funcionalidades para obtener todos los lotes disponibles, obtener lotes por propietario y gestionar el estado de los lotes.
Baja lógica de propietarios:

Permite dar de baja a un propietario de forma lógica, eliminando su acceso a los recursos del sistema sin eliminar la información.
   

## 📥 Instalación

### Requisitos previos

- Java 17
- Maven
- Docker desktop
- Spring Boot
- Eureka Server

### Pasos de instalación

1. Clonar este repositorio en tu máquina local.
2. Configurar las propiedades necesarias en el archivo `application.properties` o `application.yml`.
3. Ejecutar el comando `mvn spring-boot:run` para iniciar la aplicación.

### Opcional: Docker

1. Abrir docker desktop.
2. Clonar el repositorio en tu máquina local.
3. Abrir una consola en la dirección donde se encuentra el docker-compose.yml
4. Ejecutar el comando `docker compose -up --build` para realizar el buildeo del contenedor.

---

## 🌐 Endpoints Principales

### **Usuarios**

- **GET** `/owners`  
  Retorna una lista de todos los propietarios activos registrados en el sistema.

- **GET** `/owners/{ownerId}`  
  Permite consultar la información detallada de un propietario específico.

- **POST** `/owners`  
  Crea un nuevo propietario en el sistema.

- **PUT** `owners/{ownerId}`  
  Actualiza la información de un propietario existente.

### **Plots**

- **GET** `/plots`  
  Retorna una lista de todos los lotes en el sistema.

- **GET** `/plots/{plotId}`  
  Permite consultar la información detallada de un lote específico.

- **POST** `/plots`  
  Crea un nuevo plot en el sistema.

- **PUT** `plots/{plotId}`  
  Actualiza la información de un plot existente.

## Integrantes

<div align="center">

| Nombre                      |
| --------------------------- |
| Bertello Valentino          |
| Cifuentes Pilar             |
| Lara, Ulises                |
| Lopez, Camila Antonella     |
| Masera, Gustavo Martin      |
| Moro, Benjamín              |
| Ruiz, Facundo Nicolás       |
|  Sánchez, Juan Manuel       |

</div>
