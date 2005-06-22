// $Id$

package napkin.sketch.sketchers;

import napkin.sketch.Sketcher;
import napkin.sketch.geometry.CubicLine;
import napkin.sketch.geometry.Path;
import napkin.sketch.geometry.QuadLine;
import napkin.sketch.geometry.SketchShape;
import napkin.sketch.geometry.StraightLine;

/**
 * LineSketcher: Sketches things as straight lines.
 *
 * @author Peter Goodspeed
 * @author Justin Crafford
 */
public class LineSketcher extends Sketcher {
    /** @see Sketcher#deformLine(StraightLine) */
    public SketchShape deformLine(StraightLine l) {
        return l;
    }

    /** @see Sketcher#deformQuad(QuadLine) */
    public SketchShape deformQuad(QuadLine q) {
        return formPath(q.transformToLine());
    }

    /** @see Sketcher#deformCubic(CubicLine) */
    public SketchShape deformCubic(CubicLine c) {
        return formPath(c.transformToLine());
    }

    /** @see Sketcher#deformPath(Path) */
    public SketchShape deformPath(Path p) {
        Path ret = new Path();
        SketchShape[] elements = p.simplify();

        for (SketchShape element : elements) {
            ret.append(element.deform(this), false);
        }

        return ret;
    }
}
