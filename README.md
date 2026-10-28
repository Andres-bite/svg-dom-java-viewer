# Visor SVG con Java DOM y Java 2D

Aplicación de escritorio que abre documentos SVG, los interpreta con el DOM estándar de Java y dibuja un subconjunto de SVG con Swing/Java 2D. No utiliza bibliotecas externas de SVG.

Soporta `line`, `circle`, `polyline`, `rect`, `ellipse` y `text`, incluso si están dentro de grupos `g`.

## Requisitos y ejecución

Se requiere JDK 17 o posterior (probado con JDK 25).

```bash
javac -d out $(find src/main/java -name '*.java')
java -cp out com.example.svgviewer.SvgViewerApp
```

En la ventana, use **Abrir SVG** y seleccione `samples/test_svg_dom.svg`.

Para ejecutar la comprobación sin interfaz gráfica:

```bash
javac -d out $(find src/main/java src/test/java -name '*.java')
java -ea -cp out com.example.svgviewer.SvgDocumentParserTest
```

## Contenido

- `src/main/java`: aplicación, modelo, procesador DOM y componentes Swing.
- `samples/test_svg_dom.svg`: dos instancias de cada elemento solicitado.
- `docs/respuestas_practica.md`: respuestas a las actividades, diseño UML y evidencia de pruebas.

## Alcance

Los atributos de presentación se leen directamente de cada elemento: `fill`, `stroke`, `stroke-width`, `font-family` y `font-size`. Se admiten colores básicos y valores hexadecimales `#RGB` y `#RRGGBB`; los valores numéricos son simples, tal como pide la práctica.

