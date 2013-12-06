package uidxgenerator.builder;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static uidxgenerator.constants.SqlConstants.CREATE_TABLE_PREFIX;
import static uidxgenerator.constants.SqlConstants.SQL_DELIMITER;
import uidxgenerator.domain.SqlCommand;
import uidxgenerator.util.StringUtil;

/**
 * CreateUniqueIndex����Builder�N���X�ł��B<br />
 * 
 * 
 * @author W.Ryozo
 * @version 1.0
 */
public class CreateIndexSqlBuilder {
	
	/** SQL���̃x�[�X */
	private static final String SQL_BASE = "CREATE UNIQUE INDEX {IDX_NAME} ON {TABLE_NAME} ({KEY_LIST})";
	private static final String SQL_WHERE = " WHERE ";
	private static final String SQL_CONDITION = "{FIELD_NAME} = {FIELD_VALUE}";
	private static final String SQL_AND = "AND";
	private static final String SQL_ISNULL = "IS NULL";
	/** �u�������� */
	private static final String REPLACE_STR_INDEX_NAME = "{IDX_NAME}";
	private static final String REPLACE_STR_TABLE_NAME = "{TABLE_NAME}";
	private static final String REPLACE_STR_KEY_LIST = "{KEY_LIST}";
	private static final String REPLACE_STR_FIELD_NAME = "{FIELD_NAME}";
	private static final String REPLACE_STR_FIELD_VALUE = "{FIELD_VALUE}";
	
	/** Index���� */
	// TODO index���͎w����ł��邵�A�w�肵�Ȃ��Ă������i�f�t�H���g�l�̐ݒ�j����B
	private String indexName;
	/** �e�[�u������ */
	private String tableName;
	/** ��ӃL�[���� */
	private String[] keyList;
	/** ��ӏ��� */
	private Map<String, String> conditionMap = new LinkedHashMap<String, String>();
	
	/**
	 * �C���f�b�N�X���A�e�[�u�����A�C���f�b�N�X�t�B�[���h���𗘗p���ăC���X�^���X���쐬���܂��B
	 * @param indexName �쐬�Ώۂ̃C���f�b�N�X����
	 * @param tableName �C���f�b�N�X�t�^�Ώۂ̃e�[�u����
	 * @param keyList �C���f�b�N�X��t�^����J�������i�������j�[�N�̏ꍇ�A�����w��j
	 */
	public CreateIndexSqlBuilder(String indexName, String tableName, String... keyList) {
		if (StringUtil.isNullOrEmpty(indexName) 
				|| StringUtil.isNullOrEmpty(tableName)
				|| keyList == null
				|| keyList.length == 0) {
			throw new IllegalArgumentException("�����w�肪�s���ł��B��������Ƃ��邱�Ƃ͋�����܂���B");
		}
		this.indexName = indexName;
		this.tableName = tableName;
		this.keyList = keyList;
	}
	
	/**
	 * ���C���f�b�N�X���L���ƂȂ������ݒ肵�܂��B<br />
	 * �����l��null�̏ꍇ�AIsNull�����o�͂��܂��B
	 * @param fieldName �����Ƃ���t�B�[���h��
	 * @param fieldValue �����l
	 */
	public void setIndexCondition(String fieldName, String fieldValue) {
		if (StringUtil.isNullOrEmpty(fieldName)) {
			throw new IllegalArgumentException("UniqueIndex�̏����t�B�[���h��Null�ł�");
		}
		if (fieldValue == null) {
			// PostgreSQL������SQL�𔭍s����ꍇ�A���̔��莮��isEmpty��ǉ����Ă͂Ȃ�Ȃ��B
			// PostgreSQL�͋󕶎���F�����邽�߁ASQL���[WHERE KEY = ""]���w��\�ł���B
			// �󕶎���ISNULL�ɕϊ�����ƈӖ����ς���Ă��܂��B
			// �i�ϊ���SQL��Oracle�ł���ꍇ�AOracle�͋󕶎���F���ł��Ȃ����߁A�󕶎���ISNULL�u�����ׂ��ł���B
			fieldValue = SQL_ISNULL;
		}
		conditionMap.put(fieldName, fieldValue);
	}
	
	/**
	 * ���C���f�b�N�X���L���ƂȂ������Boolean�l�Ŏw�肵�܂��B<br />
	 * @param fieldName
	 * @param fieldValue
	 */
	public void setIndexCondition(String fieldName, boolean fieldValue) {
		if (StringUtil.isNullOrEmpty(fieldName)) {
			throw new IllegalArgumentException("UniqueIndex�̏����t�B�[���h���ł�");
		}
		conditionMap.put(fieldName, Boolean.toString(fieldValue));
	}

	/**
	 * �ݒ肳�ꂽ���e�Ɋ�Â�CreateUniqueIndex�����쐬���܂��B
	 * @return CreateUniqueIndex����ێ�����SqlCommand
	 */
	public SqlCommand build() {
		StringBuilder sqlBuilder = new StringBuilder();
		StringBuilder keyListBuilder =new StringBuilder();
		keyListBuilder.append(keyList[0]);
		if (keyList.length > 1) {
			for (int i = 1; i< keyList.length; i++) {
				keyListBuilder.append(", ");
				keyListBuilder.append(keyList[i]);
			}
		}
		sqlBuilder.append(
				SQL_BASE.replace(REPLACE_STR_INDEX_NAME, indexName)
				.replace(REPLACE_STR_TABLE_NAME, tableName)
				.replace(REPLACE_STR_KEY_LIST, keyListBuilder.toString()));
		
		if (!conditionMap.isEmpty()) {
			sqlBuilder.append(SQL_WHERE);
			StringBuilder conditionBuilder = new StringBuilder();
			Set<Entry<String, String>> entrySet = conditionMap.entrySet();
			Iterator<Entry<String, String>> iterator = entrySet.iterator();
			
			boolean isFirst = true;
			while (iterator.hasNext()) {
				Entry<String, String> conditionEntry = iterator.next();
				if (!isFirst) {
					conditionBuilder.append(SQL_AND);
					isFirst = false;
				}
				conditionBuilder.append(
						SQL_CONDITION.replace(REPLACE_STR_FIELD_NAME, conditionEntry.getKey())
								     .replace(REPLACE_STR_FIELD_VALUE, conditionEntry.getValue()));
			}
			sqlBuilder.append(conditionBuilder.toString());
		}
		
		sqlBuilder.append(SQL_DELIMITER);
		sqlBuilder.append(System.getProperty("line.separator"));
		
		SqlCommand command = new SqlCommand(sqlBuilder.toString());
		return command;
	}
}
