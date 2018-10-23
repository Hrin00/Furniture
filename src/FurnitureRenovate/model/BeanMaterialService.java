package FurnitureRenovate.model;

public class BeanMaterialService {
    private int materialServiceId;
    private int materialId;
    private int serviceId;
    private int materialQuantity;
    //材料名 材料数量 材料单位 服务名
    private BeanMaterial material;
    private BeanService service;
    private String brandName;


    public int getMaterialServiceId() {
        return materialServiceId;
    }

    public void setMaterialServiceId(int materialServiceId) {
        this.materialServiceId = materialServiceId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(int materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BeanMaterial getMaterial() {
        return material;
    }

    public void setMaterial(BeanMaterial material) {
        this.material = material;
    }

    public BeanService getService() {
        return service;
    }

    public void setService(BeanService service) {
        this.service = service;
    }
}
