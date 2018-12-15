package world.tawa.tawajdbc.annotations;

import java.lang.annotation.*;

/**
 * Created by tawa on 2018-12-15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QueryDefine {
    /** The associated domain class */
    Class<?> domainClass();
}
