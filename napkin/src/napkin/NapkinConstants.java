// $Id$

package napkin;

import java.awt.*;
import javax.swing.*;

public interface NapkinConstants extends SwingConstants {
    int LENGTH = 100;

    String THEME_KEY = "napkin.theme";
    String BACKGROUND_KEY = "napkin.background";
    String DISABLED_MARK_KEY = "napkin.disabledMark";
    String BORDER_KEY = "napkin.border";
    String OPAQUE_KEY = "napkin.wasOpaque";

    String[] CLIENT_PROPERTIES = {
        THEME_KEY, BACKGROUND_KEY, DISABLED_MARK_KEY, BORDER_KEY, OPAQUE_KEY,
    };

    // ColorUIResource can't seem to deal with the alpha channel, so we
    // have to use a Color.  I've filed a bug, but for now we do this.
    Color CLEAR = new AlphaColorUIResource(new Color(0, 0, 0, 0));

    int NO_SIDE = -1;
}
