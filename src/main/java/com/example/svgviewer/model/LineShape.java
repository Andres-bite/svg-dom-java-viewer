package com.example.svgviewer.model;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public final class LineShape extends AbstractShape {
    private final double x1, y1, x2, y2;

    public LineShape(double x1, double y1, double x2, double y2, Style style) {
        super(style); this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }

    @Override public void draw(Graphics2D graphics) {
        if (style.stroke() != null) {
            graphics.setColor(style.stroke());
            graphics.setStroke(new java.awt.BasicStroke(style.strokeWidth()));
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }
}
