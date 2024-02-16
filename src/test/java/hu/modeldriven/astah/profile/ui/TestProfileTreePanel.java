package hu.modeldriven.astah.profile.ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

public class TestProfileTreePanel {

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        SwingUtilities.invokeAndWait(() -> {
            try {
                UIManager.put("TitlePane.menuBarEmbedded", false);
                FlatLightFlatIJTheme.setup();
                FlatLaf.updateUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            JPanel contentPanel = new JPanel(new BorderLayout());
            frame.setContentPane(contentPanel);

            ProfileTreePanel panel = new ProfileTreePanel(new EventBus(), new SimpleUMLModel());
            contentPanel.add(panel, BorderLayout.CENTER);

            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.setVisible(true);
        });
    }

}
