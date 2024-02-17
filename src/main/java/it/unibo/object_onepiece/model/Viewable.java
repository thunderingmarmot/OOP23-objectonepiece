package it.unibo.object_onepiece.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates whether the class it is used on can be viewed by the View.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Viewable {
    /**
     * Getter for an Entity name that can be used by the View.
     * @return the Entity name
     */
    String getName();
}
