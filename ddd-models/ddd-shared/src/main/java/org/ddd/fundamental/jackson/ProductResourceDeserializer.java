package org.ddd.fundamental.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;

import java.io.IOException;

/**
 * 增加一个自定义反序列化的处理器
 */
@Slf4j
public class ProductResourceDeserializer extends StdDeserializer<ProductResource> {
    protected ProductResourceDeserializer(){
        super((Class<?>) null);
    }

    protected ProductResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProductResource deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String id = node.get("id").asText();
        String resourceType  = node.get("resourceType").asText();
        JsonNode childNode = node.get("resource");
        boolean use = childNode.get("use").asBoolean();
        String desc = childNode.get("desc").asText();
        String name = childNode.get("name").asText();
        ChangeableInfo info = ChangeableInfo.create(name,desc,use);
        if (ProductResourceType.EQUIPMENT.name().equals(resourceType)) {
            return ProductResource.create(new EquipmentId(id),
                    ProductResourceType.EQUIPMENT,info);
        }
        if (ProductResourceType.TOOLING.name().equals(resourceType)) {
            return ProductResource.create(new EquipmentId(id),
                    ProductResourceType.TOOLING,info);
        }
        if (ProductResourceType.WORK_STATION.name().equals(resourceType)) {
            return ProductResource.create(new WorkStationId(id),
                    ProductResourceType.WORK_STATION,info);
        }
        if (ProductResourceType.MATERIAL.name().equals(resourceType)) {
            return ProductResource.create(new MaterialId(id),
                    ProductResourceType.MATERIAL,info);
        }
        return null;
    }
}
