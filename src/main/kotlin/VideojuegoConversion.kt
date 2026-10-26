
import java.nio.file.Files
import java.nio.file.Path
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun leerVideojuegosCSV(ruta: Path): List<Videojuego> {

    return csvReader { delimiter = ';' }
        .readAll(ruta.toFile())

        .map { fila ->
            Videojuego(
                fila[0].toInt(),
                fila[1],
                fila[2],
                fila[3].toInt(),
                fila[4].toDouble()
            )

        }
}

fun escribirVideojuegosCSV(ruta: Path, lista: List<Videojuego>) {
    Files.createDirectories(ruta.parent)

    val filas = lista.map {

        listOf(
            it.id.toString(),
            it.titulo,
            it.genero,
            it.anio.toString(),
            it.nota.toString()

        )
    }

    csvWriter { delimiter = ';' }.writeAll(filas, ruta.toFile())
}

fun escribirVideojuegosJSON(ruta: Path, lista: List<Videojuego>) {

    Files.createDirectories(ruta.parent)
    ruta.toFile().writeText(Json.encodeToString(lista))

}

fun escribirVideojuegosXML(ruta: Path, lista: List<Videojuego>) {

    Files.createDirectories(ruta.parent)

    val xmlMapper = XmlMapper()
    xmlMapper.registerKotlinModule()

    xmlMapper.writeValue(ruta.toFile(), VideojuegosXML(lista))

}

fun convertir(

    origen: Path,
    destino: Path,
    leer: (Path) -> List<Videojuego>,
    escribir: (Path, List<Videojuego>) -> Unit

) {
    try {
        if (!Files.exists(origen)) {

            println("No existe el fichero : $origen")

            return
        }

        val videojuegos = leer(origen)

        escribir(destino, videojuegos)

        println("Fichero hecho: $destino")

        println("Convertidos: ${videojuegos.size}")


    } catch (e: Exception) {

        println("Error durante la conversión: ${e.message}")

    }

}

fun menuConversiones() {

    while (true) {
        println()
        println("--- Conversiones ---")
        println("1. CSV  -> JSON")
        println("2. CSV  -> XML")
        println("3. JSON -> CSV")
        println("4. JSON -> XML")
        println("5. XML  -> CSV")
        println("6. XML  -> JSON")
        println("0. Volver")
        print("Elige una opción: ")

        val opcion = readlnOrNull()?.toIntOrNull()

        val csv = Path.of("Datos", "datos_ini", "videojuegos.csv")

        val json = Path.of("Datos", "datos_ini", "videojuegos.json")

        val xml = Path.of("Datos", "datos_ini", "videojuegos.xml")

        val salida = Path.of("Datos", "datos_fin")

        when (opcion) {

            1 -> convertir(
                csv, salida.resolve("videojuegos.json"),
                ::leerVideojuegosCSV, ::escribirVideojuegosJSON

            )

            2 -> convertir(

                csv, salida.resolve("videojuegos.xml"),
                ::leerVideojuegosCSV, ::escribirVideojuegosXML

            )

            3 -> convertir(

                json, salida.resolve("videojuegos.csv"),
                ::leerVideojuegosJSON, ::escribirVideojuegosCSV

            )

            4 -> convertir(

                json, salida.resolve("videojuegos.xml"),
                ::leerVideojuegosJSON, ::escribirVideojuegosXML

            )

            5 -> convertir(

                xml, salida.resolve("videojuegos.csv"),
                ::leerVideojuegosXML, ::escribirVideojuegosCSV

            )

            6 -> convertir(

                xml, salida.resolve("videojuegos.json"),
                ::leerVideojuegosXML, ::escribirVideojuegosJSON

            )

            0 -> return

            else -> println("Opcion no valida.")

        }
    }
}
