package com.example.svgviewer.util;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import org.w3c.dom.Element;

/** Conversión reutilizable de atributos SVG simples. */
public final class SvgValues {
    private static final Map<String, Color> BASIC_COLORS = Map.ofEntries(
            Map.entry("black", Color.BLACK), Map.entry("white", Color.WHITE),
            Map.entry("red", Color.RED), Map.entry("green", Color.GREEN),
            Map.entry("blue", Color.BLUE), Map.entry("yellow", Color.YELLOW),
            Map.entry("orange", Color.ORANGE), Map.entry("pink", Color.PINK),
            Map.entry("gray", Color.GRAY), Map.entry("grey", Color.GRAY),
            Map.entry("lightblue", new Color(173, 216, 230)),
            Map.entry("lightgreen", new Color(144, 238, 144)),
            Map.entry("navy", new Color(0, 0, 128)),
            Map.entry("purple", new Color(128, 0, 128)));

    private SvgValues() { }

    public static double requiredDouble(Element element, String name) {
        if (!element.hasAttribute(name)) throw error(element, "falta el atributo '" + name + "'");
        return doubleValue(element, name, element.getAttribute(name));
    }

    public static double optionalDouble(Element element, String name, double defaultValue) {
        return element.hasAttribute(name) ? doubleValue(element, name, element.getAttribute(name)) : defaultValue;
    }

    public static Color color(Element element, String name, Color defaultValue) {
        if (!element.hasAttribute(name)) return defaultValue;
        String value = element.getAttribute(name).trim().toLowerCase(Locale.ROOT);
        if (value.equals("none")) return null;
        Color named = BASIC_COLORS.get(value);
        if (named != null) return named;
        if (value.matches("#[0-9a-f]{6}")) return Color.decode(value);
        if (value.matches("#[0-9a-f]{3}")) {
            return new Color(Integer.parseInt("" + value.charAt(1) + value.charAt(1), 16),
                    Integer.parseInt("" + value.charAt(2) + value.charAt(2), 16),
                    Integer.parseInt("" + value.charAt(3) + value.charAt(3), 16));
        }
        throw error(element, "color no soportado para '" + name + "': " + value);
    }

    private static double doubleValue(Element element, String name, String text) {
        try {
            double value = Double.parseDouble(text.trim());
            if (!Double.isFinite(value)) throw new NumberFormatException();
            return value;
        } catch (NumberFormatException exception) {
            throw error(element, "valor numérico inválido para '" + name + "': " + text);
        }
    }

    private static IllegalArgumentException error(Element element, String message) {
        return new IllegalArgumentException("Elemento <" + element.getTagName() + ">: " + message);
    }
}
