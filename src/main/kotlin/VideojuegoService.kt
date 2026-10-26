
fun listarVideojuegos(lista: List<Videojuego>) {

    if (lista.isEmpty()) {

        println("No hay juegos")

    } else {

        for (videojuego in lista) {

            println(

                "ID: ${videojuego.id} | " +
                        "Título: ${videojuego.titulo} | " +
                        "Género: ${videojuego.genero} | " +
                        "Año: ${videojuego.anio} | " +
                        "Nota: ${videojuego.nota}"


            )
        }
    }
}

fun buscarPorId(lista: List<Videojuego>, id: Int) {

    val videojuego = lista.find { it.id == id }

    if (videojuego == null) {

        println("No existe un juego con ese ID.")

    } else {
        listarVideojuegos(listOf(videojuego))

    }
}

fun anadirVideojuego(lista: List<Videojuego>): List<Videojuego> {

    print("Titulo: ")

    val titulo = readlnOrNull()?.trim()

    if (titulo.isNullOrBlank()) {

        println("El titulo no puede estar vacio.")
        return lista


    }

    print("Genero: ")

    val genero = readlnOrNull()?.trim()

    if (genero.isNullOrBlank()) {

        println("El genero no puede estar vacio.")
        return lista

    }

    print("Año: ")

    val anio = readlnOrNull()?.toIntOrNull()


    if (anio == null || anio <= 0) {

        println("El año no es valido.")
        return lista

    }

    print("Nota (0.0 - 10.0): ")

    val nota = readlnOrNull()?.toDoubleOrNull()

    if (nota == null || nota < 0.0 || nota > 10.0) {

        println("La nota debe estar entre 0.0 y 10.0.")
        return lista

    }

    val nuevoId = (lista.maxOfOrNull { it.id } ?: 0) + 1

    val videojuego = Videojuego(
        nuevoId,
        titulo,
        genero,
        anio,
        nota
    )

    println("Videojuego puesto correctamente.")
    return lista + videojuego

}

fun modificarNota(

    lista: List<Videojuego>,
    id: Int,
    nuevaNota: Double

): List<Videojuego> {


    if (nuevaNota < 0.0 || nuevaNota > 10.0) {
        println("La nota debe estar entre 0.0 y 10.0.")

        return lista


    }

    if (lista.none { it.id == id }) {

        println("No existe ningun videojuego con ese ID.")

        return lista
    }

    val listaNueva = lista.map { videojuego ->

        if (videojuego.id == id) {
            videojuego.copy(nota = nuevaNota)


        } else {

            videojuego

        }
    }

    println("Nota modificada correctamente.")

    return listaNueva
}

fun eliminarVideojuego(

    lista: List<Videojuego>,
    id: Int

): List<Videojuego> {


    if (lista.none { it.id == id }) {
        println("No existe ningun videojuego con ese ID.")


        return lista
    }

    print("¿Seguro que quieres eliminarlo?: ")


    val confirmacion = readlnOrNull()?.trim()?.lowercase()

    if (confirmacion == "s") {

        println("Videojuego eliminado correctamente.")

        return lista.filter { it.id != id }
    }

    println("Eliminacion cancelada.")
    return lista
}
fun integrarCatalogo(

    actual: List<Videojuego>,
    importados: List<Videojuego>


): List<Videojuego> {


    val ids = actual.map { it.id }.toMutableSet()

    val nuevos = importados.filter { ids.add(it.id) }


    println("Videojuegos nuevos integrados: ${nuevos.size}")


    return actual + nuevos
}