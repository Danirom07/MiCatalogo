
import java.nio.file.Path

fun menuVideojuegoBin() {
    while (true) {
        println()
        println("===== CRUD fichero BIN =====")
        println("1. Importar informacion de un fichero de intercambio")
        println("2. Leer información del fichero binario")
        println("3. Añadir un registro nuevo")
        println("4. Modificar un registro existente (por ID)")
        println("5. Eliminar un registro existente (por ID)")
        println("0. Volver")
        print("Elige una opción: ")

        when (readlnOrNull()?.toIntOrNull()) {

            1 -> {

                val videojuegos = leerVideojuegosCSV(

                    Path.of("datos", "datos_ini", "videojuegos.csv") )

                VideojuegoBinRepository.importar(videojuegos)


            }

            2 -> {


                val videojuegos = VideojuegoBinRepository.leerTodos()


                if (videojuegos.isEmpty()) {

                    println("No hay videojuegos en el fichero binario")

                } else {

                    videojuegos.forEach { println(it) }

                }
            }

            3 -> anadirVideojuegoBin()

            4 -> modificarVideojuegoBin()

            5 -> eliminarVideojuegoBin()

            0 -> return


            else -> println("Opcion no valida")

        }

    }

}

private fun pedirEntero(mensaje: String): Int {


    while (true) {

        print(mensaje)

        val numero = readlnOrNull()?.toIntOrNull()

        if (numero != null) return numero

        println("Debes introducir un numero")
    }

}

private fun pedirDouble(mensaje: String): Double {

    while (true) {

        print(mensaje)

        val numero = readlnOrNull()?.replace(',', '.')?.toDoubleOrNull()

        if (numero != null) return numero

        println("Debes introducir un número valido")


    }
}

private fun anadirVideojuegoBin() {


    var id = pedirEntero("ID: ")


    while (VideojuegoBinRepository.existe(id)) {


        println("Ese ID ya existe. Introduce otro")
        id = pedirEntero("ID: ")


    }

    print("TItulo: ")

    val titulo = readlnOrNull().orEmpty()

    print("GEnero: ")

    val genero = readlnOrNull().orEmpty()

    val anio = pedirEntero("Año: ")

    val nota = pedirDouble("Nota: ")

    val videojuego = Videojuego(id, titulo, genero, anio, nota)

    if (VideojuegoBinRepository.anadir(videojuego)) {


        println("Videojuego PUESTO correctamente.")


    } else {

        println("No se ha podido poner el videojuego.")


    }
}

private fun modificarVideojuegoBin() {


    val id = pedirEntero("ID del videojuego que quieres modificar: ")

    val videojuego = VideojuegoBinRepository.leerTodos().find { it.id == id }



    if (videojuego == null) {

        println("No existe ningún videojuego con ese ID.")
        return

    }

    println("Videojuego encontrado: ${videojuego.titulo}")

    print("Nuevo titulo: ")


    val titulo = readlnOrNull().orEmpty()

    print("Nuevo genero: ")


    val genero = readlnOrNull().orEmpty()

    val anio = pedirEntero("Nuevo año: ")

    val nota = pedirDouble("Nueva nota: ")

    if (VideojuegoBinRepository.modificar(id, titulo, genero, anio, nota)) {


        println("Videojuego cambiar correctamente.")

    } else {

        println("No se ha podido cambiar el videojuego.")

    }
}

private fun eliminarVideojuegoBin() {


    val id = pedirEntero("ID del videojuego que quieres eliminar: ")

    val videojuego = VideojuegoBinRepository.leerTodos().find { it.id == id }

    if (videojuego == null) {


        println("No existe ningun videojuego con ese ID.")

        return
    }

    println("Videojuego encontrado: ${videojuego.titulo}")

    print("¿Seguro que quieres eliminarlo?: ")


    if (readlnOrNull()?.trim()?.lowercase() == "s") {

        if (VideojuegoBinRepository.eliminar(id)) {

            println("Videojuego eliminado correctamente.")

        }

    } else {

        println("Eliminación cancelada.")

    }
}
