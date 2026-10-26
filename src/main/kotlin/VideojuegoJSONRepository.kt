import java.nio.file.Path
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

fun leerVideojuegosJSON(ruta: Path): List<Videojuego> {

    val contenido = ruta.toFile().readText()

    return Json.decodeFromString<List<Videojuego>>(contenido)
}