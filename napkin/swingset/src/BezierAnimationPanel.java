/*
 * @(#)BezierAnimationPanel.java	1.14 04/07/26
 *
 * Copyright (c) 2004 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)BezierAnimationPanel.java	1.14 04/07/26
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BezierAnimationPanel
 *
 * @version 1.14 07/26/04
 * @author Jim Graham
 * @author Jeff Dinkins (removed dynamic setting changes, made swing friendly)
 */
class BezierAnimationPanel extends JPanel implements Runnable {

    final Runnable timer = new Runnable() {
        public void run() {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            try {
                while (running.get()) {
                    if (getSize().width > 0) {
                        prePaint();
                        SwingUtilities.invokeLater(BezierAnimationPanel.this);
                    }
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };

    Color backgroundColor =  new Color(0,     0, 153);
    Color outerColor      =  new Color(255, 255, 255);
    Color gradientColorA  =  new Color(255,   0, 101);
    Color gradientColorB  =  new Color(255, 255,   0);

    boolean bgChanged = false;

    GradientPaint gradient = null;

    public final int NUMPTS = 6;

    float animpts[] = new float[NUMPTS * 2];

    float deltas[] = new float[NUMPTS * 2];

    float staticpts[] = {
	 50.0f,   0.0f,
	150.0f,   0.0f,
	200.0f,  75.0f,
	150.0f, 150.0f,
	 50.0f, 150.0f,
	  0.0f,  75.0f,
    };

    float movepts[] = new float[staticpts.length];

    BufferedImage img;

    Rectangle bounds = null;

    final AtomicBoolean running = new AtomicBoolean(false);

    final BasicStroke solid = new BasicStroke(9.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 9.0f);
    final int rule = AlphaComposite.SRC_OVER;
    final AlphaComposite opaque = AlphaComposite.SrcOver;
    final AlphaComposite blend = AlphaComposite.getInstance(rule, 0.9f);
    final AlphaComposite set = AlphaComposite.Src;

    /**
     * BezierAnimationPanel Constructor
     */
    public BezierAnimationPanel() {
	addHierarchyListener(
	    new HierarchyListener() {
	       public void hierarchyChanged(HierarchyEvent e) {
		   if(isShowing()) {
		       start();
		   } else {
		       stop();
		   }
	       }
	   }
	);
	setBackground(getBackgroundColor());
        SwingUtilities.invokeLater(this);
    }

    public boolean isOpaque() {
        return true;
    }

    public Color getGradientColorA() {
	return gradientColorA;
    }

    public void setGradientColorA(Color c) {
	if(c != null) {
	    gradientColorA = c;
	}
    }

    public Color getGradientColorB() {
	return gradientColorB;
    }

    public void setGradientColorB(Color c) {
	if(c != null) {
	    gradientColorB = c;
	}
    }

    public Color getOuterColor() {
	return outerColor;
    }

    public void setOuterColor(Color c) {
	if(c != null) {
	    outerColor = c;
	}
    }

    public Color getBackgroundColor() {
	return backgroundColor;
    }

    public void setBackgroundColor(Color c) {
	if(c != null) {
	    backgroundColor = c;
	    setBackground(c);
	    bgChanged = true;
	}
    }

    public void start() {
        if (!running.compareAndSet(false, true))
            return;
	Dimension size = getSize();
	for (int i = 0; i < animpts.length; i += 2) {
	    animpts[i + 0] = (float) (Math.random() * size.width);
	    animpts[i + 1] = (float) (Math.random() * size.height);
	    deltas[i + 0] = (float) (Math.random() * 4.0 + 2.0);
	    deltas[i + 1] = (float) (Math.random() * 4.0 + 2.0);
	    if (animpts[i + 0] > size.width / 6.0f) {
		deltas[i + 0] = -deltas[i + 0];
	    }
	    if (animpts[i + 1] > size.height / 6.0f) {
		deltas[i + 1] = -deltas[i + 1];
	    }
	}
        new Thread(timer).start();
        System.out.println("Animation Started.");
    }

    public synchronized void stop() {
        if (!running.compareAndSet(true, false))
            return;
        System.out.println("Animation Stopped.");
    }

    public void animate(float[] pts, float[] deltas, int index, int limit) {
	float newpt = pts[index] + deltas[index];
	if (newpt <= 0) {
	    newpt = -newpt;
	    deltas[index] = (float) (Math.random() * 3.0 + 2.0);
	} else if (newpt >= (float) limit) {
	    newpt = 2.0f * limit - newpt;
	    deltas[index] = - (float) (Math.random() * 3.0 + 2.0);
	}
	pts[index] = newpt;
    }

    public void run() {
        repaint();
    }

    public void prePaint() {
	GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO);
	Dimension oldSize = getSize();
	Shape clippath = null;
        Dimension size = getSize();
        if (size.width != oldSize.width || size.height != oldSize.height) {
            img = null;
            clippath = null;
        }

        if (img == null) {
            img = (BufferedImage) createImage(size.width, size.height);
        }

        Graphics2D bufferG2D = img.createGraphics();
        bufferG2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_DEFAULT);
        bufferG2D.setClip(clippath);

        for (int i = 0; i < animpts.length; i += 2) {
            animate(animpts, deltas, i + 0, size.width);
            animate(animpts, deltas, i + 1, size.height);
        }
        float[] ctrlpts = animpts;
        int len = ctrlpts.length;
        gp.reset();
        float prevx = ctrlpts[len - 2];
        float prevy = ctrlpts[len - 1];
        float curx = ctrlpts[0];
        float cury = ctrlpts[1];
        float midx = (curx + prevx) / 2.0f;
        float midy = (cury + prevy) / 2.0f;
        gp.moveTo(midx, midy);
        for (int i = 2; i <= ctrlpts.length; i += 2) {
            float x1 = (midx + curx) / 2.0f;
            float y1 = (midy + cury) / 2.0f;
            prevx = curx;
            prevy = cury;
            if (i < ctrlpts.length) {
                curx = ctrlpts[i + 0];
                cury = ctrlpts[i + 1];
            } else {
                curx = ctrlpts[0];
                cury = ctrlpts[1];
            }
            midx = (curx + prevx) / 2.0f;
            midy = (cury + prevy) / 2.0f;
            float x2 = (prevx + midx) / 2.0f;
            float y2 = (prevy + midy) / 2.0f;
            gp.curveTo(x1, y1, x2, y2, midx, midy);
        }
        gp.closePath();

        bufferG2D.setComposite(set);
        bufferG2D.setBackground(backgroundColor);
        bufferG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

        if(bgChanged || bounds == null) {
            bounds = new Rectangle(0, 0, getWidth(), getHeight());
            bgChanged = false;
        }
        // g2d.clearRect(bounds.x-5, bounds.y-5, bounds.x + bounds.width + 5, bounds.y + bounds.height + 5);
        bufferG2D.clearRect(0, 0, getWidth(), getHeight());

        bufferG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        bufferG2D.setColor(outerColor);
        bufferG2D.setComposite(opaque);
        bufferG2D.setStroke(solid);
        bufferG2D.draw(gp);
        bufferG2D.setPaint(gradient);

        if(!bgChanged) {
            bounds = gp.getBounds();
        } else {
            bounds = new Rectangle(0, 0, getWidth(), getHeight());
            bgChanged = false;
        }
        gradient = new GradientPaint(bounds.x, bounds.y, gradientColorA,
                bounds.x + bounds.width, bounds.y + bounds.height,
                gradientColorB, true);
        bufferG2D.setComposite(blend);
        bufferG2D.fill(gp);
        bufferG2D.dispose();
    }

    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
        if (img == null)
            return;
        g2d.setComposite(AlphaComposite.Src);
        g2d.drawImage(img, null, 0, 0);
    }
}
