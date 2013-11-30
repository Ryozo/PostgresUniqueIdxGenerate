package uidxgenerator.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * SQL�t�@�C������Validate���܂��B
 * @author W.Ryozo
 * @version 1.0
 */
public class FileValidator {
	
	/**
	 * TODO �L�q
	 * @param filePath �`�F�b�N�Ώۃt�@�C���̃p�X
	 */
	public static void validateFile(String filePath) throws IOException {
		if (filePath == null) {
			throw new NullPointerException("�Ώۃt�@�C���p�X��Null�ł�");
		}
		
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("�Ώۃt�@�C�������݂��܂���");
		}
	}
}
