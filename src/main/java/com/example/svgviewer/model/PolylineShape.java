package com.example.svgviewer.model;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;

public final class PolylineShape extends AbstractShape {
    private final List<double[]> points;
    public PolylineShape(List<double[]> points, Style style) { super(style); this.points = List.copyOf(points); }
    @Override public void draw(Graphics2D graphics) {
        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);
        for (int i = 1; i < points.size(); i++) path.lineTo(points.get(i)[0], points.get(i)[1]);
        if (style.fill() != null) { path.closePath(); graphics.setColor(style.fill()); graphics.fill(path); }
        if (style.stroke() != null) {
            graphics.setColor(style.stroke());
            graphics.setStroke(new java.awt.BasicStroke(style.strokeWidth()));
            graphics.draw(path);
        }
    }
}
