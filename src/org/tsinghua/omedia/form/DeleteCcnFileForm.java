package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

/**
 * 
 * @author xuhongfeng
 *
 */
public class DeleteCcnFileForm extends BaseForm {
    @HttpParam(name="ccnName")
    private String ccnName;

    public String getCcnName() {
        return ccnName;
    }

    public void setCcnName(String ccnName) {
        this.ccnName = ccnName;
    }
    
    
}
