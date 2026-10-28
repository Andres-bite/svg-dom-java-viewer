package com.example.svgviewer;

import com.example.svgviewer.ui.SvgViewerFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class SvgViewerApp {
    private SvgViewerApp() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) { }
            new SvgViewerFrame().setVisible(true);
        });
    }
}
