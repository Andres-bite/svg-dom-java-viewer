package com.example.svgviewer.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public final class CircleShape extends AbstractShape {
    private final double cx, cy, radius;
    public CircleShape(double cx, double cy, double radius, Style style) {
        super(style); this.cx = cx; this.cy = cy; this.radius = radius;
    }
    @Override public void draw(Graphics2D graphics) {
        paintShape(graphics, new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));
    }
}
