package com.example.svgviewer.model;

import java.awt.Font;
import java.awt.Graphics2D;

public final class TextShape extends AbstractShape {
    private final double x, y;
    private final String text;
    public TextShape(double x, double y, String text, Style style) {
        super(style); this.x = x; this.y = y; this.text = text;
    }
    @Override public void draw(Graphics2D graphics) {
        if (style.fill() != null) {
            graphics.setColor(style.fill());
            graphics.setFont(new Font(style.fontFamily(), Font.PLAIN, Math.round(style.fontSize())));
            graphics.drawString(text, (float) x, (float) y);
        }
    }
}
