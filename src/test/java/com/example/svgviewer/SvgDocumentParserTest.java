package com.example.svgviewer;

import com.example.svgviewer.parser.SvgDocumentParser;
import java.io.File;

/** Prueba ejecutable sin bibliotecas externas. */
public final class SvgDocumentParserTest {
    public static void main(String[] args) throws Exception {
        var parser = new SvgDocumentParser();
        var shapes = parser.parse(new File("samples/test_svg_dom.svg"));
        if (shapes.size() != 12) throw new AssertionError("Se esperaban 12 figuras y se obtuvieron " + shapes.size());
        try {
            parser.parse(new File("samples/no-existe.svg"));
            throw new AssertionError("Debía rechazar un archivo inexistente.");
        } catch (IllegalArgumentException expected) { }
        System.out.println("OK: se interpretaron 12 figuras, incluidos elementos dentro de <g>.");
    }
}
