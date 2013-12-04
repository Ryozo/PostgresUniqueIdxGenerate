package uidxgenerator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SQL�S�̂�ێ�����Domain�ł��B
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
	 * SQL�����擾���܂��B
	 * @return�@SQL���̈ꗗ
	 */
	public List<SqlCommand> getSqlCommandList() {
		return sqlCommandList;
	}
	
	/**
	 * ���g���ێ�����SQL�����̑SUNIQUE����ɑ΂��Ĉ����Ɏw�肳�ꂽUNIQUE������ǉ����܂��B<br />
	 * TODO javadoc
	 * <pre>
	 * [SQL�̕ύX���e]
	 *  1.���g���ێ�����SCreateTable���ɒ�`���ꂽUNIQUE�����`���폜
	 *  2�D1�ɂĈ����Ɏw�肳�ꂽ������ݒ肵��Create Unique Index����ǉ�
	 * </pre>
	 * @param conditionMap UNIQUE����ɑ΂��Ēǉ���������iKey:�J�������AValue:�����l)
	 */
	public void addConditionToAllUniqueConstraint(Map<String, String> conditionMap) {
		
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
