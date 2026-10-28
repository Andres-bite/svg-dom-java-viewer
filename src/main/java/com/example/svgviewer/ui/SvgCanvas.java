package com.example.svgviewer.ui;

import com.example.svgviewer.model.SvgShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;

/** Panel que únicamente representa figuras ya procesadas. */
public final class SvgCanvas extends JPanel {
    private List<SvgShape> shapes = List.of();

    public SvgCanvas() { setBackground(Color.WHITE); }
    public void setShapes(List<SvgShape> shapes) { this.shapes = List.copyOf(shapes); repaint(); }

    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (SvgShape shape : shapes) shape.draw(g2);
        } finally { g2.dispose(); }
    }
}
