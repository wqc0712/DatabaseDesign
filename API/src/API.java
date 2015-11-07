import java.util.ArrayList;
import java.util.Arrays;




public class API {
	static API manager = new API();
	private static RM_Manager rm = RM_Manager.getInstance();
	private static IndexManager im = IndexManager.getInstance();
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
				System.out.println("Successfully create table "+table_Name+".");

				Value attr_ = cm.GetAttrInformation(table_Name, cm.findOffset(table_Name, primary_Key));
				Index new_Index = new Index();
				new_Index.indexName = "_" + table_Name;
				new_Index.tableName = table_Name;
				new_Index.column = cm.GetTableInformation(table_Name).getLength();
				new_Index.columnLength = attr_.getLength();
				new_Index.rootNum = 0;
				new_Index.blockNum = 0;
				im.createIndex(table_Name, new_Index);
				//System.out.println("Successfully create index "+index_Name+".");
			} catch  (Exception e) {
				System.out.println(e);
//				throw e;
			}
		} else
			System.out.println("Error: the table name has been already used.");
//			throw new Exception("Error: the table name has been already used.");
	}

	public void create_index(String index_Name, String table_Name, String attr_Name) throws Exception {
		if (cm.isTableExist(table_Name)) {
			if (cm.findOffset(table_Name, attr_Name) != -1) {
				if (!cm.isIndexExist(index_Name)) {
					try {
						cm.addIndex(index_Name, table_Name, attr_Name);						
						Value attr_ = cm.GetAttrInformation(table_Name, cm.findOffset(table_Name, attr_Name));
						Index new_Index = new Index();
						new_Index.indexName = index_Name;
						new_Index.tableName = table_Name;
						new_Index.column = cm.GetTableInformation(table_Name).getLength();
						new_Index.columnLength = attr_.getLength();
						new_Index.rootNum = 0;
						new_Index.blockNum = 0;
						im.createIndex(table_Name, new_Index);
//						rm.createFile(table_Name, attr.size() * 256);
						System.out.println("Successfully create index "+index_Name+".");
					} catch  (Exception e) {
						System.out.println(e);
					}					
				} else
					System.out.println("Error: Index " + index_Name + " already existed.");
			} else
				System.out.println("Error: attribute " + attr_Name + " does not exist in table "+table_Name+".");
		} else
			System.out.println("Error: do not have this table");
	}

	public void drop_table(String table_Name) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				rm.destoryFile(table_Name);
				CatalogManager.getInstance().dropTable(table_Name);
				System.out.println("Drop table "+table_Name+" successfully.");
			} catch  (Exception e) {
				System.out.println(e);
			}
		} else
			System.out.println("Error: do not have this table.");
//			throw new Exception("Error: do not have this table.");
	}
	ArrayList<RM_Record> intersect(ArrayList<RM_Record> set1,ArrayList<RM_Record> set2) {
		ArrayList<RM_Record> result = new ArrayList();
		for (int i = 0; i < set1.size(); i++) 
			for (int j = 0; j < set2.size(); j++) {
				if (set1.get(i).getRid().equal(set2.get(j).getRid()))
					result.add(set1.get(i));
					
			}
		return result;
	}
	
	public void drop_index(String index_Name) throws Exception {
		if (cm.isIndexExist(index_Name)) {
			cm.dropIndex(index_Name);
			im.dropIndex(index_Name);
//			BufferManager.dropTable(index_Name+".index");
		} else 
			System.out.println("Error: do not have this index");
	}
	
	public void select_from (String table_Name, ArrayList<CondExpr> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				RM_FileHandler rmf = rm.openFile(table_Name);
				if (attr.size() == 0) {
					RM_FileScan rmfs = new RM_FileScan(rmf, Constant.TYPE.INT, 4, 0, Constant.COMP_OP.NO_OP, PF_Manager.intTobyteArray(1));
					RM_Record rmr = rmfs.getNextRec();
					prePrintOut(table_Name);
					while (rmr != null) {
						formatPrintOut(rmr, table_Name);
						rmr = rmfs.getNextRec();
					}					
				} else if (attr.size() == 1) {							/*	If there is only one condition in where statement.	*/
					if (cm.TestAttr(table_Name, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getValue().getLength())){ /*The last parameter may have some problem.*/
						Constant.TYPE type = null;
						int length = 0;
						switch (attr.get(0).getValue().getType()) {
						case 0: type = Constant.TYPE.INT; length = 4;break;
						case 1: type = Constant.TYPE.STRING; length = attr.get(0).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
						case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
						}
						
						int offset = cm.findOffset(table_Name, attr.get(0).getID())*256;
						
						Constant.COMP_OP op = null;
						switch (attr.get(0).getOP()) {
						case "=": op = Constant.COMP_OP.EQ_OP; break;
						case ">": op = Constant.COMP_OP.GT_OP; break;
						case "<": op = Constant.COMP_OP.LT_OP; break;
						case "<>": op = Constant.COMP_OP.NE_OP; break;
						case ">=": op = Constant.COMP_OP.GT_OP; break;
						case "<=": op = Constant.COMP_OP.LE_OP; break;
						}
						ArrayList<Value> temp = new ArrayList<>();
						temp.add(attr.get(0).getValue());
						RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
						RM_Record rmr = rmfs.getNextRec();
						prePrintOut(table_Name);
						while (rmr != null) {
							formatPrintOut(rmr, table_Name);
//							System.out.println(rmr.getData());			/*this place has bugs. must have format.*/
							rmr = rmfs.getNextRec();
						}
					} else
						System.out.println("Error: the where condition is wrong!");
//						throw new Exception("Error: the where condition is wrong!");
				} else {								/*	If there is more than one condition in where statement.	*/
					ArrayList<RM_Record> setCollection;
					ArrayList<RM_Record> result = new ArrayList();
					for (int i = 0; i < attr.size(); i++) {
					if (cm.TestAttr(table_Name, attr.get(i).getID(), attr.get(i).getValue().getType(), attr.get(i).getValue().getLength())){ /*The last parameter may have some problem.*/
						Constant.TYPE type = null;
						int length = 0;
						switch (attr.get(i).getValue().getType()) {
						case 0: type = Constant.TYPE.INT; length = 4;break;
						case 1: type = Constant.TYPE.STRING; length = attr.get(i).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
						case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
						}
						
						int offset = cm.findOffset(table_Name, attr.get(i).getID())*256;
						
						Constant.COMP_OP op = null;
						switch (attr.get(i).getOP()) {
						case "=": op = Constant.COMP_OP.EQ_OP; break;
						case ">": op = Constant.COMP_OP.GT_OP; break;
						case "<": op = Constant.COMP_OP.LT_OP; break;
						case "<>": op = Constant.COMP_OP.NE_OP; break;
						case ">=": op = Constant.COMP_OP.GT_OP; break;
						case "<=": op = Constant.COMP_OP.LE_OP; break;
						}
						ArrayList<Value> temp = new ArrayList<>();
						temp.add(attr.get(i).getValue());
						RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
						RM_Record rmr = rmfs.getNextRec();
						setCollection = new ArrayList<RM_Record>();
						while (rmr != null) {
							setCollection.add(rmr);							
//							System.out.println(rmr.getData());			/*this place has bugs. must have format.*/
							rmr = rmfs.getNextRec();
						}
						if (i == 0) {
							result = setCollection;
						} else {
							result = intersect(result,setCollection);
						}
					} else
						System.out.println("Error: the where condition is wrong!");
//						throw new Exception("Error: the where condition is wrong!");
					}
					prePrintOut(table_Name);
					for (int i = 0; i < result.size(); i++) {
						formatPrintOut(result.get(i), table_Name);
					}
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else
			System.out.println("do not have this table.");
//			throw new Exception("do not have this table");
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
					rm.closeFile(rmf);
					System.out.println("Successfully insert into table "+table_Name+".");
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
			} else
				System.out.println("Error: attributes are not cooresponding to the table.");
//				throw new Exception("Error: attributes are not corresponding to the table.");
		} else
			System.out.println("Error: do not have this table.");
//			throw new Exception("Error: do not have this table.");
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
						if (attr.size() == 0) {
							RM_FileScan rmfs = new RM_FileScan(rmf, Constant.TYPE.INT, 4, 0, Constant.COMP_OP.NO_OP, PF_Manager.intTobyteArray(1));
							RM_Record rmr = rmfs.getNextRec();
							int count = 0;
							while (rmr != null) {
								count ++;
								rmf.deleteRec(rmr.getRid());
								rmr = rmfs.getNextRec();
							}
							System.out.println("Successfully delete "+count+" lines.");
						} else if (attr.size() == 1) {						/*	If there is only one condition in where statement.	*/
							if (cm.TestAttr(table_Name, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getValue().getLength())){ /*The last parameter may have some problem.*/
								Constant.TYPE type = null;
								int length = 0;
								switch (attr.get(0).getValue().getType()) {
								case 0: type = Constant.TYPE.INT; length = 4;break;
								case 1: type = Constant.TYPE.STRING; length = attr.get(0).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
								case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
								}
								
								int offset = cm.findOffset(table_Name, attr.get(0).getID())*256;
								
								Constant.COMP_OP op = null;
								switch (attr.get(0).getOP()) {
								case "=": op = Constant.COMP_OP.EQ_OP; break;
								case ">": op = Constant.COMP_OP.GT_OP; break;
								case "<": op = Constant.COMP_OP.LT_OP; break;
								case "<>": op = Constant.COMP_OP.NE_OP; break;
								case ">=": op = Constant.COMP_OP.GT_OP; break;
								case "<=": op = Constant.COMP_OP.LE_OP; break;
								}
								ArrayList<Value> temp = new ArrayList<>();
								temp.add(attr.get(0).getValue());
								RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
								RM_Record rmr = rmfs.getNextRec();
								int count = 0;
								while (rmr != null) {
									count ++;
									rmf.deleteRec(rmr.getRid());
									rmr = rmfs.getNextRec();
								}
								System.out.println("Successfully delete "+count+" lines.");
							} else
								System.out.println("Error: where condition is wrong!");
//								throw new Exception("Error: the where condition is wrong!");
						} else {							/*	If there is more than one condition in where statement.	*/
							ArrayList<RM_Record> setCollection;
							ArrayList<RM_Record> result = new ArrayList();
							for (int i = 0; i < attr.size(); i++) {
							if (cm.TestAttr(table_Name, attr.get(i).getID(), attr.get(i).getValue().getType(), attr.get(i).getValue().getLength())){ /*The last parameter may have some problem.*/
								Constant.TYPE type = null;
								int length = 0;
								switch (attr.get(i).getValue().getType()) {
								case 0: type = Constant.TYPE.INT; length = 4;break;
								case 1: type = Constant.TYPE.STRING; length = attr.get(i).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
								case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
								}
								
								int offset = cm.findOffset(table_Name, attr.get(i).getID())*256;
								
								Constant.COMP_OP op = null;
								switch (attr.get(i).getOP()) {
								case "=": op = Constant.COMP_OP.EQ_OP; break;
								case ">": op = Constant.COMP_OP.GT_OP; break;
								case "<": op = Constant.COMP_OP.LT_OP; break;
								case "<>": op = Constant.COMP_OP.NE_OP; break;
								case ">=": op = Constant.COMP_OP.GT_OP; break;
								case "<=": op = Constant.COMP_OP.LE_OP; break;
								}
								ArrayList<Value> temp = new ArrayList<>();
								temp.add(attr.get(i).getValue());
								RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
								RM_Record rmr = rmfs.getNextRec();
								setCollection = new ArrayList<RM_Record>();
								while (rmr != null) {
									setCollection.add(rmr);							
//									System.out.println(rmr.getData());			/*this place has bugs. must have format.*/
									rmr = rmfs.getNextRec();
								}
								if (i == 0) {
									result = setCollection;
								} else {
									result = intersect(result,setCollection);
								}
							} else
								System.out.println("Error: the where condition is wrong!");
//								throw new Exception("Error: the where condition is wrong!");
							}
							for (int i = 0; i < result.size(); i++) {
								// TODO
								rmf.deleteRec(result.get(i).getRid());
							}
							System.out.println("Successfully delete "+result.size()+" lines.");
						}
					} else
						System.out.println("Error: attributes are not corresponding to the table.");
//						throw new Exception("Error: attributes are not corresponding to the table.");
				} catch (Exception e) {
					System.out.println(e);
				}
		} else
			System.out.println("Error: do not have this table.");
//			throw new Exception("Error: do not have this table. "); 
	}
	private static void prePrintOut(String tableName) {
		Value table_ = cm.GetTableInformation(tableName);
		Value attr_;
		for (int i = 0; i < table_.getLength(); i++) {
			attr_ = cm.GetAttrInformation(tableName, i);
			System.out.print(attr_.getData()); 
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < table_.getLength(); i++) {
			attr_ = cm.GetAttrInformation(tableName, i);
			for (int j = 0; j < (attr_.getLength() + 1)/2; j++)
				System.out.print("- ");
		}
		System.out.println();
	}
	
	private static void formatPrintOut(RM_Record rmr, String tableName) {	
		Value table_ = cm.GetTableInformation(tableName);
		Value attr_;
		for (int i = 0; i < table_.getLength(); i++) {
			attr_ = cm.GetAttrInformation(tableName, i);
			byte [] temp = new byte[256];
			System.arraycopy(rmr.getData(), i * 256, temp, 0, 256);
			switch (attr_.getType()) {
			case 0:{
				System.out.print(PF_Manager.byteArrayToint(temp) + "\t");
				break;
			}
			case 1:{
				System.out.print("\'");
				for (int j = 0; j < 256; j++) {
					if (temp[j] != 0) {
						System.out.print((char)(temp[j]));
					} else
						break;
				}
				System.out.print("\'" + "\t");
				break;
			}
			case 2:{
				System.out.print(PF_Manager.byteArrayToDouble(temp) + "\t");
				break;
			}
			}
		}
		System.out.println();
	}
	private static byte[] String2Byte(ArrayList<Value> attr) {
		byte[] a;
		a = new byte[attr.size() * 256];
		for (int i = 0; i < attr.size(); i++){
			String u = attr.get(i).getData();
			switch (attr.get(i).getType()) {
			case 0:{
				int uu = Integer.parseInt(u); 
				byte[] b = PF_Manager.intTobyteArray(uu);
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				break;
			}
			case 1:{
				byte[] b = u.getBytes();
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				for (int j = b.length; j < 256; j++)
					a[i * 256 + j] = 0;
				break;
			}
			case 2:{
				double uu = Double.parseDouble(u); 
				byte[] b = PF_Manager.doubleToByteArray(uu);
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				break;
			}
			}
		}
		return a;
	}
}
