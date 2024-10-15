# TPI

Este proyecto es parte del Trabajo Práctico Integrador (TPI) realizado durante el año 2024, de las asignaturas Laboratorio de Computación IV, Programación IV y Metodología de Sistemas de la carrera "Tecnicatura Universitaria en Programación" de la "Universidad Tecnológica Nacional - Facultad Regional Córdoba".

GRUPO 6: Usuarios (Microservicio de Users)

- Disponemos de una Fake Api para simular las consultas que sean necesarias para los demas grupos , Link: https://my-json-server.typicode.com/405786MoroBenjamin/users-responses

- Proponemos un diagrama que representa como se relacionan los demas microservicios con el actual "OwnersPlots"

![image](https://github.com/user-attachments/assets/6f7ac240-50f7-4a9c-9650-a0303f810210)

- El anterior diagrama ofrece una representación visual de la arquitectura del sistema, enfocándose en las relaciones y dependencias entre los microservicios. Cada componente en el diagrama corresponde a un microservicio, y se representa mediante:

![image](https://github.com/user-attachments/assets/4244f39b-f327-4ac3-aab7-343afc8adc14)

OwnersPlots requiere consumir al microservicio Contacts ya que debemos conocer el contacto de nuestros propietarios y estos se almacenan en ese
microservicio , la información que desprende el microservicio de OwnersPlots es esencial para áreas como Multas y Boletas, ya que para la operación y cálculo de sus actividades requieren información precisa de los propietarios y las parcelas, por eso es así que los dos microservicios del área Boletas (ExpensesManager y ExpensesGeneration) y el microservicio de sanciones del área de Multas (Sanctions) consumen directamente a OwnersPlots. En la representación gráfica se observa a un microservicio llamado FileManager apartado, este se encarga de gestionar todos los archivos que se van a utilizar en el sistema, esta forma de graficado no significa que no tenga relación con ningún microservicio, todo lo contrario, se relaciona con la mayoría de los microservicios, lo graficamos de esta forma porque no conocemos con exactitud en qué momento y qué áreas van a consumir este microservicio.

- El script de la base de datos de nuestro microservicio se encuentra dentro de la carpeta /docs/db de este mismo repositorio

<img width="3449" alt="Base de datos (1)" src="https://github.com/user-attachments/assets/a0d46ae7-b48f-4411-9b02-c0929b00848e">



