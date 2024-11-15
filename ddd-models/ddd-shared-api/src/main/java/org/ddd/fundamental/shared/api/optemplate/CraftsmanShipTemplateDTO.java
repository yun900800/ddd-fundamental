package org.ddd.fundamental.shared.api.optemplate;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;

import java.util.ArrayList;
import java.util.List;

public class CraftsmanShipTemplateDTO extends AbstractDTO<CraftsmanShipId> {

    private MaterialId productId;

    private List<WorkProcessTemplateId> templateIds;

    @SuppressWarnings("unused")
    private CraftsmanShipTemplateDTO(){}

    private CraftsmanShipTemplateDTO(CraftsmanShipId craftsmanShipId,
                                     MaterialId productId,
                                     List<WorkProcessTemplateId> templateIds){
        super(craftsmanShipId);
        this.productId = productId;
        this.templateIds = templateIds;
    }

    public static CraftsmanShipTemplateDTO create(CraftsmanShipId craftsmanShipId,
                                                  MaterialId productId,
                                                  List<WorkProcessTemplateId> templateIds){
        return new CraftsmanShipTemplateDTO(craftsmanShipId,productId, templateIds);
    }

    @Override
    public String toString() {
        return "CraftsmanShipTemplateDTO{" +
                "productId=" + productId +
                ", templateIds=" + templateIds +
                '}';
    }

    public MaterialId getProductId() {
        return productId;
    }

    public List<WorkProcessTemplateId> getTemplateIds() {
        return new ArrayList<>(templateIds);
    }

    @Override
    public CraftsmanShipId id() {
        return super.id;
    }
}
