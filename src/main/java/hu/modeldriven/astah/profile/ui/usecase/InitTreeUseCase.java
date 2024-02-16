package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeModel;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.plaf.basic.BasicTreeUI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class InitTreeUseCase implements EventHandler<ProfileAvailableEvent> {

    private final JTree tree;

    public InitTreeUseCase(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void handleEvent(ProfileAvailableEvent event) {
        ProfileTreeModel treeModel = new ProfileTreeModel(event.profile());
        tree.setModel(treeModel);
        treeModel.addTreeModelListener(new TreeModelListener() {

            @Override
            public void treeNodesChanged(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeNodesInserted(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeStructureChanged(TreeModelEvent treeModelEvent) {
                tree.expandPath(treeModelEvent.getTreePath());
                invalidateLayoutCache();
            }
        });
    }

    /**
     * JTree caches the size of the labels, because someone had a "brilliant" idea.
     * Obviously there is no standard way to clear this cache, resulting when the
     * underlying data structure of a tree item is changed (like a name of a stereotype)
     * then the label boundaries will not change. Because...caching...
     * <p>
     * Rewriting the preferred size or anything does not work, because it is not
     * taken into account.
     * <p>
     * The solution is therefore to clean the layout cache with the following hack.
     * <p>
     * Some facts:
     * <p>
     * - BasicTreeUI keeps a cache of node sizes
     * - there's no public api to force it to revalidate that cache
     * - node size requirements are assumed to depend exclusively on data, not on visual state, that is change of selection state will not trigger any internal update
     * - an aside: setting size inside the renderer/editor has no effect: whatever you do, the ui will change it as it deems appropriate
     * - On the whole, there is no way to implement your requirement without going dirty. Basically, you have to listen to selection changes - because the renderer has different size requirement in selected vs. not selected - and then do your best to invalidate the ui's internal cache. Basically two options:
     * <p>
     * - use reflection to access protected methods of the ui
     * - fake model events that would lead to an internal re-calculation of the cache
     * <p>
     * <p>
     * https://stackoverflow.com/questions/22330502/change-jtree-row-height-resizing-behavior-when-rendering
     */
    private void invalidateLayoutCache() {

        if (tree.getUI() instanceof BasicTreeUI) {

            BasicTreeUI ui = (BasicTreeUI) tree.getUI();
            try {
                Method method = BasicTreeUI.class.getDeclaredMethod("configureLayoutCache");
                method.setAccessible(true);
                method.invoke(ui);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException e1) {
                e1.printStackTrace();
            }
        } else {
            System.err.println("Tree UI is not BasicTreeUI, " +
                    "expect visual glitches like small text in tree of profile editor");
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ProfileAvailableEvent.class);
    }
}
