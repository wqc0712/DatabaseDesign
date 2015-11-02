import java.util.ArrayList;
import java.util.Arrays;

//import com.sun.org.apache.xml.internal.resolver.CatalogManager;

import recordmanager.RM_FileHandler;
import recordmanager.RM_Manager;
import sun.awt.SunHints.Value;
import CatalogManager.src.*;
import Interpreter.Main;

public class API {
	static API manager = new API();
	private static RM_Manager rm = RM_Manager.getInstance();
	private static CatalogManager cm = CatalogManager.getInstance();
		
	private static byte[] _att;
	private static API Instant = new API();
	public static API getInstance() { return manager;}

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
		
	}

	public void insert_table(String table_Name, ArrayList<Value> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			if (cm.TestAttrOnOrder(table_Name, attr)) {											/* The condition here is wrong */
				try {
					static RM_FileHandler rmf = rm.openFile(table_Name);
					rmf.insertRec(attr.toByte());												/* Here I need a toByte() funciotn to translate ArrayList into byte array. */
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
			if (cm.TestAttrOnOrder(table_Name, attr)) {
				try {
					static RM_FileHandler rmf = rm.openFile(table_Name);
					boolean judge = true;
					for (int i = 0; i < attr.size(); i++)
					if TestAttr(table_Name, attr.get(i),getID(),){								/* Here I need ask Qichen about the function he provided. */
						judge = false;
						break;
					}
					if (judge) {
																								/* I need a function about knowwing weather it comes to the end of table*/
					}
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
			} else 
				throw new Exception("Error: attributes are not correspongding to the table.");
		} else
			throw new Exception("Error: do not have this table. "); 
	}
	
	private static byte[] string2byte(ArrayList<String> attr) {
		byte[] a;
		a = new byte[attr.size() * 256];
		for (int i = 0; i < attr.size(); i++){
			String u = attr.get(i);
			for (int j = 0; j < attr.size(); j++)
				a[i * 256 + j] = (byte)(u[j],toByte());
		}
		return a;
	}
}
