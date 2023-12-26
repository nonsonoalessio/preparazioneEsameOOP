package terremoti;

import java.util.Comparator;

public class MagnitudeComparator implements Comparator<EQEvent>{
    @Override
    public int compare(EQEvent o1, EQEvent o2){
        if (o1.getMagnitude() == o2.getMagnitude())
            return 0;
        else return o1.getMagnitude() > o2. getMagnitude() ? 1 : -1;
    }
}
