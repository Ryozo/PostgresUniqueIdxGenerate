package uidxgenerator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL�S�̂�ێ�����Entity�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class EntireSQL implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** SQL�����\������SQL�̃��X�g */
	private List<SqlCommand> sqlCommandList = new ArrayList<SqlCommand>();
	
	/**
	 * SQL����ǉ����܂��B
	 * @param command
	 */
	public void addSqlCommand(SqlCommand command) {
		sqlCommandList.add(command);
	}
	
	/**
	 * SQL���𕶎���\���ŕԋp���܂��B
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (SqlCommand command : sqlCommandList) {
			builder.append(command.toString());
		}
		return builder.toString();
	}

}
