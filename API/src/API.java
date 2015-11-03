import java.util.ArrayList;
import java.util.Arrays;

import constant.Constant;

//import com.sun.org.apache.xml.internal.resolver.CatalogManager;

import recordmanager.RM_FileHandler;
import recordmanager.RM_FileScan;
import recordmanager.RM_Manager;
import recordmanager.RM_Record;
import sun.awt.SunHints.Value;
import CatalogManager.src.*;
import Interpreter.*;

public class API {
	static API manager = new API();
	private static RM_Manager rm = RM_Manager.getInstance();
	private static CatalogManager cm = CatalogManager.getInstance();
	
	private static byte[] _att;
	private static API Instant = new API();
	public static API getInstance() { return manager;}

	/*table_Name requires */
	public void create_table(String table_Name, String primary_Key, ArrayList<Value> attr) throws Exception{
		if (!cm.isTableExist(table_Name)) {
			try {
				cm.addTable(table_Name, attr.size(), attr, primary_Key);
				rm.createFile(table_Name, attr.size() * 256);
			} catch  (Exception e) {
				throw e;
			} finally {
				/*useless*/
			}
		} else
			throw new Exception("Error: the table name has been already used.");
	}

	public void drop_table(String table_Name) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				rm.destoryFile(table_Name);
			} catch  (Exception e) {
				throw e;
			} finally {
				/*useless*/
			}
		} else
			throw new Exception("Error: do not have this table.");
	}
	
	public void select_from (String table_Name, ArrayList<CondExpr> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				RM_FileHandler rmf = rm.openFile(table_Name);
				if (attr.size() == 1) {
					if (cm.TestAttr(table_Name, 0, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getLength())){ /*The last parameter may have some problem.*/
						Constant.TYPE type;
						int length;
						switch (attr.get(0).getValue().getType()) {
						case 0: type = Constant.TYPE.INT; length = 4;break;
						case 1: type = Constant.TYPE.STRING; length = attr.get(0).getLength();break;	/*This may have some problem about attrbute's length. */
						case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
						}
						
						int offset = cm.findOffset(table_Name, attr.get(0).getID());
						
						Constant.COMP_OP op;
						switch (attr.get(0).getOP()) {
						case "=": op = Constant.COMP_OP.EQ_OP; break;
						case ">": op = Constant.COMP_OP.GT_OP; break;
						case "<": op = Constant.COMP_OP.LT_OP; break;
						case "<>": op = Constant.COMP_OP.NE_OP; break;
						case ">=": op = Constant.COMP_OP.GT_OP; break;
						case "<=": op = Constant.COMP_OP.LE_OP; break;
						}
						RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(attr.get(0).getValue()));
						RM_Record rmr = rmfs.getNextRec();
						while (rmr != null) {
							System.out.println(rmr.getData());
							rmr = rmfs.getNextRec();
						}
					} else 
						throw new Exception("Error: the where condition is wrong!");
				} else {
					/*	Use Index...	*/
				}
				
			} catch (Exception e) {
				
			} finally {
				
			}
		} else 
			throw new Exception("do not have this table");
	}

	public void insert_table(String table_Name, ArrayList<Value> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			boolean judge = true;
			for (int i = 0; i < attr.size(); i++)
			if (!cm.TestAttrOnOrder(table_Name, i, attr.get(i).getType(), attr.get(i).getLength())) {
				judge = false;
				break;
			}
			if (judge) {
				try {
					RM_FileHandler rmf = rm.openFile(table_Name);
					rmf.insertRec(String2Byte(attr));
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
			} else
				throw new Exception("Error: attributes are not correspongding to the table.");
		} else 
			throw new Exception("Error: do not have this table.");
	}
	
	public void delete_record(String table_Name, ArrayList<CondExpr> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
				try {
					RM_FileHandler rmf = rm.openFile(table_Name);
					boolean judge = true;
					for (int i = 0; i < attr.size(); i++)
					if (!cm.TestAttr(table_Name, attr.get(i).getID(),attr.get(i).getValue().getType(),attr.get(i).getValue().getLength())) {
						judge = false;
						break;
					}
					if (judge) {
						RM_FileHandler rmf = rm.openFile(table_Name);
						if (attr.size() == 1) {
							if (cm.TestAttr(table_Name, 0, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getLength())){ /*The last parameter may have some problem.*/
								Constant.TYPE type;
								int length;
								switch (attr.get(0).getValue().getType()) {
								case 0: type = Constant.TYPE.INT; length = 4;break;
								case 1: type = Constant.TYPE.STRING; length = attr.get(0).getLength();break;	/*This may have some problem about attrbute's length. */
								case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
								}
								
								int offset = cm.findOffset(table_Name, attr.get(0).getID());
								
								Constant.COMP_OP op;
								switch (attr.get(0).getOP()) {
								case "=": op = Constant.COMP_OP.EQ_OP; break;
								case ">": op = Constant.COMP_OP.GT_OP; break;
								case "<": op = Constant.COMP_OP.LT_OP; break;
								case "<>": op = Constant.COMP_OP.NE_OP; break;
								case ">=": op = Constant.COMP_OP.GT_OP; break;
								case "<=": op = Constant.COMP_OP.LE_OP; break;
								}
								RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(attr.get(0).getValue()));
								RM_Record rmr = rmfs.getNextRec();
								while (rmr != null) {
									rmf.deleteRec(rmr.getRid());
									rmr = rmfs.getNextRec();
								}
							} else 
								throw new Exception("Error: the where condition is wrong!");
						} else {
							/*	Use Index...	*/
						}
					} else
						throw new Exception("Error: attributes are not corresponding to the table.");
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
		} else
			throw new Exception("Error: do not have this table. "); 
	}
	
	private static byte[] String2Byte(ArrayList<Value> attr) {
		byte[] a;
		a = new byte[attr.size() * 256];
		for (int i = 0; i < attr.size(); i++){
			char[] u = attr.get(i).getData().toCharArray();
			for (int j = 0; j < u.length; j++)
				a[i * 256 + j] = (byte) (u[j] & 0xFF);
		}
		return a;
	}
}
