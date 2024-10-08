package net.sourceforge.napkinlaf.util;

import javax.swing.*;
import java.awt.*;

/**
 * This class intercepts calls to the original repaint manager to check for
 * non-Napkin components attempting to repaint.
 * <p/>
 * When such a case is detected, we look for the closest parent which is a
 * Napkin component, then invalidate the relevent region so that a repaint to
 * the underlying paper (or more) can be performed together with the non-Napkin
 * component's repaint in order for Napkin UI to remain intact.
 *
 * @author Alex Lam Sze Lok
 */
public class NapkinRepaintManager extends RepaintManager {

    private final RepaintManager manager;

    /**
     * Creates a new instance of {@link NapkinRepaintManager} that wraps the
     * given repaint manager .
     *
     * @param manager The repaint manager to use.
     */
    private NapkinRepaintManager(RepaintManager manager) {
        this.manager = manager;
    }

    public static NapkinRepaintManager wrap(RepaintManager manager) {
        if (manager instanceof NapkinRepaintManager) {
            return (NapkinRepaintManager) manager;
        } else {
            return new NapkinRepaintManager(manager);
        }
    }

    public static RepaintManager unwrap(RepaintManager manager) {
        return manager instanceof NapkinRepaintManager ?
                ((NapkinRepaintManager) manager).manager :
                manager;
    }

    /**
     * Calls {@link #repaintNapkinParent(JComponent,Rectangle)} with the visible
     * region of the given JComponent as the extra parameter.
     *
     * @param component The bottom component to repaint.
     */
    private void repaintNapkinParent(JComponent component) {
        repaintNapkinParent(component, component.getVisibleRect());
    }

    /**
     * Search up the hierarchy for a Napkin component (the parent component),
     * and calculates the given region in the parent component's coordinates.
     *
     * @param component The bottom component to repaint.
     * @param region    The subregion of the component to repaint.
     */
    @SuppressWarnings({"ObjectEquality"})
    private void repaintNapkinParent(JComponent component, Rectangle region) {
        Container container = component;
        Rectangle bounds = new Rectangle();
        // loop for a matching Napkin parent
        while (container != null && !NapkinUtil.isNapkinInstalled(container)) {
            container.getBounds(bounds);
            container = container.getParent();
            region.x += bounds.x;
            region.y += bounds.y;
        }
        // repaint the relevant region in parent
        if (container != component && NapkinUtil.isNapkinInstalled(container)) {

            manager.addDirtyRegion((JComponent) container, region.x, region.y,
                    region.width, region.height);
        }
    }

    // --------------------------------------
    // Methods that will cause possible repaints in Napkin parent components
    // --------------------------------------

    /**
     * {@inheritDoc}
     * <p/>
     * In addition, if <b>aComponent</b> is not a Napkin component then the
     * region it covers the closest parent Napkin component will be repainted as
     * well.
     */
    @Override
    public void markCompletelyDirty(JComponent aComponent) {
        repaintNapkinParent(aComponent);
        manager.markCompletelyDirty(aComponent);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * In addition, if <b>invalidComponent</b> is not a Napkin component then
     * the region it covers the closest parent Napkin component will be
     * repainted as well.
     */
    @Override
    public void addInvalidComponent(JComponent invalidComponent) {
        repaintNapkinParent(invalidComponent);
        manager.addInvalidComponent(invalidComponent);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * In addition, if the given component is not a Napkin component then the
     * region it covers the closest parent Napkin component will be repainted as
     * well.
     */
    @Override
    public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
        repaintNapkinParent(c, new Rectangle(x, y, w, h));
        manager.addDirtyRegion(c, x, y, w, h);
    }

    /**
     // --------------------------------------
     // Simple delegations
     // --------------------------------------

     // These overrides are for Mustang (1.6), and won't compile under 1.5
     // TODO: Figure out a way to make this conditional -- two versions of the
     // class file maybe?
     //    /** {@inheritDoc} */
//    @Override
//    public void addDirtyRegion(Applet applet, int x, int y, int w, int h) {
//        manager.addDirtyRegion(applet, x, y, w, h);
//    }
//
//    /** {@inheritDoc} */
//    @Override
//    public void addDirtyRegion(Window window, int x, int y, int w, int h) {
//        manager.addDirtyRegion(window, x, y, w, h);
//    }

    /** {@inheritDoc} */
    @Override
    public Image getOffscreenBuffer(Component c, int proposedWidth,
            int proposedHeight) {
        return manager.getOffscreenBuffer(c, proposedWidth, proposedHeight);
    }

    /** {@inheritDoc} */
    @Override
    public Image getVolatileOffscreenBuffer(Component c, int proposedWidth,
            int proposedHeight) {
        return manager.getVolatileOffscreenBuffer(c, proposedWidth,
                proposedHeight);
    }

    /** {@inheritDoc} */
    @Override
    public void setDoubleBufferMaximumSize(Dimension d) {
        manager.setDoubleBufferMaximumSize(d);
    }

    /** {@inheritDoc} */
    @Override
    public void setDoubleBufferingEnabled(boolean aFlag) {
        manager.setDoubleBufferingEnabled(aFlag);
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getDirtyRegion(JComponent aComponent) {
        return manager.getDirtyRegion(aComponent);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompletelyDirty(JComponent aComponent) {
        return manager.isCompletelyDirty(aComponent);
    }

    /** {@inheritDoc} */
    @Override
    public void markCompletelyClean(JComponent aComponent) {
        manager.markCompletelyClean(aComponent);
    }

    /** {@inheritDoc} */
    @Override
    public void removeInvalidComponent(JComponent component) {
        manager.removeInvalidComponent(component);
    }

    /** {@inheritDoc} */
    @Override
    public void validateInvalidComponents() {
        manager.validateInvalidComponents();
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getDoubleBufferMaximumSize() {
        return manager.getDoubleBufferMaximumSize();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDoubleBufferingEnabled() {
        return manager.isDoubleBufferingEnabled();
    }

    /** {@inheritDoc} */
    @Override
    public void paintDirtyRegions() {
        manager.paintDirtyRegions();
    }

    /**
     * --------------------------------------
     * Object methods override
     * --------------------------------------
     */

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "NapkinRepaintManager {" + manager.toString() + "}";
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NapkinRepaintManager && manager.equals(
                ((NapkinRepaintManager) obj).manager);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return NapkinRepaintManager.class.hashCode() ^ manager.hashCode();
    }
}
