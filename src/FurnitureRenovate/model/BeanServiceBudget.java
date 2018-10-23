package FurnitureRenovate.model;

public class BeanServiceBudget {
    private int serviceBudgetId;
    private int serviceId;
    private int serviceSumQuantity;
    private int needHour;
    private String comment;

    public int getServiceBudgetId() {
        return serviceBudgetId;
    }

    public void setServiceBudgetId(int serviceBudgetId) {
        this.serviceBudgetId = serviceBudgetId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getNeedHour() {
        return needHour;
    }

    public void setNeedHour(int needhour) {
        this.needHour = needhour;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getServiceSumQuantity() {
        return serviceSumQuantity;
    }

    public void setServiceSumQuantity(int serviceSumQuantity) {
        this.serviceSumQuantity = serviceSumQuantity;
    }
}
