package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;

import javax.persistence.*;

@Entity
@Table( name = "w_work_process_template_control")
public class WorkProcessTemplateControlEntity extends AbstractEntity<WorkProcessTemplateId> {

    private WorkProcessTemplateControl control;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "template_id")
//    private WorkProcessTemplate template;

    @SuppressWarnings("unused")
    protected WorkProcessTemplateControlEntity(){}

    protected WorkProcessTemplateControlEntity(WorkProcessTemplateControl control){
        super(WorkProcessTemplateId.randomId(WorkProcessTemplateId.class));
        this.control = control;
    }

    public static WorkProcessTemplateControlEntity create(WorkProcessTemplateControl control){
        return new WorkProcessTemplateControlEntity(control);
    }

//    public void setTemplate(WorkProcessTemplate template) {
//        this.template = template;
//    }

    public WorkProcessTemplateControl getControl() {
        return control.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
