package uidxgenerator.entity;

import java.io.Serializable;

import uidxgenerator.constants.SqlType;
import uidxgenerator.util.StringUtil;

/**
 * SQL����\���N���X�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class SqlCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** SQL�{�� */
	private String command;
	
	/** SQL��� */
	private SqlType type;
	
	/**
	 * SQL����SQL��ʂ𗘗p���ăC���X�^���X���쐬���܂��B
	 * @param command SQL��
	 * @param type SQL�̎��
	 */
	public SqlCommand(String command) {
		// TODO Builder�N���X�������A���Ɠ��e�C��
		if (StringUtil.isNullOrEmpty(command)) {
			throw new IllegalArgumentException();
		}
		this.command = command;
		this.type = SqlType.CREATETABLE;
	}
	
	/**
	 * SQL�{�����擾���܂��B
	 * @return
	 */
	public String getSqlCommand() {
		return command;
	}
	
	/**
	 * SQL��ʂ��擾���܂��B
	 * @return
	 */
	public SqlType getType() {
		return type;
	}

}
