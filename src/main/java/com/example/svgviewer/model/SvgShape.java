package com.example.svgviewer.model;

import java.awt.Graphics2D;

/** Figura ya interpretada, lista para dibujarse. */
public interface SvgShape {
    void draw(Graphics2D graphics);
}
