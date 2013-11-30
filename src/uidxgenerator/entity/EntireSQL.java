package uidxgenerator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL�S�̂�ێ�����Entity�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class EntireSQL implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** SQL�����\������SQL�̃��X�g */
	private Map<Integer, SqlCommand> sqlCommandMap = new LinkedHashMap<Integer, SqlCommand>();
//	private List<SqlCommand> sqlCommandList = new ArrayList<SqlCommand>();
	
	/**
	 * SQL����ǉ����܂��B
	 * @param command
	 */
	public void addSqlCommand(SqlCommand command) {
		
	}
	
	/**
	 * SQL����u�������܂��B
	 * @param sqlId �u�������Ώۂ�SqlCommand��ID
	 * @param command �u�������Ώۂ�SqlCommand
	 */
	public void replaceSqlCommand(Integer sqlId, SqlCommand command) {
		
	}
	
	/**
	 * SQL���𕶎���\���ŕԋp���܂��B
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (SqlCommand command : sqlCommandMap.values()) {
			builder.append(command.toString());
		}
		return builder.toString();
	}

}
