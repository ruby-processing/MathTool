# MathTool
Provides an example of creating an independent JRuby Extension.  

## Motivation
Both ruby-processing and JRubyArt should provide math convenience methods of vanilla processing, that are not otherwise available for ruby. One of the more useful convenience methods is processings `map` method (there could be hardly less welcome name for ruby) which maps a value from source range to a target range. This as with many other methods are provided as a static PApplet method, which makes it good ruby module candidate (ruby-processing originally patched through many PApplet class methods as instance methods, some of which were better provide in ruby). 

Initially these methods were provided in pure ruby however I was unhappy with using `map` in ruby and provided an alternative `map1d`, replacing the 5 argument `map` method with a 3 argument `map1d` making use of rubies Range class. This is probably inefficient but much more ruby like. Other convenience functions of `lerp` and `norm` are essentially 
variations on the original `map`. It is expected that creating JRuby extensions will be more efficient, and in any event calculated values will be passed back to java methods and the jvm should be able to sort it out.

There is a need to create example JRuby extensions to promote their usage (although I'm not entirely sure that using maven will help this goal, and currently sticking with my tried and tested Library vs LibraryService for now, its good enough for headius, kares, mkristian its good enough for me).

## Work in progress
Currently the widely used method of creating JRuby extensions it to use rake-compiler, there is a initiative afoot to convert to a maven build, this is an early stage pass at doing that for MathTool (a subset of rpextras.jar built into ruby-processing and JRubyArt). However for a variety of reasons JRubyArt and ruby-processing may not be ideal examples, since version control is completely within the hands of the developer, which may not always be the case. Further we do not provide the main java jars (jruby-complete and processing jars, in the gem, actually this is probably a good thing, nor do we try and link/version control from the rpextras.jar, which would be a bad idea...).
