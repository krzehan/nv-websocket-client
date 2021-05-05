package pl.hanebach;

import java.util.ArrayList;
import java.util.List;

public class DvceHb {

    //addDvce
    //addDvceGrp

    //connectDvce(a,b)
    //connectDvceGrp(c,d)

    //disconnectDvce(a)
    //disconnectDvceGrp(c)

    String label;
    String capacity;
    int qOfConnectedDvces;
    int qOfConnectedDvceGrps;

    static List<DvceHb> dvceHbExt = new ArrayList<>();


    DvceHb(){

    dvceHbExt.add(this);


    }

}
