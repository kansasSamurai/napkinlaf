package net.sourceforge.napkinlaf.util;

import net.sourceforge.napkinlaf.borders.NapkinBorder;
import net.sourceforge.napkinlaf.sketch.SketchifiedIcon;
import static net.sourceforge.napkinlaf.util.NapkinConstants.*;

import javax.swing.*;
import static javax.swing.AbstractButton.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This is the class where all smart listeners use by Napkin are stuffed.
 *
 * @author Alex Lam Sze Lok
 */
class NapkinSmartListeners {

    public static class BackgroundListener extends SmartStickyListener<Color> {

        public BackgroundListener() {
            super(BACKGROUND_KEY, "background");
        }

        @Override
        protected void overrideValue(JComponent c, Color newValue) {
            if (NapkinUtil.replaceBackground(newValue)) {
                c.setBackground(CLEAR);
            }
        }

        @Override
        protected boolean shouldRecord(Color newValue) {
            return !(newValue instanceof AlphaColorUIResource);
        }
    }

    public static class BorderListener extends SmartStickyListener<Border> {

        public BorderListener() {
            super(BORDER_KEY, "border");
        }

        @SuppressWarnings({"ObjectEquality"})
        @Override
        protected void overrideValue(JComponent c, Border newValue) {
            if (shouldRecord(newValue)) {
                Border newBorder = NapkinUtil.wrapBorder(newValue);
                if (newBorder != newValue) {
                    // setBorder() must be supported
                    c.setBorder(newBorder);
                }
            }
        }

        @Override
        protected boolean shouldRecord(Border newValue) {
            return !(newValue == null || newValue instanceof NapkinBorder);
        }
    }

    public static class RolloverListener extends SmartStickyListener<Boolean> {

        public RolloverListener() {
            super(ROLLOVER_ENABLED, ROLLOVER_ENABLED_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Boolean newValue) {
            if (!Boolean.TRUE.equals(newValue)) {
                ((AbstractButton) c).setRolloverEnabled(true);
            }
        }

        @Override
        protected boolean shouldRecord(Boolean newValue) {
            return true;
        }
    }

    public static class ButtonIconListener extends SmartStickyListener<Icon> {

        public ButtonIconListener() {
            super(BUTTON_ICON_KEY, ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setIcon(new SketchifiedIcon(c, newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class PressedIconListener extends SmartStickyListener<Icon> {

        public PressedIconListener() {
            super(PRESSED_ICON_KEY, PRESSED_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setPressedIcon(new SketchifiedIcon(c,
                        newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class SelectedIconListener extends SmartStickyListener<Icon> {

        public SelectedIconListener() {
            super(SELECTED_ICON_KEY, SELECTED_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setSelectedIcon(new SketchifiedIcon(c,
                        newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class RolloverIconListener extends SmartStickyListener<Icon> {

        public RolloverIconListener() {
            super(ROLLOVER_ICON_KEY, ROLLOVER_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setRolloverIcon(new SketchifiedIcon(c,
                        newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class RolloverSelectedIconListener
            extends SmartStickyListener<Icon> {

        public RolloverSelectedIconListener() {
            super(ROLLOVER_SELECTED_ICON_KEY,
                    ROLLOVER_SELECTED_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setRolloverSelectedIcon(
                        new SketchifiedIcon(c, newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class DisabledIconListener extends SmartStickyListener<Icon> {

        public DisabledIconListener() {
            super(DISABLED_ICON_KEY, DISABLED_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setDisabledIcon(new SketchifiedIcon(c,
                        newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    public static class DisabledSelectedIconListener
            extends SmartStickyListener<Icon> {

        public DisabledSelectedIconListener() {
            super(DISABLED_SELECTED_ICON_KEY,
                    DISABLED_SELECTED_ICON_CHANGED_PROPERTY);
        }

        @Override
        protected void overrideValue(JComponent c, Icon newValue) {
            if (!(newValue == null || newValue instanceof NapkinIcon)) {
                ((AbstractButton) c).setDisabledSelectedIcon(
                        new SketchifiedIcon(c, newValue));
            }
        }

        @Override
        protected boolean shouldRecord(Icon newValue) {
            return !(newValue == null || newValue instanceof SketchifiedIcon);
        }
    }

    private NapkinSmartListeners() {
    }
}
