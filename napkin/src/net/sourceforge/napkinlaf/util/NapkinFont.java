// $Id: NapkinFont.java 357 2006-03-15 10:51:36Z kcrca $

/*
 * NapkinFont.java
 *
 * Created on 25 February 2006, 10:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sourceforge.napkinlaf.util;

import javax.swing.plaf.*;
import java.awt.*;
import java.lang.reflect.Field;

/**
 * Until Mustang there is Bug 6313541 which prevents the bundled fonts to load
 * (because font2DHandle is not transferred when calling FontUIResource)
 * <p/>
 * Fixed by a workaround using Reflection - which might not work for applets and
 * Web Start applications, so here I've put in checks so workaround is used only
 * when needed.
 *
 * @author Alex Lam Sze Lok
 */
public class NapkinFont extends Font implements UIResource {

    public NapkinFont(String name, int style, int size) {
        super(name, style, size);
    }

    public NapkinFont(Font font) {
        super(font.getAttributes());
        try {
            Field field = Font.class.getDeclaredField("font2DHandle");
            field.setAccessible(true);
            field.set(this, field.get(font));
            field.setAccessible(false);
            field = Font.class.getDeclaredField("createdFont");
            field.setAccessible(true);
            field.set(this, field.get(font));
            field.setAccessible(false);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
