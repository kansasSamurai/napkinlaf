// $Id$

package napkin;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class NapkinCheckBoxUI extends BasicCheckBoxUI
        implements NapkinConstants {

    public static ComponentUI createUI(JComponent c) {
        return NapkinUtil.uiFor(c, new NapkinCheckBoxUI());
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        NapkinUtil.installUI(c);
        icon = NapkinIconFactory.createCheckBoxIcon();
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    public void paint(Graphics g, JComponent c) {
        NapkinUtil.defaultGraphics(g);
        super.paint(g, c);
    }
}

