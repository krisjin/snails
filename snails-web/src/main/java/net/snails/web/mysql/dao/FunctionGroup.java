package net.snails.web.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
public interface FunctionGroup {
	public void addFunctionGroup(FunctionGroup fc);
	
	public void deleteFunctionGroup(@Param("functionGroupId") int functionGroupId);
	
	public void updateFunctionGroup(FunctionGroup fc);
	
	public FunctionGroup findFunctionGroup(@Param("functionGroupId") int functionGroupId);
	
	public List<FunctionGroup> getAllFunctionGroup();
}
