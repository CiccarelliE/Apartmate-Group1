package MODEL;

import java.util.ArrayList;

/**
 * @author Cole Daubenspeck Dec 6, 2018
 */

public class UtilityHistory extends ArrayList<UtilityBill>
{
    private String name;
    
    public UtilityHistory(String name) {
        super(12);
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public boolean add(UtilityBill ub) {
        boolean added = super.add(ub);
        if(added && size() > 12)
            remove(0);
        return added;
    }
}
