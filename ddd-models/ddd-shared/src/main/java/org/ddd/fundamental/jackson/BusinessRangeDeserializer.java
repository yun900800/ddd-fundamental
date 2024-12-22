package org.ddd.fundamental.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

import java.io.IOException;
import java.time.Instant;

@Slf4j
public class BusinessRangeDeserializer extends StdDeserializer<BusinessRange> {

    protected BusinessRangeDeserializer(){
        super((Class<?>) null);
    }

    protected BusinessRangeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BusinessRange deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        String clazz = node.get("clazz").asText();
        if (clazz.equals(WorkOrderComposable.class.getSimpleName())){
            JsonNode childNode = node.get("business");
            String workOrderId = childNode.get("workOrderId").asText();
            String workProcessId = childNode.get("workProcessId").asText();
            JsonNode childNode1 = node.get("dateRangeValue");
            ObjectMapper mapper = new ObjectMapper();
            DateRangeValue rangeValue = mapper.readValue(mapper.writeValueAsString(childNode1),DateRangeValue.class);
            BusinessRange<WorkOrderComposable> businessRange = BusinessRange.create(
                    WorkOrderComposable.create(
                            new WorkOrderId(workOrderId),
                            new WorkProcessId(workProcessId)
                    ),
                    rangeValue
            );
            return businessRange;
        }
        return null;
    }
}
