/*
 * Created on Nov 5, 2004 by peterg : Renderer.java in edu.wpi.mqp.napkin for
 * MQP
 */
package edu.wpi.mqp.napkin;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.Random;

import edu.wpi.mqp.napkin.geometry.*;
import edu.wpi.mqp.napkin.renderers.LineRenderer;

/**
 * The <tt>Renderer<tt> class uses the graphical information contained in a 
 * Template to produce an image. The Renderer is responsible for such things as
 * deforming shapes and changing stroke widths. The altered graphic elements 
 * are then rasterized to create an icon's final image.
 * 
 * A developer wishing to create a new render style needs only implement
 * the abstract methods specifying the deformations that need to
 * be applied to each UtilityShape type. However, they have the option
 * of also overriding the default <tt>render</tt> method if they wish. 
 * 
 * @author Peter Goodspeed
 * @author Justin Crafford
 */
public abstract class Renderer {
	private static Random rng = new Random();

	/**
	 * Renders a TemplateItem exactly as specified by the TemplateItem itself.
	 * Usually only useful for rendering items which have been deformed by other
	 * transformations.
	 * 
	 * @param tItem
	 *           A component of a template specifying geometry and color
	 *           information
	 * @param g2d
	 *           The graphics object on which to render the image
	 */
	protected void quickRender(TemplateItem tItem, Graphics2D g2d) {
		if (tItem.isDrawFill()) {
			g2d.setColor(tItem.getFillColor());
			g2d.fill(tItem.getShape());
		}
		if (tItem.isDrawStroke()) {
			g2d.setStroke(Renderer.getPen(tItem.getStrokeWeight()));
			g2d.setColor(tItem.getStrokeColor());

			g2d.draw(tItem.getShape());
		}
	}

	/**
	 * Returns a drawing pen with round caps and ends, and the specified stroke
	 * weight
	 * 
	 * @param weight
	 *           The width of the stroke of the pen
	 * @return a pen with round caps and ends and with the specified stroke
	 *         weight
	 */
	public static Stroke getPen(float weight) {
		return new BasicStroke(weight, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
	}

	/**
	 * Performs the actual drawing of the template on a Graphics2D object
	 * 
	 * @param template
	 *           the template to render
	 * @param g2d
	 *           the graphics object on which to render the image
	 */
	public void render(Template template, Graphics2D g2d) {
		TemplateItem current;

		Iterator i = template.getListIterator();
		while (i.hasNext()) {
			current = (TemplateItem) i.next();
			current.setShape(current.getShape().deform(this));
			this.quickRender(current, g2d);
		}
	}

	/**
	 * @param l
	 * @return a StraightLine which has been deformed in the manner appropriate
	 *         for this renderer
	 */
	public abstract UtilityShape deformLine(StraightLine l);

	/**
	 * @param q
	 * @return a QuadLine which has been deformed in the manner appropriate for
	 *         this renderer
	 */
	public abstract UtilityShape deformQuad(QuadLine q);

	/**
	 * @param c
	 * @return a CubicLine which has been deformed in the manner appropriate for
	 *         this renderer
	 */
	public abstract UtilityShape deformCubic(CubicLine c);

	/**
	 * @param p
	 * @return a Path which has been deformed in the manner appropriate for this
	 *         renderer
	 */
	public abstract UtilityShape deformPath(Path p);

	/**
	 * Returns the index in <tt>edges</tt> of the edge which first intersects
	 * <tt>l</tt>, or -1 if no such edge is found. First is defined as 'has
	 * the lowest index'
	 * 
	 * @param l
	 * @param edges
	 * @return the index in <tt>edges</tt> of the edge which first intersects
	 *         <tt>l</tt>
	 */
	private int intersectsEdges(StraightLine l, UtilityShape[] edges) {
		for (int i = 0; i < edges.length; ++i) {
			if (l.intersectsLine((StraightLine) edges[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param l
	 * @return a path which represents the input collection of lines
	 */
	protected Path formPath(StraightLine[] l) {
		Path ret = new Path();
		Point p = new Point(l[0].getP1());
		ret.moveTo(p.fX(), p.fY());

		for (int i = 0; i < l.length; ++i) {
			p = new Point(l[i].getP2());
			ret.lineTo(p.fX(), p.fY());
		}

		return ret;
	}

	/**
	 * @param q
	 * @return a path which represents the input collection of quads
	 */
	protected Path formPath(QuadLine[] q) {
		Path ret = new Path();
		Point p = new Point(q[0].getP1());
		Point c;
		ret.moveTo(p.fX(), p.fY());

		for (int i = 0; i < q.length; ++i) {
			p = new Point(q[i].getP2());
			c = new Point(q[i].getCtrlPt());
			ret.quadTo(c.fX(), c.fY(), p.fX(), p.fY());
		}

		return ret;
	}

	/**
	 * @param c
	 * @return a path which represents the input collection of cubics
	 */
	protected Path formPath(CubicLine[] c) {
		Path ret = new Path();
		Point p = new Point(c[0].getP1());
		Point c1;
		Point c2;
		ret.moveTo(p.fX(), p.fY());

		for (int i = 0; i < c.length; ++i) {
			p = new Point(c[i].getP2());
			c1 = new Point(c[i].getCtrlP1());
			c2 = new Point(c[i].getCtrlP2());
			ret.curveTo(c1.fX(), c1.fY(), c2.fX(), c2.fY(), p.fX(), p.fY());
		}

		return ret;
	}
}

