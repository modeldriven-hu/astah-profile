package hu.modeldriven.astah.profile.ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import hu.modeldriven.core.eventbus.EventBus;

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
            JDialog dialog = new JDialog();
            JPanel contentPanel = new JPanel(new BorderLayout());
            dialog.setContentPane(contentPanel);

            ProfileTreePanel panel = new ProfileTreePanel(new EventBus());
            contentPanel.add(panel, BorderLayout.CENTER);

            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            dialog.setVisible(true);
        });
    }

}
