
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Path

object VideojuegoBinRepository {

    private val ruta = Path.of("datos_ini", "videojuegos.dat")

    private const val TAM_TITULO = 40


    private const val TAM_GENERO = 20

    private const val TAM_REGISTRO = 76


    private fun escribirTexto(archivo: RandomAccessFile, texto: String, longitud: Int) {


        val bytes = texto.take(longitud).toByteArray(Charsets.US_ASCII)

        val relleno = ByteArray(longitud) { ' '.code.toByte() }

        System.arraycopy(bytes, 0, relleno, 0, minOf(bytes.size, longitud))

        archivo.write(relleno)
    }




    private fun leerTexto(archivo: RandomAccessFile, longitud: Int): String {


        val bytes = ByteArray(longitud)
        archivo.readFully(bytes)

        return String(bytes, Charsets.US_ASCII).trim()


    }


    private fun escribirVideojuego(archivo: RandomAccessFile, v: Videojuego) {


        archivo.writeInt(v.id)

        escribirTexto(archivo, v.titulo, TAM_TITULO)

        escribirTexto(archivo, v.genero, TAM_GENERO)


        archivo.writeInt(v.anio)
        archivo.writeDouble(v.nota)
    }



    private fun leerVideojuego(archivo: RandomAccessFile): Videojuego {

        val id = archivo.readInt()

        val titulo = leerTexto(archivo, TAM_TITULO)

        val genero = leerTexto(archivo, TAM_GENERO)
        val anio = archivo.readInt()
        val nota = archivo.readDouble()

        return Videojuego(id, titulo, genero, anio, nota)



    }



    fun leerTodos(): List<Videojuego> {


        if (!Files.exists(ruta)) return emptyList()

        val lista = mutableListOf<Videojuego>()

        RandomAccessFile(ruta.toFile(), "r").use { archivo ->


            while (archivo.filePointer < archivo.length()) {
                lista.add(leerVideojuego(archivo))

            }
        }

        return lista


    }

    fun importar(lista: List<Videojuego>) {


        Files.createDirectories(ruta.parent)


        RandomAccessFile(ruta.toFile(), "rw").use { archivo ->
            archivo.setLength(0)

            for (v in lista) {


                escribirVideojuego(archivo, v)

            }
        }

        println("Videojuegos importados: ${lista.size}")

    }

    fun anadir(v: Videojuego): Boolean {

        val lista = leerTodos()

        if (lista.any { it.id == v.id }) return false



        RandomAccessFile(ruta.toFile(), "rw").use { archivo ->


            archivo.seek(archivo.length())
            escribirVideojuego(archivo, v)


        }

        return true
    }

    fun modificar(id: Int, titulo: String, genero: String, anio: Int, nota: Double): Boolean {


        RandomAccessFile(ruta.toFile(), "rw").use { archivo ->

            while (archivo.filePointer < archivo.length()) {

                val posicion = archivo.filePointer
                val v = leerVideojuego(archivo)

                if (v.id == id) {


                    archivo.seek(posicion)
                    escribirVideojuego(
                        archivo,
                        Videojuego(id, titulo, genero, anio, nota)
                    )


                    return true
                }

            }

        }

        return false
    }

    fun eliminar(id: Int): Boolean {


        val lista = leerTodos()

        val nuevaLista = lista.filter { it.id != id }

        if (nuevaLista.size == lista.size) return false

        importar(nuevaLista)


        return true

    }

    fun existe(id: Int): Boolean {

        return leerTodos().any { it.id == id }

    }

    fun tamanoRegistro(): Int {

        return TAM_REGISTRO

    }
}
