package world.tawa.tawajdbc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by tawa on 2018-12-15
 */
public class DumpUtil {
    //
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String dump(Object o) {
        if(o==null){
            return "<NULL>";
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
