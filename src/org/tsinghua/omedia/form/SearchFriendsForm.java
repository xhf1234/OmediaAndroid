package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

public class SearchFriendsForm extends BaseForm {
    @HttpParam(name="keyword")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    
}
