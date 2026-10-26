
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.nio.file.Files
import java.nio.file.Path

object VideojuegoRepository {

    private val ruta = Path.of("datos_ini", "videojuegos.csv")

    fun leerVideojuegos(): List<Videojuego> {

        if (!Files.exists(ruta)) {
            return emptyList()

        }

        return csvReader { delimiter = ';' }

            .readAll(ruta.toFile())
            .mapNotNull { fila ->

                try {
                    Videojuego(
                        id = fila[0].toInt(),
                        titulo = fila[1],
                        genero = fila[2],
                        anio = fila[3].toInt(),
                        nota = fila[4].toDouble()
                    )


                } catch (e: Exception) {

                    null

                }

            }
    }

    fun guardarVideojuegos(lista: List<Videojuego>) {

        Files.createDirectories(ruta.parent)

        val filas = lista.map { videojuego ->

            listOf(

                videojuego.id.toString(),
                videojuego.titulo,
                videojuego.genero,
                videojuego.anio.toString(),
                videojuego.nota.toString()

            )
        }

        csvWriter { delimiter = ';' }

            .writeAll(filas, ruta.toFile())
    }
}
