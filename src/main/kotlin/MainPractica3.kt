
import java.nio.file.Path

fun main() {
    var videojuegos = VideojuegoRepository.leerVideojuegos()

    while (true) {
        println()
        println("===== CATALOGO DE VIDEOJUEGOS =====")
        println("1. Gestion CSV")
        println("2. Leer catalogo desde XML")
        println("3. Leer catalogo desde JSON")
        println("4. Conversiones entre formatos")
        println("5. Gestion fichero BIN")
        println("0. Salir")
        println("====================================")
        print("Elige una opcion: ")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> {

                videojuegos = gestionCSV(videojuegos)

            }

            2 -> {
                try {
                    val ruta = Path.of("Datos", "datos_ini", "videojuegos.xml")
                    val importados = leerVideojuegosXML(ruta)

                    println("\n--- Videojuegos vistos desde XML ---")

                    listarVideojuegos(importados)

                    val ids = videojuegos.map { it.id }.toMutableSet()

                    val nuevos = importados.filter { ids.add(it.id) }

                    videojuegos = videojuegos + nuevos

                    VideojuegoRepository.guardarVideojuegos(videojuegos)

                    println("Videojuegos nuevos: ${nuevos.size}")
                    println("Catalogo actualizado del CSV.")


                } catch (e: Exception) {
                    println("Error al leer el XML: ${e.message}")
                }
            }
            3 -> {
                try {
                    val ruta = Path.of("Datos", "datos_ini", "videojuegos.json")
                    val importados = leerVideojuegosJSON(ruta)


                    println("\n--- Videojuegos leidos del JSON ---")
                    listarVideojuegos(importados)



                    videojuegos = integrarCatalogo(videojuegos, importados)

                    VideojuegoRepository.guardarVideojuegos(videojuegos)

                    println("Cambiando en el CSV")

                } catch (e: Exception) {

                    println("Error al leer el JSON: ${e.message}")

                }
            }

            4 -> menuConversiones()

            5 -> menuVideojuegoBin()

            0 -> {

                println("Saliendo...")

                return
            }

            else -> {

                println("Esta opcion no  vale")

            }
        }
    }
}

fun gestionCSV(listaInicial: List<Videojuego>): List<Videojuego> {

    var lista = listaInicial

    while (true) {
        println()
        println("--- Gestion CSV ---")
        println("1. Listar todos los videojuegos")
        println("2. Buscar videojuego por ID")
        println("3. Poner nuevo videojuego")
        println("4. Modificar nota de un videojuego")
        println("5. Eliminar videojuego")
        println("0. Volver al menu")
        print("Elige una opcion: ")

        when (readlnOrNull()?.toIntOrNull()) {

            1 -> {

                listarVideojuegos(lista)

            }

            2 -> {

                print("Dame el ID: ")

                val id = readlnOrNull()?.toIntOrNull()

                if (id == null) {

                    println("No es correcto")
                } else {

                    buscarPorId(lista, id)
                }
            }

            3 -> {

                val anterior = lista

                lista = anadirVideojuego(lista)

                if (lista != anterior) {

                    VideojuegoRepository.guardarVideojuegos(lista)
                }
            }

            4 -> {


                print("Dame el ID: ")
                val id = readlnOrNull()?.toIntOrNull()

                if (id == null) {

                    println("El ID no es vale")
                } else if (lista.none { it.id == id }) {

                    println("No existe ningun juego con ese ID")

                } else {

                    print("Dame la nueva nota: ")

                    val nota = readlnOrNull()?.toDoubleOrNull()

                    if (nota == null || nota < 0.0 || nota > 10.0) {


                        println("La nota tiene que estar entre 0.0 y 10.0")
                    } else {
                        lista = modificarNota(lista, id, nota)
                        VideojuegoRepository.guardarVideojuegos(lista)
                    }
                }
            }

            5 -> {

                print("Dame el ID: ")
                val id = readlnOrNull()?.toIntOrNull()

                if (id == null) {

                    println("El ID no es vale")

                } else if (lista.none { it.id == id }) {

                    println("No existe un videojuego con ese ID")
                } else {


                    val anterior = lista
                    lista = eliminarVideojuego(lista, id)

                    if (lista != anterior) {

                        VideojuegoRepository.guardarVideojuegos(lista)

                    }
                }
            }

            0 -> {
                return lista
            }

            else -> {
                println("Opción no válida.")
            }
        }
    }
}
