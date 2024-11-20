package org.ddd.fundamental.shared.api.optemplate;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;

public class WorkProcessTemplateDTO extends AbstractDTO<WorkProcessTemplateId> {

    private ChangeableInfo workProcessInfo;

    private ProductResources resources;

    @SuppressWarnings("unused")
    private WorkProcessTemplateDTO(){}

    private WorkProcessTemplateDTO(WorkProcessTemplateId id,
                                   ChangeableInfo workProcessInfo,
                                   ProductResources resources){
        super(id);
        this.workProcessInfo = workProcessInfo;
        this.resources = resources;
    }

    public static WorkProcessTemplateDTO create(WorkProcessTemplateId id,
                                                ChangeableInfo workProcessInfo,
                                                ProductResources resources){
        return new WorkProcessTemplateDTO(id,workProcessInfo,resources);
    }

    public ChangeableInfo getWorkProcessInfo() {
        return workProcessInfo.clone();
    }

    public ProductResources getResources() {
        return resources;
    }

    @Override
    public WorkProcessTemplateId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "WorkProcessTemplateDTO{" +
                "workProcessInfo=" + workProcessInfo +
                ", resources=" + resources +
                '}';
    }
}
