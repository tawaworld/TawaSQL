package world.tawa.tawajdbc.annotations;

import java.lang.annotation.*;

/**
 * Created by tawa on 2018-12-15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DomainDefine {

    String comment() default "";

    //tableName first than domainClassName
    String tableName() default "";

    /** table domain class */
    Class<?> domainClass() default void.class;
}
