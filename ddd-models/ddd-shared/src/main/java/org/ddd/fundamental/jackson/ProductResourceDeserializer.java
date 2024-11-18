package org.ddd.fundamental.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;

import java.io.IOException;

/**
 * 增加一个自定义反序列化的处理器
 */
public class ProductResourceDeserializer extends StdDeserializer<ProductResource> {
    protected ProductResourceDeserializer(){
        super((Class<?>) null);
    }

    protected ProductResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProductResource deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        String id = node.get("id").asText();
        String resourceType  = node.get("resourceType").asText();
        JsonNode childNode = node.get("resource");
        boolean use = childNode.get("use").asBoolean();
        String desc = childNode.get("desc").asText();
        String name = childNode.get("name").asText();
        ChangeableInfo info = ChangeableInfo.create(name,desc,use);;
        if (ProductResourceType.EQUIPMENT.getValue().equals(resourceType)) {
            return ProductResource.create(new EquipmentId(id),
                    ProductResourceType.EQUIPMENT,info);
        }
        if (ProductResourceType.TOOLING.getValue().equals(resourceType)) {
            return ProductResource.create(new ToolingEquipmentId(id),
                    ProductResourceType.TOOLING,info);
        }
        return null;
    }
}
