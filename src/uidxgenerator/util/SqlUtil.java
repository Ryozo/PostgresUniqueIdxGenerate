package uidxgenerator.util;

import uidxgenerator.constants.SqlType;

/**
 * SQL�֘A�̏�����񋟂���Util�N���X�ł��B
 * @author W.Ryozo
 *
 */
public class SqlUtil {

	/** Create Table���̐ړ��� */
	private static final String CREATE_TABLE_PREFIX = "CREATE TABLE";
	
	/**
	 * SQL���̎�ʂ𔻒肵�܂��B
	 * @param sqlCommand ����Ώۂ�SQL��
	 * @return ���茋��
	 */
	public static SqlType decisionSqlType(String sqlCommand) {
		// TODO �C�� ��������SQL�̎�ʂ͂ǂ��܂ŕK�v�Ȃ̂��H
		if (StringUtil.trimWithSpaceString(sqlCommand).startsWith(CREATE_TABLE_PREFIX)) {
			return SqlType.CREATETABLE;
		}

		return SqlType.OTHER;
	}
}
