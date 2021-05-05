package pl.hanebach;

import java.util.ArrayList;
import java.util.List;

public class DvceGrp {


   static List<DvceGrp> dvceGrpExt = new ArrayList<>();

   DvceGrp(){

       dvceGrpExt.add(this);

   }


}
