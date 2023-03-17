类标识
@Tag(name = "SearchController", description = "前台门户-搜索模块")
方法信息
@Operation(summary = "商品搜索接口")
参数信息
@ParameterObject GoodsSearchReqDTO condition
@Parameter int id

请求对象描述：
@Parameter(description = "搜索关键字")
private String keyword;

传输对象描述:
@Schema(description = "商品ID")
private Long id;


