# MathTool
Provides an example of creating a JRuby Extension

## Motivation
Both ruby-processing and JRubyArt should provide math convenience methods of vanilla processing, that are not otherwise available for ruby. One of the more useful convenience methods is processings `map` method (there could be hardly less welcome name for ruby) which maps a value from source range to a target range. This as with many other methods are provided as a static PApplet method, which makes it good ruby module candidate (ruby-processing originally patched through many PApplet class methods as instance methods, some of which were better provide in ruby). 

Initially these methods were provided in pure ruby however I was unhappy with using `map` in ruby and provided an alternative `map1d`, replacing the 5 argument `map` method with a 3 argument `map1d` making use of rubies Range class. This is probably inefficient but much more ruby like. Other convenience functions of `lerp` and `norm` are essentially 
variations on the original `map`.

There is a need to create example JRuby extensions to promote there usage.
