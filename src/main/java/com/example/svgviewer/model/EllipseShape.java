package com.example.svgviewer.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public final class EllipseShape extends AbstractShape {
    private final double cx, cy, rx, ry;
    public EllipseShape(double cx, double cy, double rx, double ry, Style style) {
        super(style); this.cx = cx; this.cy = cy; this.rx = rx; this.ry = ry;
    }
    @Override public void draw(Graphics2D graphics) {
        paintShape(graphics, new Ellipse2D.Double(cx - rx, cy - ry, rx * 2, ry * 2));
    }
}
