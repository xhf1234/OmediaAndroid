package org.tsinghua.omedia.form;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class AbstractForm {
    public String validate() {
        return FormValidator.validate(this);
    }
    
    public boolean skipValidate(String tag) {
        return false;
    }
}
