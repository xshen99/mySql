import java.util.*;
import java.io.*;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class Main {
	
	public static void main(String[] args) {
		
		run();
		
	}
	
	public static void run() {
		
		while (true) {
			
			System.out.print("$> ");
			String input = "";
			Scanner scanner = new Scanner(System.in);		
			while (!input.contains(";")) input = input + " " + scanner.nextLine();		//Assume the statements end with ';'
			
			try {
				CCJSqlParser ctp = new CCJSqlParser(new StringReader(input));
				Statement query = ctp.Statement();

				if (query instanceof CreateTable) {		
					CreateTable createtable=(CreateTable) query;
					Table2 table = Table2.getTable(createtable, inMemory);
					database.put(table.getName(), table);				
				}
				
				else if (query instanceof Select) {
					
					Select select = (Select) query;
					PlainSelect ps = (PlainSelect) select.getSelectBody();					
					Iterator2 it = Iterator2.getIterator(ps, database);	
				}
				
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			scanner.close();
		}
		
	}
}

