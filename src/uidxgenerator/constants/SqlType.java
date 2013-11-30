package uidxgenerator.constants;

/**
 * SQLの種別を表す列挙子です
 * @author W.Ryozo
 * @version 1.0
 */
public enum SqlType {
	CREATETABLE,  // Create Table文
	CREATEINDEX,  // Create Index文
	OTHER,        // その他のSQL文
}
