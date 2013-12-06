package uidxgenerator.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

/**
 * CreateTable����\��Domain�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class CreateTableSqlCommand extends SqlCommand {

	private static final long serialVersionUID = 1L;
	
	/** �쐬�Ώۂ̃e�[�u���� */
	private String createTableName;
	
	/**
	 * Unique����t�^�Ώۂ̍��ڈꗗ�B<br />
	 * UNIQUE�ΏۂƂ���J�������̈ꗗ��ێ�����Set�ō\�������B<br />
	 * �P��UNIQUE�̏ꍇ��Set��1�v�f�̂ݎ����A����UNIQUE�̏ꍇ�A
	 * Set��UNIQUE�L�[���\�����邷�ׂẴJ��������ێ�����B
	 */
	private List<Set<String>> uniqueKeyList;
	
	public CreateTableSqlCommand(String command, String tableName, List<Set<String>> uniqueKeyList) {
		super(command);
		this.createTableName = tableName;
		this.uniqueKeyList = uniqueKeyList;
	}
	
	/**
	 * ��CreateTable���쐬����TABLE���̂�ԋp���܂��B
	 * @return TABLE����
	 */
	public String getCreateTableName() {
		return createTableName;
	}
	
	/**
	 * ��CreateTable���ێ�����UNIQUE���񍀖ڂ̈ꗗ��ԋp���܂��B
	 * @return UNIQUE�ƂȂ鍀�ڈꗗ
	 */
	public List<Set<String>> getUniqueKeyList() {
		return uniqueKeyList;
	}
	
	/**
	 * TODO �����ɒ�`���Ȃ��B�ꏊ��ς��邱�ƁB
	 */
	public void removeUniqueConstraints() {
		BufferedReader reader = null;
		StringBuilder sqlBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new StringReader(this.getSqlCommand()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.toUpperCase().indexOf("UNIQUE") == -1) {
					sqlBuilder.append(line).append(System.getProperty("line.separator"));
				} else {
					if (line.trim().toUpperCase().startsWith("UNIQUE")) {
						// �s����Unique�����݂���ꍇ�͂��̍s���̖�������B
						continue;
					}
					sqlBuilder.append(line.replaceAll("(?i) UNIQUE", "")).append(System.getProperty("line.separator"));
				}
			}
		} catch (IOException ioe) {
			// TODO ��O��`
			throw new RuntimeException(ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					// �������Ȃ��B�iStringReader������j
				}
			}
		}
		
		// TODO command�̒u�������B
	}

}
