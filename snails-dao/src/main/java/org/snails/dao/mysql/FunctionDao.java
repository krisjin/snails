package org.snails.dao.mysql;

import java.util.List;

import net.snails.entity.mysql.Function;

import org.apache.ibatis.annotations.Param;

public interface FunctionDao {

	public void addFunction(Function function);

	public void updateFunction(Function function);

	public void deleteFunction(@Param("functionId") int functionId);

	public Function findFunctionById(@Param("functionId") int functionId);

	public List<Function> findFunctionByIds(@Param("ids") String ids);

}
