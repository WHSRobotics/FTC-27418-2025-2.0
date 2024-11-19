package org.whitneyrobotics.ftc.teamcode.Libraries.Filters;

/**
 * Class to combine two or more filters together
 */
public class CompoundFilter implements Filter{
    public double output = 0;
    final Filter filter;

    /**
     * Constructs a compound filter containing of other {@link Filter filters} that will be evaluated sequentially.
     * <em>Note: <strong>Order matters!</strong> The innermost filter will be evaluated first, and subsequent filters will wrap the adjusted measurement.</em>
     * @param filter1 First (innermost) filter
     * @param filter2 Second filter
     * @param filters Additional filters to add
     */
    public CompoundFilter(Filter filter1, Filter filter2, Filter... filters){
        CompoundFilter filter = new CompoundFilter(filter1,filter2);
        for(Filter nextFilter : filters){
            filter = new CompoundFilter(filter, nextFilter);
        }
        this.filter = filter;
    }

    @Override
    public void calculate(double newState) {
        //recursively calculate new states
        filter.calculate(newState);
        output = filter.getOutput();
    }

    @Override
    public double getOutput() {
        return output;
    }
}
