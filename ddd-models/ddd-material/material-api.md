## 物料领域API ##

- 查询所有的物料数据 url=/material/materials 外部
- 分页查询物料数据 url=/material/materials/{pageSize}/{pageNo}?queryParam={XXXX} 外部
- 根据id数据批量查询物料数据 url=/material/materialsByIds 内部
- 分类查询物料数据 url=/material/materialsByMaterialType 内部
- 添加物料数据 url=/material/add_material 外部
- 修改物料主数据 url=/material/change_materialMaster/{id} 外部
- 修改物料控制属性 url=/material/change_materialControl/{id} 外部
- 添加可选物料属性 url=/material/add_optional_props/{id} 外部
- 添加可选物料特性 url=/material/add_optional_character/{id} 外部
- 获取单个物料信息 url=/material/getMaterialById/{id} 外部 

注意:外部接口包含内部,也就是说只要外部能够访问的接口,内部一定能够访问;而只给内部访问的接口
外部肯定不能访问;
这里的定义似乎应该是
1. 外部无条件访问(不用登录)
2. 外部有条件访问(需要登录)
3. 内部无条件访问(内部任意访问)
4. 内部有条件访问(内部认证访问)

### 外部接口 ###

### 内部接口 ###