package uidxgenerator.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import uidxgenerator.entity.EntireSQL;
import uidxgenerator.parser.SQLParser;
import uidxgenerator.validator.FileValidator;

/**
 * UniqueIndex��Generator�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class UniqueIndexGenerator {
	
	/**
	 * TODO �ڍׂɋL�q�B
	 * @param sqlPath UniqueIndex�t�^�ΏۂƂ���SQL�t�@�C���p�X
	 * @param fileEncoding SQLFile�̃G���R�[�f�B���O
	 * @param indexConditionFields UniqueIndex�̏���
	 * @return �쐬����SQL�t�@�C���̃p�X
	 */
	// TODO throws Exception���C��
	public String generate(String sqlFilePath, String fileEncoding, String... indexConditionFields) throws Exception {
		FileValidator.validateFile(sqlFilePath);
		if (indexConditionFields == null 
				|| indexConditionFields.length == 0) {
			// TODO �t�@�C���ύX�s�v
		}
		
		BufferedReader reader = null;
		String sqlFileContents = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(sqlFilePath)),fileEncoding));
			StringBuilder builder = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1) {
				builder.append((char) c);
			}
			
			sqlFileContents = builder.toString();
			
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}
		
		SQLParser sqlParser = new SQLParser();
		EntireSQL entireSql = sqlParser.parse(sqlFileContents);
		
		
		return null;
	}
}
