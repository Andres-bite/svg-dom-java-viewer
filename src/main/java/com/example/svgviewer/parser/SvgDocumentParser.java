package com.example.svgviewer.parser;

import com.example.svgviewer.model.*;
import com.example.svgviewer.util.SvgValues;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Lee el subconjunto SVG requerido y conserva el orden de los nodos del DOM. */
public final class SvgDocumentParser {
    public List<SvgShape> parse(File file) throws Exception {
        if (file == null || !file.isFile() || !file.canRead()) {
            throw new IllegalArgumentException("El archivo no existe o no puede leerse.");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        Element root = document.getDocumentElement();
        if (!"svg".equals(localName(root))) throw new IllegalArgumentException("La raíz del documento debe ser <svg>.");
        List<SvgShape> shapes = new ArrayList<>();
        visitChildren(root, shapes);
        return List.copyOf(shapes);
    }

    private void visitChildren(Element parent, List<SvgShape> shapes) {
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element = (Element) node;
            if ("g".equals(localName(element))) visitChildren(element, shapes);
            else addShape(element, shapes);
        }
    }

    private void addShape(Element e, List<SvgShape> shapes) {
        Style style = styleOf(e);
        switch (localName(e)) {
            case "line" -> shapes.add(new LineShape(SvgValues.requiredDouble(e, "x1"), SvgValues.requiredDouble(e, "y1"),
                    SvgValues.requiredDouble(e, "x2"), SvgValues.requiredDouble(e, "y2"), style));
            case "circle" -> shapes.add(new CircleShape(SvgValues.requiredDouble(e, "cx"), SvgValues.requiredDouble(e, "cy"),
                    positive(e, "r"), style));
            case "rect" -> shapes.add(new RectangleShape(SvgValues.optionalDouble(e, "x", 0), SvgValues.optionalDouble(e, "y", 0),
                    positive(e, "width"), positive(e, "height"), style));
            case "ellipse" -> shapes.add(new EllipseShape(SvgValues.requiredDouble(e, "cx"), SvgValues.requiredDouble(e, "cy"),
                    positive(e, "rx"), positive(e, "ry"), style));
            case "polyline" -> shapes.add(new PolylineShape(points(e), style));
            case "text" -> shapes.add(new TextShape(SvgValues.requiredDouble(e, "x"), SvgValues.requiredDouble(e, "y"),
                    e.getTextContent().trim(), style));
            default -> { /* Fuera del alcance: se ignora sin alterar los elementos reconocidos. */ }
        }
    }

    private Style styleOf(Element e) {
        return new Style(SvgValues.color(e, "fill", Style.DEFAULT.fill()),
                SvgValues.color(e, "stroke", Style.DEFAULT.stroke()),
                (float) positiveOrDefault(e, "stroke-width", Style.DEFAULT.strokeWidth()),
                e.hasAttribute("font-family") ? e.getAttribute("font-family") : Style.DEFAULT.fontFamily(),
                (float) positiveOrDefault(e, "font-size", Style.DEFAULT.fontSize()));
    }

    private static double positive(Element e, String name) {
        double value = SvgValues.requiredDouble(e, name);
        if (value < 0) throw new IllegalArgumentException("Elemento <" + e.getTagName() + ">: '" + name + "' no puede ser negativo.");
        return value;
    }
    private static double positiveOrDefault(Element e, String name, double fallback) {
        double value = SvgValues.optionalDouble(e, name, fallback);
        if (value <= 0) throw new IllegalArgumentException("Elemento <" + e.getTagName() + ">: '" + name + "' debe ser mayor que cero.");
        return value;
    }
    private static List<double[]> points(Element e) {
        if (!e.hasAttribute("points")) throw new IllegalArgumentException("Elemento <polyline>: falta el atributo 'points'.");
        String[] values = e.getAttribute("points").trim().split("[\\s,]+");
        if (values.length < 4 || values.length % 2 != 0) throw new IllegalArgumentException("Elemento <polyline>: 'points' debe contener pares x,y.");
        List<double[]> result = new ArrayList<>();
        for (int i = 0; i < values.length; i += 2) {
            try { result.add(new double[] { Double.parseDouble(values[i]), Double.parseDouble(values[i + 1]) }); }
            catch (NumberFormatException exception) { throw new IllegalArgumentException("Elemento <polyline>: coordenada inválida.", exception); }
        }
        return result;
    }
    private static String localName(Element e) { return e.getLocalName() == null ? e.getTagName() : e.getLocalName(); }
}
