package uidxgenerator.parser;

import java.util.ArrayList;
import java.util.List;

import uidxgenerator.entity.EntireSQL;
import uidxgenerator.entity.SqlCommand;
import uidxgenerator.util.StringUtil;

/**
 * SQL����Parser�ł��B<br />
 * @author W.Ryozo
 * @version 1.0
 */
public class SQLParser {
	
	/** SQL���̋�؂蕶���@*/
	private static final String SQL_COMMAND_DELIMITER = ";";
	
	/**
	 * SQL����ǂݍ��݁A{@link EntireSQL}�I�u�W�F�N�g�ɕϊ����܂��B
	 * @param targetSqlCommands SQL��
	 * @return ������SQL����͂���EntireSQL
	 */
	public EntireSQL parse(String targetSqlCommands) {
		EntireSQL entireSQL = new EntireSQL();
		if (StringUtil.isNullOrEmpty(targetSqlCommands)) {
			return entireSQL;
		}
		
		List<String> sqlCommandList = splitSqlCommands(targetSqlCommands);
		for (String sql : sqlCommandList) {
			entireSQL.addSqlCommand(new SqlCommand(sql));
		}
		
		return entireSQL;

	}
	
	/**
	 * �����Ɏ󂯎����SQL�R�}���h��SQL��؂蕶���ŕ������AList�`���ŕԋp���܂��B<br />
	 * �X��SQL���͖�����SQL��؂蕶�����܂񂾏�Ԃŕ�������܂��B<br />
	 * �����\�b�h��SQL��؂蕶����SQL���̃R�����g�����ŗ��p����Ă���ꍇ�A����ɓ��삵�܂���B
	 * @param targetSqlCommands �ϊ��Ώۂ�SQL��
	 * @return �������SQL��
	 */
	private List<String> splitSqlCommands(String targetSqlCommands) {
		// TODO �R�����g�Ή�
		List<String> sqlCommandList = new ArrayList<String>();
		String[] splitSqlCommands = targetSqlCommands.split(SQL_COMMAND_DELIMITER);
		for (String command : splitSqlCommands) {
			sqlCommandList.add(command.concat(SQL_COMMAND_DELIMITER));
		}
		
		return sqlCommandList;
		
//		int ch =0;
//		while (ch >= targetSqlCommands.length()) {
//			int index = targetSqlCommands.indexOf(SQL_COMMAND_DELIMITER, ch) + 1;
//			sqlCommandList.add(targetSqlCommands.substring(ch, index));
//			ch += index - ch;
//		}
//		return sqlCommandList;
	}
}
