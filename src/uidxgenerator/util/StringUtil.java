package uidxgenerator.util;

/**
 * String�֘A��Util�N���X�ł��B
 * @author W.Ryozo
 * @version 1.0
 */
public class StringUtil {
	
	/**
	 * �����̕�����null�܂��͋󕶎��ł��邱�Ƃ��`�F�b�N���܂��B
	 * @param target �`�F�b�N�Ώۂ̕�����
	 * @return �`�F�b�N����
	 */
	public static boolean isNullOrEmpty(String target) {
		return target == null || target.length() == 0;
	}
	
	/**
	 * �󔒕���(���p�A�S�p�X�y�[�X�A�^�u�A���s�A���A�j��Trim���܂��B
	 * @param target trim�Ώۂ̕�����
	 * @return Trim�㕶����
	 */
	public static String trimWithSpaceString(String target) {
		// TODO ���K�\���̃}�b�`��ǉ��B
		return target.trim();
	}

}
