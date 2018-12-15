package world.tawa.tawajdbc.annotations;

import java.lang.annotation.*;

/**
 * Created by tawa on 2018-12-15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DomainField {

    String comment() default "";

    boolean autoIncrement() default false;

    /** The associated fields of other tables (Split by ,) */
    String foreignKeyFields() default "";

    /** Real table field name, camel eg:userName(default fieldName)*/
    String tableField() default "";

    /**select max() or sum() avg() */
    String statFunc() default "";

    boolean ignoreWhenSelect() default false;
}
