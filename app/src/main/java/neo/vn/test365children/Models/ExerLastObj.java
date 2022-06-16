package neo.vn.test365children.Models;

import java.util.List;

public class ExerLastObj {

    String title;

    List<Baitap_Tuan> mList;

    boolean isExtend = false;

    public ExerLastObj(String title, List<Baitap_Tuan> mList) {
        this.title = title;
        this.mList = mList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Baitap_Tuan> getmList() {
        return mList;
    }

    public void setmList(List<Baitap_Tuan> mList) {
        this.mList = mList;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setExtend(boolean extend) {
        isExtend = extend;
    }
}

