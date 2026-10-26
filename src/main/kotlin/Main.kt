import java.nio.file.Path
import java.nio.file.Files
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

fun main() {
    rutas()
}

fun rutas() {
    val datosIni = Path.of("datos", "datos_ini")

    val datosDir = Path.of("datos")
    if (!datosIni.exists()) {
        println("Creando ruta...")

        Files.createDirectories(datosIni)


    } else {
        println("Creando ruta...")

    }


    val datosFin = Path.of("datos", "datos_fin")

    if (!datosFin.exists()) {
        println("Creando ruta...")

        Files.createDirectories(datosFin)

    } else {
        println("Creando ruta...")


    }

    try {
        Files.walk(datosDir).use { stream ->
            stream.sorted().forEach { path ->
                val profundidad = path.nameCount - datosDir.nameCount
                val indentacion = "\t".repeat(profundidad)
                val prefijo = if (Files.isDirectory(path)) "[DIR]" else "[FICHERO]"

                println("$indentacion$prefijo ${path.fileName}")
            }
        }
    } catch (e: Exception) {
        println("Error al recorrer los directorios: ${e.message}")
    }

}