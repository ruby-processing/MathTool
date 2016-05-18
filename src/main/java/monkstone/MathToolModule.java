/**
 * The purpose of this tool is to allow JRubyArt users to use an alternative
 * to processing.org map, lerp and norm methods in their sketches
 * Copyright (c) 2015-16 Martin Prout. This tool is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * Obtain a copy of the license at http://www.gnu.org/licenses/lgpl-2.1.html
 */
package monkstone;

import org.jruby.Ruby;
import org.jruby.RubyFloat;
import org.jruby.RubyModule;
import org.jruby.RubyRange;
import org.jruby.anno.JRubyMethod;
import org.jruby.anno.JRubyModule;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

/**
 *
 * @author Martin Prout
 */
@JRubyModule(name = "MathTool") 
public class MathToolModule {    
    
   /**
    *
    * @param runtime Ruby
    */
  
    public static void createMathToolModule(Ruby runtime) {
        RubyModule mtModule = runtime.defineModule("MathTool");
        mtModule.defineAnnotatedMethods(MathToolModule.class);
    }

    /**
     *
     * @param context ThreadContext
     * @param recv IRubyObject 
     * @param args array of RubyRange (must be be numeric)
     * @return mapped value RubyFloat
     */
    @JRubyMethod(name = "map1d", rest = true, module = true)
    public static IRubyObject mapOneD(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        double value = (Double) args[0].toJava(Double.class);
        RubyRange r1 = (RubyRange) args[1];
        RubyRange r2 = (RubyRange) args[2];
        double first1 = (Double) r1.first(context).toJava(Double.class);
        double first2 = (Double) r2.first(context).toJava(Double.class);
        double last1 = (Double) r1.last(context).toJava(Double.class);
        double last2 = (Double) r2.last(context).toJava(Double.class);
        return mapMt(context, value, first1, last1, first2, last2);
    }

    /**
     *
     * @param context ThreadContext
     * @param recv IRubyObject 
     * @param args array of RubyRange (must be be numeric)
     * @return mapped value RubyFloat
     */
    @JRubyMethod(name = "constrained_map", rest = true, module = true)
    public static IRubyObject constrainedMap(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        double value = (Double) args[0].toJava(Double.class);
        RubyRange r1 = (RubyRange) args[1];
        RubyRange r2 = (RubyRange) args[2];
        double first1 = (Double) r1.first(context).toJava(Double.class);
        double first2 = (Double) r2.first(context).toJava(Double.class);
        double last1 = (Double) r1.last(context).toJava(Double.class);
        double last2 = (Double) r2.last(context).toJava(Double.class);
        double max = Math.max(first1, last1);
        double min = Math.min(first1, last1);
        if (value < min) {
            value = min;
        }
        if (value > max) {
            value = max;
        }
       return mapMt(context, value, first1, last1, first2, last2);
    }

    /**
     *
     * @param context ThreadContext
     * @param recv self IRubyObject
     * @param args floats as in processing map function
     * @return mapped value RubyFloat
     */
    @JRubyMethod(name = "p5map", rest = true, module = true)
    public static IRubyObject mapProcessing(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        double value = (Double) args[0].toJava(Double.class);
        double first1 = (Double) args[1].toJava(Double.class);
        double first2 = (Double) args[3].toJava(Double.class);
        double last1 = (Double) args[2].toJava(Double.class);
        double last2 = (Double) args[4].toJava(Double.class);
        return mapMt(context, value, first1, last1, first2, last2);
    }


    /**
     * A more correct version than processing.org version
     * @param context ThreadContext
     * @param recv self IRubyObject
     * @param args args[2] should be between 0 and 1.0 if not returns start or stop
     * @return lerped value RubyFloat
     */
    @JRubyMethod(name = "lerp", rest = true, module = true)
    public static IRubyObject lerpP(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        double start = (Double) args[0].toJava(Double.class);
        double stop = (Double) args[1].toJava(Double.class);
        double amount = (Double) args[2].toJava(Double.class);
        if (amount <= 0) return args[0];
        if (amount >= 1.0) return args[1];
        return context.getRuntime().newFloat((1 - amount) * start + (stop * amount));
    }


    /**
     * Identical to p5map(value, low, high, 0, 1).
     * Numbers outside of the range are not clamped to 0 and 1,
     * because out-of-range values are often intentional and useful.
     * @param context ThreadContext
     * @param recv IRubyObject
     * @param args array of args must be be numeric
     * @return mapped value RubyFloat
     */
    @JRubyMethod(name = "norm", rest = true, module = true)
    public static IRubyObject normP(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        double value = (Double) args[0].toJava(Double.class);
        double start = (Double) args[1].toJava(Double.class);
        double stop = (Double) args[2].toJava(Double.class);
        return mapMt(context, value, start, stop, 0, 1.0);
    }

    /**
     * Identical to p5map(value, low, high, 0, 1) but 'clamped'.
     * Numbers outside of the range are clamped to 0 and 1,
     * @param context ThreadContext
     * @param recv IRubyObject
     * @param args array of args must be be numeric
     * @return mapped value RubyFloat
     */
    @JRubyMethod(name = "norm_strict", rest = true, module = true)
    public static IRubyObject norm_strict(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        Ruby ruby = context.runtime;
        double value = (Double) args[0].toJava(Double.class);
        double start = (Double) args[1].toJava(Double.class);
        double stop = (Double) args[2].toJava(Double.class);
        if (value <= start) {
            return new RubyFloat(ruby, 0);
        } else if (value >= stop) {
            return new RubyFloat(ruby, 1.0);
        } else {
            return mapMt(context, value, start, stop, 0, 1.0);
        }
    }

    static final RubyFloat mapMt(ThreadContext context, double value, double first1, double last1, double first2, double last2) {
        double result = first2 + (last2 - first2) * ((value - first1) / (last1 - first1));
        return context.getRuntime().newFloat(result);
    }

    /**
     * Provides processing constrain method as a ruby module method
     * @param context ThreadContext
     * @param recv IRubyObject
     * @param args array of args must be be numeric
     * @return original or limit values
     */
    @JRubyMethod(name = "constrain", rest = true, module = true)
    public static IRubyObject constrainValue(ThreadContext context, IRubyObject recv, IRubyObject[] args) {
        RubyFloat value = args[0].convertToFloat();
        RubyFloat start = args[1].convertToFloat();
        RubyFloat stop = args[2].convertToFloat();
        if (value.op_ge(context, start).isTrue() && value.op_le(context, stop).isTrue()) {
            return args[0];
        } else if (value.op_ge(context, start).isTrue()) {
            return args[2];
        } else {
            return args[1];
        }
    }
}
