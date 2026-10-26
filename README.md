# Catálogo de Videojuegos

Este proyecto es un programa de consola desarrollado en Kotlin que nos permite gestionar un catálogo de videojuegos. El programa trabaja con archivos en formato CSV, XML y JSON, y nos permite convertir la información entre estos formatos. Tambien, gestiona los videojuegos en un archivo binario con acceso aleatorio.

El sistema permite importar videojuegos desde un archivo CSV, leer los datos almacenados en el archivo binario, añadir nuevos videojuegos, modificar los existentes y eliminar registros.

## 1. Estructura de datos

### Data Class

Para almacenar la información de cada videojuego se utiliza la clase `Videojuego`, que tiene los siguientes atributos:

```kotlin

data class Videojuego(

val id: Int,

val titulo: String,

val genero: String,

val anio: Int,

val nota: Double

)

```

Cada atributo guarda una parte específica de la información: el identificador del videojuego, su título, el género, el año de lanzamiento y la nota.

### Estructura del registro binario

En el archivo binario, cada videojuego ocupa **76 bytes**. He decidido usar un tamaño fijo para facilitar la localización de los registros y permitir modificaciones directas sin tener que reescribir todo el archivo.

- **ID:** tipo Int, ocupa 4 bytes.

- **Título:** tipo String, ocupa 40 bytes.

- **Género:** tipo String, ocupa 20 bytes.

- **Año:** tipo Int, ocupa 4 bytes.

- **Nota:** tipo Double, ocupa 8 bytes.

El total es:

4 + 40 + 20 + 4 + 8 = 76 bytes

Los campos de texto tienen un tamaño fijo. Si el texto es más corto que el espacio reservado, se rellena con espacios. Si es más largo, se trunca para que no sobrepase los 40 bytes para el título y los 20 bytes para el género.

## 2. Instrucciones de ejecución

1. Abre el proyecto en IntelliJ IDEA.

2. Espera a que Gradle termine de sincronizar el proyecto.

3. Asegúrate de que el archivo CSV inicial está en la ruta que el programa espera.

4. Ejecuta el archivo MainPractica3.kt.

5. Usa el menú principal para acceder a las diferentes opciones del catálogo.

En la opción de gestión del archivo binario puedes importar los datos del CSV, ver los videojuegos almacenados, añadir nuevos registros, modificar videojuegos por su ID y eliminarlos.

El archivo binario se guarda en la ruta datos_ini/videojuegos.dat. Se crea automáticamente cuando se importa información o se añade un nuevo videojuego.

## 3. Decisiones de diseño

He elegido el formato CSV como archivo inicial porque es fácil de leer y manipular. También he trabajado con XML y JSON para practicar la lectura y conversión entre formatos distintos.

Para el archivo binario he decidido usar registros de tamaño fijo de 76 bytes. Esto permite calcular la posición exacta de cada registro y facilita el acceso aleatorio a los datos.

He asignado 40 bytes para el título y 20 bytes para el género, para que haya suficiente espacio para los textos que uso en el catálogo.

Para modificar o eliminar un videojuego, uso su ID, ya que permite identificar claramente el registro que se quiere cambiar. Además, al añadir nuevos videojuegos, compruebo que el ID no esté ya en uso para evitar duplicados.

Para escribir y leer datos en el archivo binario, uso RandomAccessFile, ya que permite acceder a cualquier posición del archivo para leer o escribir información sin tener que recorrer todo el contenido.
