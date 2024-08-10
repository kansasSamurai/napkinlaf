# Napkin Look & Feel

The original project site is "stale" but still hosted at:
https://napkinlaf.sourceforge.net/

<hr>

#### What's Here

If you get the jars release, you will have two jar files (and this
<tt>README</tt>). The smaller one &mdash; <tt>napkinlaf.jar</tt> &mdash; is the
jar for the Napkin Look and Feel implementation. The larger one &mdash; <tt>napkinlaf-swingset2.jar</tt> &mdash;
contains both SwingSet2 classes so it can be run as a demo thusly:

```
    java -jar napkinlaf-swingset2.jar 
```

The SwingSet2 code is the one released in JDK 1.5. It has been modified to allow
an extensible set of look and feel types, including those with themes, and to
fix a couple of places where assumptions were made about how a look and feel
works that were not accurate.

<hr>

#### Using Napkin

You can set the look & feel for Java applications. 
The [Java tutorial](https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/lookandfeel/plaf.html) 
[1] describes the standard way to do this. This will work for command line applications. 

Changing it for applications that start from a desktop 
(such as by a double-click) are more difficult to get at; 
you will have to poke at the configuration for each such application. 

[1] Original Link - [http://java.sun.com/docs/books/tutorial/uiswing/misc/plaf.html](http://java.sun.com/docs/books/tutorial/uiswing/misc/plaf.html)



##### Historical Text (no longer applies) ...

<a href="http://weblogs.java.net/blog/kirillcool/archive/2005/08/intellij_idea_5.html">
This page</a> shows how it's done for one application 
(JetBrain's <a href="http://www.jetbrains.com/idea/">IntelliJ IDE</a>); 
maybe this will give you some ideas. 
It also shows how to make Napkin an installed look & feel.

<hr>

#### Personal Stuff 

This is released under the BSD license, but we're curious
about anyone who does use it. We'd consider it a favor if you'd drop us a
note about what you are doing with it and any comments you have.

<b>Peter Goodspeed</b> and <b>Justin Crafford</b> created the sketching
subsystem, as a senior project for their degrees at Worcester Polytechnic
Institute. They did a great job, and solved an important problem. 

And thanks to ...
<b>Scott Anderson</b>, a fellow student and friend of mine who suggested they
get in touch with me to look for a thesis project.

<b>Scott Violet</b> of Sun has helped me pick apart some of the more
abstruse and arcane bits of the PLAF framework, which is full of 'em. 
Thanks a bunch, Scott, 

And thanks to ...
<b>Hans Muller</b>, also of Sun, for plugging me together with him.

    The font <a href="http://www.ms-studio.com/FontSales/felttiproman.html">Felt
    Tip Roman</a> was created by <b>Mark Simonson</b>, who spent a lot of time
    with me on how to license this. He has kindly decided that this particular
    use does not require individual licensing for each user of the LAF, but can
    be done by special arrangement. The legalities are below, but beyond those
    legalities, we'd like to ask you all to be cool &mdash; if you like the font
    and want to use it, buy it properly. Making fonts is not easy, and font
    folks get their work ripped off far too often. If you do want to use it, buy
    it from <a href="http://www.ms-studio.com ">his site</a>, where he gets more
    from it.

    The font <a ref="http://www.aenigmafonts.com/fonts/images/ab/aescfont.gif">&AElig;nigma
    Scrawl</a> was created by <b><a href="http://www.aenigmafonts.com/">Brian
    Kent</a></b>, and has worked very well for a handwritten font that scales
    reasonably to GUI-control sizes (most handwritten-style fonts are display
    fonts that only work in large sizes). To make things work better, Brian has
    adapted the font to adjust the spacing around some punctuation as well as
    some other tweaks. So the version released here is a custom one direct from
    the artist (which we believe he expects to roll back into the font at future
    date). We would like to thank Brian a lot for his quick and nimble
    cooperation, which made this work a lot better and easier on me.

<b>Miro Juri&#353;i&#263</b> has been very helpful in thinking through with
me (sometimes <em>for</em> me) some of the hairy graphics problems. This
definitely pushes into some poorly documented areas of the 2D API, and it has
helped a lot to have someone to talk it through with. Thanks, Miro!

<b>Brian Hawthorne</b> created a quick and excellent selection of blueprint
backgrounds for me to choose from. Thanks!

Several people have helped with testing, reporting bugs, and suggesting
things. The most persistent have been: 
* <b>Deryl Steinert</b>
* <b>Bob Herrmann</b>
* <b>David Matuszek</b> 
* <b>Graham Perks</b>
* <b>Henry Story</b> 
* <b>Tom Eugelink</b>.

Thanks to all, and we'll be happy to have <em>you</em> be added to this list.

