package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControlValue;

import javax.persistence.*;

@Entity
@Table( name = "w_work_process_template_control")
public class WorkProcessTemplateControl extends AbstractEntity<WorkProcessTemplateId> {

    private WorkProcessTemplateControlValue control;

    @SuppressWarnings("unused")
    protected WorkProcessTemplateControl(){}

    protected WorkProcessTemplateControl(WorkProcessTemplateControlValue control){
        super(WorkProcessTemplateId.randomId(WorkProcessTemplateId.class));
        this.control = control;
    }

    public static WorkProcessTemplateControl create(WorkProcessTemplateControlValue control){
        return new WorkProcessTemplateControl(control);
    }

    public WorkProcessTemplateControlValue getControl() {
        return control.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
