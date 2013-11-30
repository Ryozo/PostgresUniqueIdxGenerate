package uidxgenerator.entity;

import java.util.List;
import java.util.Set;

/**
 * CreateTable����\��Entity�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class CreateTableSql extends SqlCommand {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Unique����t�^�Ώۂ̍��ڈꗗ�B<br />
	 * UNIQUE�ΏۂƂ���J�������̈ꗗ��ێ�����Set�ō\�������B<br />
	 * �P��UNIQUE�̏ꍇ��Set��1�v�f�̂ݎ����A����UNIQUE�̏ꍇ�A
	 * Set��UNIQUE�L�[���\�����邷�ׂẴJ��������ێ�����B
	 */
	private List<Set<String>> uniqueKeyList;
	
	public CreateTableSql(String command, List<Set<String>> uniqueKeyList) {
		super(command);
		this.uniqueKeyList = uniqueKeyList;
	}
	
	/**
	 * �Y��SQL�����ێ�����Unique������폜���܂��B
	 */
	public void removeUniqueConstraint() {
		// �P����Unique�̍폜
	}
}
