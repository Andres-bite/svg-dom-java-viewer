package com.example.svgviewer.model;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;

abstract class AbstractShape implements SvgShape {
    protected final Style style;

    protected AbstractShape(Style style) {
        this.style = style;
    }

    protected void paintShape(Graphics2D graphics, Shape shape) {
        if (style.fill() != null) {
            graphics.setColor(style.fill());
            graphics.fill(shape);
        }
        if (style.stroke() != null) {
            graphics.setColor(style.stroke());
            graphics.setStroke(new BasicStroke(style.strokeWidth()));
            graphics.draw(shape);
        }
    }
}
