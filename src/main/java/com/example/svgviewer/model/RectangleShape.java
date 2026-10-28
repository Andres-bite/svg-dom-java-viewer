package com.example.svgviewer.model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public final class RectangleShape extends AbstractShape {
    private final double x, y, width, height;
    public RectangleShape(double x, double y, double width, double height, Style style) {
        super(style); this.x = x; this.y = y; this.width = width; this.height = height;
    }
    @Override public void draw(Graphics2D graphics) {
        paintShape(graphics, new Rectangle2D.Double(x, y, width, height));
    }
}
