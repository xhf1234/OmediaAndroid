package org.tsinghua.omedia.form;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.tsinghua.omedia.annotation.form.AlphaOrNumber;
import org.tsinghua.omedia.annotation.form.NotEmpty;
import org.tsinghua.omedia.annotation.form.Size;
import org.tsinghua.omedia.tool.Logger;

public class FormValidator {
    private static final Logger logger = Logger.getLogger(FormValidator.class);
    private static final Pattern alphaOrNumberPattern = Pattern.compile("^[0-9a-zA-Z]+$");
    
    public static <F extends AbstractForm> String  validate(F form) {
        Exception exception;
        try {
            Field[] fields = form.getClass().getDeclaredFields();
            for(Field e:fields) {
                e.setAccessible(true);
                if(e.isAnnotationPresent(NotEmpty.class)) {
                    String value = (String) e.get(form);
                    if(value==null || value.length()==0) {
                        return e.getAnnotation(NotEmpty.class).msg();
                    }
                }
                if(e.isAnnotationPresent(Size.class)) {
                    Size sizeAnno = e.getAnnotation(Size.class);
                    String value = (String) e.get(form);
                    if(value==null || value.length()<sizeAnno.min()) {
                        return sizeAnno.minMsg();
                    } else if(value.length()>sizeAnno.max()) {
                        return sizeAnno.maxMsg();
                    }
                }
                if(e.isAnnotationPresent(AlphaOrNumber.class)) {
                    String value = (String) e.get(form);
                    AlphaOrNumber anno = e.getAnnotation(AlphaOrNumber.class);
                    if(value==null || !alphaOrNumberPattern.matcher(value).matches()) {
                        return anno.msg();
                    }
                }
            }
            return null;
        } catch (IllegalArgumentException e1) {
            exception = e1;
        } catch (IllegalAccessException e1) {
            exception = e1;
        }
        logger.error("", exception);
        return exception.getMessage();
    }
}
