
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.nio.file.Path

@JacksonXmlRootElement(localName = "videojuegos")


data class VideojuegosXML(

    @param:JacksonXmlElementWrapper(useWrapping = false)

    @param:JacksonXmlProperty(localName = "videojuego")

    val videojuegos: List<Videojuego> = emptyList()


)

fun leerVideojuegosXML(ruta: Path): List<Videojuego> {

    val xmlMapper = XmlMapper()


    xmlMapper.registerKotlinModule()

    val catalogo = xmlMapper.readValue(

        ruta.toFile(),
        VideojuegosXML::class.java

    )

    return catalogo.videojuegos

}

