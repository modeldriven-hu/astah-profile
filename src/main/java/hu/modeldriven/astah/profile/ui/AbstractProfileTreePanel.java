package hu.modeldriven.astah.profile.ui;

import javax.swing.*;
import java.awt.*;

public class AbstractProfileTreePanel extends JPanel {

    protected JToolBar toolBar;

    protected JButton newButton;
    protected JButton openButton;
    protected JButton saveButton;

    protected JButton updateButton;

    protected JSplitPane splitPane;

    protected JScrollPane treeScrollPane;
    protected JTree tree;

    protected JScrollPane tableScrollPane;
    protected JTable table;

    public AbstractProfileTreePanel() {
        super(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        toolBar = new JToolBar();
        toolBar.setFloatable(true);
        toolBar.setRollover(true);

        tree = new JTree();
        tree.setShowsRootHandles(true);
        treeScrollPane = new JScrollPane(tree);

        table = new JTable();
        tableScrollPane = new JScrollPane(table);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeScrollPane, tableScrollPane);

        newButton = new JButton("New");
        openButton = new JButton("Open");
        saveButton = new JButton("Save");
        updateButton = new JButton("Update model");

        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.add(updateButton);

        this.add(toolBar, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
    }

}
