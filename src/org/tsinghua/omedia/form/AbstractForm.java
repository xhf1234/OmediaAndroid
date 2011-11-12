package org.tsinghua.omedia.form;

public abstract class AbstractForm {
    public String validate() {
        return FormValidator.validate(this);
    }
}
