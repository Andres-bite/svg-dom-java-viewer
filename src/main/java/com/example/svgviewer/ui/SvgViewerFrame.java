package com.example.svgviewer.ui;

import com.example.svgviewer.parser.SvgDocumentParser;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class SvgViewerFrame extends JFrame {
    private final SvgCanvas canvas = new SvgCanvas();
    private final JLabel status = new JLabel("Seleccione un archivo SVG para comenzar.");
    private final SvgDocumentParser parser = new SvgDocumentParser();

    public SvgViewerFrame() {
        super("Visor SVG — Java DOM y Java 2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationByPlatform(true);
        JButton openButton = new JButton("Abrir SVG");
        openButton.addActionListener(event -> chooseAndLoad());
        JToolBar toolbar = new JToolBar(); toolbar.setFloatable(false); toolbar.add(openButton);
        add(toolbar, BorderLayout.NORTH);
        add(new JScrollPane(canvas), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
    }

    private void chooseAndLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Documentos SVG (*.svg)", "svg"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) load(chooser.getSelectedFile());
    }

    private void load(File file) {
        try {
            var shapes = parser.parse(file);
            canvas.setShapes(shapes);
            status.setText("Cargado: " + file.getName() + " (" + shapes.size() + " figuras reconocidas)");
        } catch (Exception exception) {
            String message = "No se pudo procesar el SVG: " + exception.getMessage();
            status.setText(message);
            JOptionPane.showMessageDialog(this, message, "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }
}
