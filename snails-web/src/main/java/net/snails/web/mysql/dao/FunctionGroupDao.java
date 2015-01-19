package net.snails.web.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface FunctionGroupDao {
	public void addFunctionGroup(FunctionGroupDao fc);

	public void deleteFunctionGroup(@Param("functionGroupId") int functionGroupId);

	public void updateFunctionGroup(FunctionGroupDao fc);

	public FunctionGroupDao findFunctionGroup(@Param("functionGroupId") int functionGroupId);

	public List<FunctionGroupDao> getAllFunctionGroup();
}
