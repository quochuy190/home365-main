package neo.vn.test365children.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 31-May-2022
 * Time: 01:32
 * Version: 1.0
 */
public class BoSach {
    @SerializedName("ERROR")
    String name;
    @SerializedName("id")
    int id;
    @SerializedName("MESSAGE")
    int image;

    public BoSach(int id, String name,  int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public BoSach(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
