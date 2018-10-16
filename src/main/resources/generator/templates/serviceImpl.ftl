package ${package}.impl.${module};
<#assign upClassName=className?cap_first>
import ${package}.bean.${module}.${upClassName}Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.google.common.collect.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qitoon.framework.utils.beancopier.CachedBeanCopier;

import ${package}.dao.${module}.I${upClassName}Dao;
import ${package}.entity.${module}.${upClassName};
import ${packageService}.${module}.I${upClassName}Service;


@Service
public class ${upClassName}ServiceImpl implements I${upClassName}Service{
   
    @Autowired
	private I${upClassName}Dao ${className}Dao;
	
	/**
	 * 查询列表数据
	 */
	public List<${upClassName}Bean> findList(${upClassName}Bean ${className}Bean) {
	  	 ${upClassName} ${className}=new ${upClassName}();
	  	 CachedBeanCopier.copy(${className}Bean,${className});
	  	 List<${upClassName}Bean> destList=Lists.newArrayList(); 
		 CachedBeanCopier.copyList(${className}Dao.findList(${className}),destList,${upClassName}Bean.class);
         return destList;
	}
	
	/**
	 * 查询分页数据
	 */
	public PageInfo<${upClassName}Bean> findListPage(${upClassName}Bean ${className}Bean,int pageNumber,int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
		${upClassName} ${className}=new ${upClassName}();
		CachedBeanCopier.copy(${className}Bean,${className});
		PageInfo<${upClassName}> info=new PageInfo<${upClassName}>(${className}Dao.findList(${className});
		PageInfo<${upClassName}Bean> page=new PageInfo<${upClassName}Bean>();
		CachedBeanCopier.copy(info,page);
		return page;
	}
	 
	
	/**
	 * 获取单条数据
	 */
	public ${upClassName}Bean getById(Long id){
		${upClassName} ${className}=${className}Dao.getById(id);
		if(${className}==null){return null;}
    	${upClassName}Bean ${className}Bean=new ${upClassName}Bean();
    	CachedBeanCopier.copy(${className},${className}Bean);
    	return ${className}Bean;
	}
	
	/**
	 * 获取ID集合获取数据
	 */
	public List<${upClassName}Bean> getByIds(Long[] id){ 
	  	List<${upClassName}Bean> list=Lists.newArrayList();
	  	CachedBeanCopier.copyList(${className}Dao.getByIds(id),list,${upClassName}Bean.class);
	  	return list;
	}
	
	/**
	 * 获取单条数据
	 */
	public ${upClassName}Bean get(${upClassName}Bean ${className}Bean){
    	  ${upClassName} ${className}=new ${upClassName}();
	  	  CachedBeanCopier.copy(${className}Bean,${className});
	  	  ${className}=${className}Dao.get(${className});
          if(${className}==null){return null;}
	  	  ${className}Bean=new ${upClassName}Bean();
	  	  CachedBeanCopier.copy(${className},${className}Bean);
	  	  return ${className}Bean;
	}
	
	/**
	 * 查询所有数据列表
	 */ 
	public List<${upClassName}Bean> findAllList(){ 
	  	  List<${upClassName}Bean> destList=Lists.newArrayList(); 
		  CachedBeanCopier.copyList(${className}Dao.findAllList(),destList,${upClassName}Bean.class);
		  return destList;
	}

	/**
	* 查询数据条数
	* @param entity
	* @return
	*/
	public int getCount(${upClassName}Bean ${className}Bean){
		  ${upClassName} ${className}=new ${upClassName}();
          CachedBeanCopier.copy(${className}Bean,${className});
          return ${className}Dao.getCount(${className});
	}
	
	/**
	 * 插入数据
	 */
	public boolean save(${upClassName}Bean ${className}Bean){
	  	 ${upClassName} ${className}=new ${upClassName}();
	  	 CachedBeanCopier.copy(${className}Bean,${className});
	  	 int rst=${className}Dao.insert(${className});
	  	 return rst>0?true:false;
	}
	
	/**
	 * 更新数据
	 */
	public boolean updateById(${upClassName}Bean ${className}Bean){
	   ${upClassName} ${className}=new ${upClassName}();
	   CachedBeanCopier.copy(${className}Bean,${className});
  	   int rst=${className}Dao.updateById(${className});
  	   return rst>0?true:false;
	}

	/**
	* 更新数据
	*/
	public boolean updateByIds(${upClassName}Bean ${className}Bean,Long[] id){
		${upClassName} ${className}=new ${upClassName}();
		CachedBeanCopier.copy(${className}Bean,${className});
		int rst=${className}Dao.updateByIds(${className},id);
		return rst>0?true:false;
	}

	/**
	 * 删除数据(物理删除)
	 */ 
	public boolean deleteById(Long id){
		int rst=${className}Dao.deleteById(id);
		return rst>0?true:false;
	}
	
	/**
	 * 删除数据(物理删除)
	 */
	public boolean delete(${upClassName}Bean ${className}Bean){ 
	    ${upClassName} ${className}=new ${upClassName}();
	    CachedBeanCopier.copy(${className}Bean,${className});
  	    int rst=${className}Dao.delete(${className});
  	    return rst>0?true:false;
	}
	
	/**
	 * 删除数据(物理删除)
	 */
	public boolean deleteByIds(Long[] id){
		int rst=${className}Dao.deleteByIds(id);
		return rst>0?true:false;
	} 
}