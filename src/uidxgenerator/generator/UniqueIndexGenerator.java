package uidxgenerator.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import uidxgenerator.domain.CreateTableSqlCommand;
import uidxgenerator.domain.EntireSQL;
import uidxgenerator.domain.SqlCommand;
import uidxgenerator.parser.SQLParser;

/**
 * UniqueIndex��Generator�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class UniqueIndexGenerator {
	
	// TODO javadoc
	public String generate(String sqlFilePath, 
			String fileEncoding, 
			String indexConditionField, 
			Boolean indexConditionValue) throws Exception {
		if (sqlFilePath == null) {
			throw new IllegalArgumentException("�Ώ�SQL�t�@�C���̃p�X���w�肳��Ă��܂���ł�");
		}
		File file = new File(sqlFilePath);
		if (!file.isFile()) {
			throw new IllegalArgumentException("�Ώ�SQL�t�@�C�������݂��܂���");
		}

		// TODO ������Validate
		String sql = readSqlFile(file, fileEncoding);		
		SQLParser sqlParser = new SQLParser();
		EntireSQL entireSql = sqlParser.parse(sql);
		
		List<SqlCommand> sqlCommandList = entireSql.getSqlCommandList();
		for (SqlCommand command : sqlCommandList) {
			if (command instanceof CreateTableSqlCommand) {
				
			}
		}

		return null;
	}
	
	/**
	 * TODO �ڍׂɋL�q�B
	 * @param sqlPath UniqueIndex�t�^�ΏۂƂ���SQL�t�@�C���p�X
	 * @param fileEncoding SQLFile�̃G���R�[�f�B���O
	 * @param indexConditionFields UniqueIndex�̏���
	 * @return �쐬����SQL�t�@�C���̃p�X
	 */
	// TODO throws Exception���C��
	public String generate(String sqlFilePath, String fileEncoding, String... indexConditionFields) throws Exception {
		// Validate
		if (sqlFilePath == null) {
			throw new IllegalArgumentException("�Ώ�SQL�t�@�C���̃p�X���w�肳��Ă��܂���ł�");
		}
		File file = new File(sqlFilePath);
		if (!file.isFile()) {
			throw new IllegalArgumentException("�Ώ�SQL�t�@�C�������݂��܂���");
		}
		
		if (indexConditionFields == null 
				|| indexConditionFields.length == 0) {
			// TODO �t�@�C���ύX�s�v
		}
		
		String sql = readSqlFile(file, fileEncoding);
		SQLParser sqlParser = new SQLParser();
		EntireSQL entireSql = sqlParser.parse(sql);
		
		
		return null;
	}
	
	/**
	 * SQL�t�@�C����ǂݍ��݂܂��B
	 * @param sqlFile �ǂݍ��ݑΏۂ�SQL�t�@�C��
	 * @param encoding SQL�t�@�C���̕����R�[�h
	 * @return �ǂݍ���SQL�t�@�C���i���s�R�[�h�܂ށj
	 */
	private String readSqlFile(File sqlFile, String encoding) throws IOException {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(sqlFile), encoding));
			builder = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1) {
				builder.append((char) c);
			}
			
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		
		return builder.toString();
	}
}
