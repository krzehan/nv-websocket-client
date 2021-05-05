package pl.hanebach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dvce implements Serializable {

    public String dvceID;
    public String dvceLabel;
    public String comment;

    DvceGrp group;


    public static List<Dvce> dvceExt = new ArrayList<>();

    Dvce(String ID, String label){

        this.dvceID = ID;
        this.dvceLabel = label;

        dvceExt.add(this);
    }

    Dvce(String ID, String label, String comment){

        this.dvceID = ID;
        this.dvceLabel = label;
        this.comment = comment;

        dvceExt.add(this);
    }


    public static List<Dvce> getDvceExt() {
        return dvceExt;
    }

    public static void setDvceExt(List<Dvce> dvceExt) {
        Dvce.dvceExt = dvceExt;
    }

    public String getDvceID() {
        return dvceID;
    }

    public void setDvceID(String dvceID) {
        this.dvceID = dvceID;
    }

    public String getDvceLabel() {
        return dvceLabel;
    }

    public void setDvceLabel(String dvceLabel) {
        this.dvceLabel = dvceLabel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
