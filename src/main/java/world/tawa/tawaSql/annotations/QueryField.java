package world.tawa.tawajdbc.annotations;

import java.lang.annotation.*;

/**
 * Created by tawa on 2018-12-15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QueryField {

    /** The associated table field */
    String tableField() default "";

    /** String default like, array default in, other default = */
    String operator() default "";

    /** Not as a query condition */
    boolean ignore() default false;

    /** Eg: (name like #{nameOrUserName} or userName like #{nameOrUserName}) */
    String whereSql() default "";

    /** The associated fields of other tables (Split by ,) */
    String foreignKeyFields() default "";


}
