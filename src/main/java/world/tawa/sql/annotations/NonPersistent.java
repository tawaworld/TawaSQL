package world.tawa.tawajdbc.annotations;

import java.lang.annotation.*;

/**
 * Created by tawa on 2018-12-15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonPersistent {

}
