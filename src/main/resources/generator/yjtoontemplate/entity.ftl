package ${package}.domain;
import com.yjtoon.framework.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
<#assign upClassName=className?cap_first> 
@Data 
public class ${upClassName} extends BaseEntity<${upClassName}> {
     
<#list columns as column>
    private ${column.dataType} ${column.columnName}; //${column.columnComment}
</#list> 
 
}