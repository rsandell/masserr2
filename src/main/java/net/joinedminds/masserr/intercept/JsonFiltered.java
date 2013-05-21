/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.intercept;

import com.google.common.collect.ImmutableSet;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.interceptor.Interceptor;
import org.kohsuke.stapler.interceptor.InterceptorAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Annotation for filtering the JSON data returned from a {@link JavaScriptMethod} annotated method.
 * Put this annotation on your js proxied method and add the properties you want filtered.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD})
@InterceptorAnnotation(JsonFiltered.Processor.class)
public @interface JsonFiltered {

    /**
     * White-list of properties to include in the output.
     */
    String[] includes() default {};

    /**
     * Black-list of properties to exclude from the output.
     */
    String[] excludes() default {};

    public static class Processor extends Interceptor {
        @Override
        public Object invoke(StaplerRequest request, StaplerResponse response, Object instance, Object[] arguments)
                throws IllegalAccessException, InvocationTargetException {
            JsonFiltered annotation = target.getAnnotation((JsonFiltered.class));
            if (annotation != null) {
                JsonConfig config = new JsonConfig();
                config.setJsonPropertyFilter(new FilterPropertyFilter(annotation.includes(), annotation.excludes()));
                HttpResponseRenderer.CONFIG.set(config);
            }
            return target.invoke(request, response, instance, arguments);
        }
    }

    static class FilterPropertyFilter implements PropertyFilter {

        Set<String> includes;
        Set<String> excludes;

        public FilterPropertyFilter(String[] includes, String[] excludes) {
            this(ImmutableSet.copyOf(includes), ImmutableSet.copyOf(excludes));
        }

        public FilterPropertyFilter(Set<String> includes, Set<String> excludes) {
            this.includes = includes;
            this.excludes = excludes;
        }

        @Override
        public boolean apply(Object source, String name, Object value) {
            if (excludes.contains(name)) {
                return true;
            } else if (!includes.isEmpty()) {
                return !includes.contains(name);
            } else {
                return false;
            }
        }
    }
}
