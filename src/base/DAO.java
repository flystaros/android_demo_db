package base;

import java.io.Serializable;
import java.util.List;

/**
 * 实体操作的通用接口
 * @author flystar
 *
 */
public interface DAO<M> 
{
	long insert(M m);
	int delete(Serializable id);  
	int update(M m);
	
	List<M> findAll();
}
