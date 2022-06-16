package neo.vn.test365children.Models;

import java.util.List;

public class ExerTakenObj {

    String title;

    List<DETAILSItem> mList;

    boolean isExtend = false;

    public ExerTakenObj(String title, List<DETAILSItem> mList) {
        this.title = title;
        this.mList = mList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DETAILSItem> getmList() {
        return mList;
    }

    public void setmList(List<DETAILSItem> mList) {
        this.mList = mList;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setExtend(boolean extend) {
        isExtend = extend;
    }
}

