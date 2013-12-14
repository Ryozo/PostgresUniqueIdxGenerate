package uidxgenerator.parser;

import static uidxgenerator.constants.SqlConstants.MULTILINE_COMMENT_PREFIX;
import static uidxgenerator.constants.SqlConstants.MULTILINE_COMMENT_SUFFIX;
import static uidxgenerator.constants.SqlConstants.SINGLELINE_COMMENT_PREFIX;
import static uidxgenerator.constants.SqlConstants.STRING_LITERAL;

/**
 * SQL�̏�Ԃ��Ǘ�����.
 * @author W.Ryozo
 * @version 1.0
 */
public class SQLState {
	/** SQL�̌��݈ʒu���R�����g�����ł��邱�Ƃ�\���t���O */
	private boolean inCommnet = false;
	
	/** SQL�̌��݈ʒu�������񃊃e�������ł��邱�Ƃ�\�� */
	private boolean inStringLiteral = false;
	
	/**
	 * ��Ԃ��X�V����B
	 * @param decisionTargetSql
	 */
	void updateState(String decisionTargetSql) {
		// TODO ���݂̏�����1�s�P�ʂœ����\�b�h���Ăяo�����O��ƂȂ��Ă���B�i1�s�R�����g�̊J�n/�I���Ɋւ����񂪎����\�b�h�̌Ăяo���Ɉ����p����Ȃ��j�s���̌Ăяo���ˑ��ɂȂ�Ȃ��悤�ɂ���B
		// �L�[���[�h�̗L�����m�F����B
		if (decisionTargetSql == null
				|| decisionTargetSql.trim().length() == 0) {
			return;
		}
		
		// �ΏۃL�[���[�h�̗L���𔻒肷��B
		if (inCommnet) {
			// �R�����g�����ł���ꍇ�A�R�����g�̏I�[��T��
			int endCommentIndex = decisionTargetSql.indexOf(MULTILINE_COMMENT_SUFFIX);
			if (endCommentIndex != -1) {
				inCommnet = false;
				// �㑱�̕���������������]������B
				this.updateState(decisionTargetSql.substring(endCommentIndex + MULTILINE_COMMENT_SUFFIX.length()));
			}
		} else if (inStringLiteral) {
			// �R�����g���ł���A�����e�����ł���Ƃ������\�L�͕s�ł���B
			// TODO �G�X�P�[�v������ɑΉ��H
			// String�������`�ł���ꍇ�AString���e�����̏I�[��T���B
			int endStringLiteralIndex = decisionTargetSql.indexOf(STRING_LITERAL);
			if (endStringLiteralIndex != -1) {
				inStringLiteral = false;
				// �㑱�̕��������������]������B
				this.updateState(decisionTargetSql.substring(endStringLiteralIndex + STRING_LITERAL.length()));
			}
		} else {
			// �R�����g�����ł������񃊃e�������ł��Ȃ��ꍇ
			int startSingleCommentIndex = decisionTargetSql.indexOf(SINGLELINE_COMMENT_PREFIX);
			int startMultiCommentIndex = decisionTargetSql.indexOf(MULTILINE_COMMENT_PREFIX);
			int startStringLiteralIndex = decisionTargetSql.indexOf(STRING_LITERAL);
			int minimumIndex = getMinimumOfPositive(startSingleCommentIndex, startMultiCommentIndex, startStringLiteralIndex);
			if (minimumIndex < 0) {
				// �R�����g�������e������������`����Ă��Ȃ��B
				// �X�e�[�^�X�X�V���������I���B
				return ;
			} else if (startSingleCommentIndex == minimumIndex) {
				// ���߂�1�s�R�����g�̏ꍇ
				// ���̍s�̎c��̍s�͂��ׂăR�����g�ł���B
				// ��Ԃ��X�V�����A�������I������
				return ;
			} else if (startMultiCommentIndex == minimumIndex) {
				// ���߂������s�R�����g�̊J�n�ł������ꍇ
				// ����s���ɃR�����g�̏I�[�����邩�`�F�b�N
				int endMultiCommentIndex = decisionTargetSql.indexOf(MULTILINE_COMMENT_SUFFIX, startMultiCommentIndex + MULTILINE_COMMENT_PREFIX.length());
				if (endMultiCommentIndex != -1) {
					// �R�����g�̏I�[�����݂���ꍇ
					// �R�����g�܂��͕����񃊃e�����̏I���Ȍ��؂����Ĕ���������������{ ����s���ŃR�����g�܂��͕����񃊃e�����͏I�����Ă��邩��A�X�e�[�^�X�X�V�s�v�B
					this.updateState(decisionTargetSql.substring(endMultiCommentIndex + MULTILINE_COMMENT_SUFFIX.length()));
				} else {
					// �R�����g�܂��͕����񃊃e�����̏I�[�����݂��Ȃ��ꍇ
					// �Y���s���ŃR�����g�܂��͕����񃊃e�����I�����Ă��Ȃ����߁A�X�e�[�^�X���X�V����B
					inCommnet = true;
					return ;
				}
			} else if (startStringLiteralIndex == minimumIndex) {
				// ���߂������񃊃e�����̊J�n�ł���ꍇ
				// ����s���ɕ����񃊃e�����̏I�[�����邩�`�F�b�N
				int endStringLiteralIndex = decisionTargetSql.indexOf(STRING_LITERAL, startStringLiteralIndex + STRING_LITERAL.length());
				if (endStringLiteralIndex != -1) {
					// �����񃊃e�����̏I�������݂���ꍇ
					// �����񃊃e�����̏I�[�Ȍ��؂����Ĕ���������������{
					// ����s���ŕ����񃊃e�����͏I�����Ă��邩��X�e�[�^�X�X�V�s�v
					this.updateState(decisionTargetSql.substring(endStringLiteralIndex + STRING_LITERAL.length()));
				} else {
					// �����񃊃e�����̏I�������݂��Ȃ��ꍇ
					// �����񃊃e�����͕����s�ɂ킽���ċL�q����Ă��邽�߁A�X�e�[�^�X���X�V����B
					inStringLiteral = true;
					return ;
				}
			}
		}
	}

	private int getMinimumOfPositive(int... targets) {
		if (targets == null || targets.length == 0) {
			throw new IllegalArgumentException("targets is null or empty");
		}
		boolean foundPositiveNum = false;
		int a = Integer.MAX_VALUE;
		for (int target : targets) {
			if (target < 0) {
				continue;
			}
			if (target == 0) {
				// 0��������ŏ��l�m��B
				return 0;
			}
			foundPositiveNum = true;
			if (target < a) {
				a = target;
			}
		}
		
		// ���̐������������΍ŏ��̗v�f��Ԃ��B
		return foundPositiveNum ? a : targets[0];
	}

	/**
	 * TODO javadoc
	 * @return
	 */
	boolean isEffective() {
		// �R�����g�̒��łȂ��A�������񃊃e�����̒��ł��Ȃ��B
		return !inCommnet && !inStringLiteral;
	}
}
