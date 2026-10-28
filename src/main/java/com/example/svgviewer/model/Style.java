package com.example.svgviewer.model;

import java.awt.Color;

/** Propiedades de presentación comunes al subconjunto SVG soportado. */
public record Style(Color fill, Color stroke, float strokeWidth, String fontFamily, float fontSize) {
    public static final Style DEFAULT = new Style(Color.BLACK, null, 1f, "SansSerif", 16f);
}
