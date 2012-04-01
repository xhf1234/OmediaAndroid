package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;
import org.tsinghua.omedia.annotation.form.NotEmpty;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CreateGroupForm extends BaseForm {
    @NotEmpty(msg="群组名称不能为空")
    @HttpParam(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
