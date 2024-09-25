package org.ddd.fundamental.refactoring.extractsubclass;

public class LaborItem extends JobItem{
    private Employee employee;

    public LaborItem(int unitPrice,int quantity){
        super(unitPrice,quantity);
    }
    public LaborItem(int quantity,Employee employee){
        super(0,quantity);
        this.employee = employee;
    }

    protected boolean isLabor(){
        return false;
    }

    public int getUnitPrice(){
        return employee.getRate();
    }

    public Employee getEmployee() {
        return employee;
    }
}
