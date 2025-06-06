# ClimaStock: Optimizador de Rotación de Stock por Clima Local

Este proyecto es una aplicación web que recomienda a las tiendas físicas de Inditex qué productos destacar según el clima local de su ciudad. El objetivo es optimizar la rotación de stock adaptando la oferta en tienda a las condiciones meteorológicas previstas.

![image](https://github.com/user-attachments/assets/c3855ec2-1077-48a9-8f2f-9c544245b4d9)

## ¿Qué ofrece ClimaStock?

- Gestión visual y sencilla de catálogo de productos y tiendas físicas.
- Consulta del clima en tiempo real para cada tienda (integración con Open-Meteo).
- Recomendaciones automáticas de productos según la previsión meteorológica (temperatura y lluvia).
- Optimización de la exposición de prendas y mejora de la experiencia en tienda.
- Carga y edición de productos con imágenes.
- Filtros por género y sección para recomendaciones más precisas.
- Interfaz web moderna y responsive con Thymeleaf y CSS personalizado.
- API REST para integración con otros sistemas.

## ¿Cómo funciona?

1. **Carga de Catálogo**  
   El usuario puede añadir y editar productos, cada uno con categoría, estación recomendada, género, sección, si es impermeable y una imagen asociada.
   ![image](https://github.com/user-attachments/assets/0ebaa23e-3ffb-42be-a811-4466111009c5)


2. **Gestión de Tiendas**  
   Se pueden crear, editar y eliminar tiendas físicas, cada una con su localización (latitud y longitud).
   ![image](https://github.com/user-attachments/assets/e1ed4aa8-8dab-4538-a1a1-850e0f2069fb)


4. **Obtención de Datos Meteorológicos**  
   El sistema consulta la API de Open-Meteo para obtener la previsión del clima (temperatura máxima y lluvia) en cada ciudad donde hay una tienda.
   

5. **Lógica de Recomendación**  
   Se aplican reglas automáticas:
    - Si la temperatura prevista es mayor a 25°C, se recomiendan productos de verano.
    - Si se prevé lluvia, se priorizan prendas impermeables.
    - Las reglas pueden adaptarse según las necesidades del negocio.
![image](https://github.com/user-attachments/assets/f5736d37-f812-4f0d-a82c-11154e8716f7)

![image](https://github.com/user-attachments/assets/757138d2-b4a7-4723-81ef-07d532ba939b)


   
6. **Visualización de Recomendaciones**  
   Para cada tienda y día, el sistema genera una lista de productos recomendados en función del clima local, con filtros por género y sección.

   ![image](https://github.com/user-attachments/assets/507f6325-a1bf-4648-b09a-9bd994eea7e8)


8. **Exposición de Resultados**
    - **API REST**: Endpoint `/recomendaciones/{tiendaId}` que devuelve las recomendaciones en JSON para una tienda concreta.
    - **Interfaz Web**: Página `/recomendaciones` para seleccionar tienda, día y filtros, y ver los productos sugeridos de forma visual.
 
## Arquitectura y Tecnologías

- **Java 17+**
- **Spring Boot** (backend y lógica de negocio)
- **Thymeleaf** (plantillas HTML)
- **CSS personalizado** (diseño visual y responsive)
- **API meteorológica**: Open-Meteo
- **Docker con MySQL** (persistencia de datos)
- **Gestión de imágenes**: subida y visualización de imágenes de productos

### Estructura de la aplicación
- **Controladores**:
    - `RecomendacionController` (`@RestController`):  
      Endpoint `/recomendaciones/{tiendaId}` para recomendaciones en JSON.
    - `RecomendacionesWebController` (`@Controller`):  
      Página `/recomendaciones` para recomendaciones visuales.
    - `ProductoController` y `TiendaController`: gestión CRUD de productos y tiendas.
- **Servicios**:
    - Servicio de recomendación (reglas de negocio).
    - Servicio de integración meteorológica.
- **Modelo de Datos**:
    - Productos, tiendas, categorías, estaciones, imágenes, etc.

## Ejemplo de Uso

1. **Gestionar catálogo y tiendas**:  
   Añade productos y tiendas desde la interfaz web.

2. **Consultar recomendaciones vía API**:
   ```
   GET /recomendaciones/{tiendaId}
   ```
   Respuesta:
   ```json
   [
     {
       "producto": "Camiseta básica",
       "categoria": "Verano",
       "motivo": "Temperatura alta"
     },
     ...
   ]
   ```

3. **Consultar recomendaciones vía Web**:  
   Accede a `/recomendaciones`, selecciona la tienda, el día y los filtros, y visualiza los productos sugeridos.

## Instalación y Ejecución

1. Clona el repositorio.
2. Configura las credenciales de la API meteorológica si es necesario.
3. Ejecuta la aplicación con:
   ```
   ./mvnw spring-boot:run
   ```
4. Accede a la interfaz web o consume la API REST.

## Personalización

- Puedes modificar las reglas de recomendación en el servicio correspondiente.
- Es posible cambiar la fuente de datos meteorológicos adaptando el servicio de integración.

## Autor

Nabid HZ
---
