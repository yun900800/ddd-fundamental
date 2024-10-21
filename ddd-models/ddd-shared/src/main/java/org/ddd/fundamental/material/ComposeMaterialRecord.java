package org.ddd.fundamental.material;

import java.util.List;

public class ComposeMaterialRecord extends ComposeComputableObject<MaterialRecord>{

    private ComposeMaterialRecordId composeMaterialRecordId;

    public ComposeMaterialRecord(List<MaterialRecord> dataList){
        super(dataList);
        this.composeMaterialRecordId = ComposeMaterialRecordId.randomId(ComposeMaterialRecordId.class);
    }

}
