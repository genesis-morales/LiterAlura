# Catálogo de Libros y Autores

Aplicación de consola desarrollada en **Java** con **Spring Boot** y **Spring Data JPA**, que permite consultar, almacenar y analizar información de libros y autores utilizando una API externa y una base de datos relacional.

El proyecto implementa persistencia de datos, validaciones de entrada y consultas optimizadas mediante JPA.

---

## Funcionalidades

La aplicación cuenta con un menú interactivo que permite:

1. **Buscar libro por título**
   - Busca el libro en la base de datos.
   - Si no existe, lo consulta desde una API externa.
   - Guarda el libro y su autor evitando duplicados.

2. **Listar libros registrados**
   - Muestra todos los libros almacenados.
   - Ordenados alfabéticamente por título.

3. **Listar autores registrados**
   - Muestra todos los autores guardados.
   - Ordenados por nombre.

4. **Listar autores vivos por año**
   - Permite ingresar un año específico.
   - Muestra los autores que estaban vivos en ese período.
   - Incluye validación de entrada para evitar errores por datos inválidos.

5. **Listar libros por idioma**
   - Filtra los libros según su idioma (es, en, fr, pt).
   - Manejo de colecciones para idiomas múltiples.

6. **Top libros más descargados**
   - Muestra los libros con mayor número de descargas.
   - Ordenados de forma descendente.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- API REST
- Base de datos relacional (PostgreSQL)
- Streams y expresiones lambda

---

## Modelo de datos

### Libro
- id
- título
- número de descargas
- idiomas
- autores

### Autor
- id
- nombre
- año de nacimiento
- año de fallecimiento

Relaciones configuradas mediante anotaciones JPA.

---

## Validaciones y control de errores

- Prevención de registros duplicados.
- Validación de entradas numéricas del usuario.
- Manejo de valores nulos provenientes de la API.
- Consultas eficientes mediante repositorios JPA.

---

## Ejecución del proyecto

1. Clonar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA.
3. Configurar la base de datos si es necesario.
4. Ejecutar la clase principal del proyecto.
5. Interactuar con el menú desde la consola.

---

## Observaciones

- La lógica de impresión se maneja fuera de los modelos para evitar sobrecargar `toString()`.
- El proyecto sigue buenas prácticas de separación de responsabilidades.
- Pensado como ejercicio práctico de persistencia y consumo de APIs.

---

## Autor

Proyecto desarrollado como parte del proceso de aprendizaje en Java y Spring Boot.

