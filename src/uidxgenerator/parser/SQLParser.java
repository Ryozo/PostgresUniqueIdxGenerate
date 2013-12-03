package uidxgenerator.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uidxgenerator.entity.CreateTableSqlCommand;
import uidxgenerator.entity.EntireSQL;
import uidxgenerator.entity.SqlCommand;
import uidxgenerator.util.StringUtil;

/**
 * SQL����Parser�ł��B<br />
 * @author W.Ryozo
 * @version 1.0
 */
public class SQLParser {
	
	/** Create Table���̐ړ��� */
	private static final String CREATE_TABLE_PREFIX = "CREATE TABLE";
	
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
			entireSQL.addSqlCommand(buildSqlCommand(sql));
		}
		return entireSQL;

	}
	
	/**
	 * �����Ɏ󂯎����SQL������SqlCommand���쐬���܂��B
	 * @param sql �Ώۂ�SQL
	 * @return �쐬����SQLCommand
	 */
	private SqlCommand buildSqlCommand(String sql) {
		if (StringUtil.isNullOrEmpty(sql)) {
			throw new IllegalArgumentException("SQL is null or empty");
		}
		
		SqlCommand sqlCommand = null;

		BufferedReader reader = null;
		StringBuilder noCommentSqlBuilder = new StringBuilder();

		try {
			// 1SQL��1�s�����͂��A�R�����g�Ȃ���SQL�����\������B
			reader = new BufferedReader(new StringReader(sql));
			
			// ���ݎQ�Ƃ���s���R�����g���ł��邩�ۂ���\���t���O
			boolean innerCommentLineFLg = false;
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (innerCommentLineFLg) {
					// �R�����g���ł���ꍇ�A�Y���s���̃R�����g�I���^�O��T���B
					if (line.indexOf("*/") != -1) {
						// �R�����g������ǂݔ�΂��A�ȍ~�̕������Append
						noCommentSqlBuilder.append(line.substring(line.indexOf("*/") + 2));
						innerCommentLineFLg = false;
					}
					continue;
				}
				if (line.indexOf("--") != -1) {
					// �R�����g�J�n����܂ł̕������Builder�ɃR�s�[
					noCommentSqlBuilder.append(line.substring(0, line.indexOf("--")));
					continue;
				}
				if (line.indexOf("/*") != -1) {
					// �R�����g�J�n����܂ł̕������Builder�ɃR�s�[
					noCommentSqlBuilder.append(line.substring(0, line.indexOf("/*")));
					String commentedString = line.substring(line.indexOf("/*") + 2);
					if (commentedString.indexOf("*/") != -1) {
						// ���s����SQL�R�����g���������Ă���
						noCommentSqlBuilder.append(commentedString.substring(commentedString.indexOf("*/") + 2));
					} else {
						innerCommentLineFLg = true;
					}
					continue;
				}
				
				noCommentSqlBuilder.append(line);
			}
			
			System.out.println(noCommentSqlBuilder.toString());
			
		} catch (IOException ioe) {
			// TODO ��O����
			throw new RuntimeException(ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					// �̂Ă� StringReader������B
				}
			}
		}

		String noCommentSql = noCommentSqlBuilder.toString();
		List<Set<String>> uniqueKeyList = new ArrayList<Set<String>>();

		if (noCommentSql.toUpperCase().startsWith(CREATE_TABLE_PREFIX)) {
			String fieldDefinition = noCommentSql.substring(noCommentSql.indexOf("(") + 1);
			// Field���ɕ�������B�������A���̎��_�ł͖�����Field�ȊO�̏����܂�ł����ԁB
			String[] fields = fieldDefinition.split(",");
			for (int i = 0; i < fields.length; i++) {
				String[] fieldItems = fields[i].split(" ");
				// �P����UNIQE�̃`�F�b�N
				// 2���ږڈȍ~��UNIQUE�L�[���[�h�����邩�`�F�b�N for��Start�͓Y����1�v�f�ڂ���B
				for (int j = 1; j < fieldItems.length; j++) {
					if ("UNIQUE".equalsIgnoreCase(fieldItems[j].trim())) {
						// �P����UNIQUE����
						Set<String> columnSet = new HashSet<String>();
						columnSet.add(fieldItems[0]);
						uniqueKeyList.add(columnSet);
					}
				}
				
				// ��������UNIQUE�̃`�F�b�N
				// 1�L�[���[�h�ڂ�UNIQUE�ł���A����2�P��ڂ̐ړ��������ʂ̊J�n�ł���ꍇ����UNIQUE�ł���
				if ("UNIQUE".equalsIgnoreCase(fieldItems[0])
						&& fieldItems[1].startsWith("(")) {
					// ����UNIQUE��`���� ���ʂ̊J�n����I���܂Ŏ擾���A�t�B�[���h�ꗗ���擾
					// ���̕����ʂ𔭌�����܂Ō㑱��Field��A��(split(",")���{�̂��߁AUNIQUE�L�[�t�B�[���h����������Ă���B
					StringBuilder uniqueFieldsBuilder = new StringBuilder();
					uniqueFieldsBuilder.append(fieldItems[1]);
					if (fieldItems[1].indexOf(")") == -1) { 
						// �Y���t�B�[���h���UNIQUE�L�[��`���������Ă��Ȃ��ꍇ
						// �I�����ʂ������܂ŁA�ȍ~��field��ǂݍ��ށB
						int j = i + 1;
						for (; j < fields.length; j++) {
							if (fields[j].indexOf(")") != -1) {
								// �����ʂ�������������Y��Field�ŕ���UNIQUE��`���I���B
								uniqueFieldsBuilder.append(",").append(fields[j].substring(0, fields[j].indexOf(")") + 1));
								break;
							}
							uniqueFieldsBuilder.append(",").append(fields[j]);
						}
						// ����UNIQUE�̒�`���͈ȍ~�ǂݔ�΂��B
						i = j;
					}
					
					String uniqueFieldDeclare = uniqueFieldsBuilder.toString();
					uniqueFieldDeclare = uniqueFieldDeclare.substring(uniqueFieldDeclare.indexOf("(") + 1, uniqueFieldDeclare.indexOf(")"));
					String[] uniqueFields = uniqueFieldDeclare.split(",");
					Set<String> columnSet = new LinkedHashSet<String>();
					for (String uniqueField : uniqueFields) {
						columnSet.add(uniqueField.trim());
					}
					uniqueKeyList.add(columnSet);
				}
			}
			
			// SqlCommand���쐬
			sqlCommand = new CreateTableSqlCommand(sql, uniqueKeyList);
		} else {
			sqlCommand = new SqlCommand(sql);
		}
		
		return sqlCommand;
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
