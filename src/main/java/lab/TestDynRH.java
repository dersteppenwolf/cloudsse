/**
*	Modificado	por:	 Marly Johana Piedrahita  (mj.piedrahita), Juan Carlos Méndez (jc.mendez)
*/

package lab;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.TreeMultimap;
import org.crypto.sse.DynRH;
import org.crypto.sse.TextExtractPar;
import org.crypto.sse.TextProc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TestDynRH {

	private static BufferedReader reader;

	private static Hashtable<String, String> directories = new Hashtable();

	public static void menu() {

		reader = new BufferedReader(new InputStreamReader(System.in));

		int option = -1;

		while (option != 0) {
			try {
				System.out.println("---------------SSE Lab - DynnRH (Dynamic Implementation)---------------");
				System.out.println("Choose one of the following options: ");
				System.out.println("1: Test indexing and query");
				System.out.println("2: Test files encryption and query over those files");
				System.out.println("0: Return");
				System.out.println("-----------------------------------------------------------");

				option = Integer.parseInt(reader.readLine());

				switch (option) {
				case 1:
					test1();
					break;
				case 2:
					test2();
					break;
				}
			} catch (InputMismatchException | NumberFormatException ime) {
				try {
					System.out.println("You did not select a valid number.");
					System.out.println("Put any letter and then press enter to continue");
					reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void test1()
			throws InputMismatchException, IOException, NumberFormatException, ClassNotFoundException {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("--------------> Choose one of the following options: <------------------");
		System.out.println("1. Create a new index over your files in order to perform SSE over them.");
		System.out.println("2. Work with an existing index.");
		System.out.println("------------------------------------------------------------------------");

		int option = Integer.parseInt(reader.readLine());

		byte[] key = null;
		HashMap<String, byte[]> index = null;
		if (option == 1) {
			key = generateKey();

			if (key == null)
				return;

			System.out.println("Enter the absolute path name of the FOLDER that contains the files to make searchable");
			String pathName = reader.readLine();
			index = buildIndex(key, pathName);

			if (index == null)
				return;
		} else if (option == 2) {
			System.out.println("Enter the absolute path name of the FILE where you previously saved your index.");
			String indexPath = reader.readLine();
			index = (HashMap<String, byte[]>) Utils.readObject(indexPath);

			System.out.println(
					"Enter the absolute path name of the FILE that contains the secret key needed to perform your queries.");
			String keyPath = reader.readLine();
			key = (byte[]) Utils.readObject(keyPath);
		} else {
			throw new InputMismatchException();
		}

		int test1Option = -1;

		while (test1Option != 0) {
			System.out.println("-----------------------------------------------------------");
			System.out.println("---------> Choose one of the following options: <----------");
			System.out.println("1: Add new files to your index");
			System.out.println("2: Delete key-value tuples from your index");
			System.out.println("3: Perform queries over your files using SSE");
			System.out.println("0: Return");
			System.out.println("-----------------------------------------------------------");

			test1Option = Integer.parseInt(reader.readLine());

			switch (test1Option) {
			case 0:
				break;
			case 1:
				System.out.println("Enter the absolute path name of the folder that contains the files to add:");
				String pathName = reader.readLine();
				updateIndex(key, index, pathName);
				break;
			case 2:
				System.out.println(
						"Enter a keyword that appears in some file whose key-value pair you want to delete (only use lowercase letters):");
				String keywordDelete = reader.readLine();
				List<String> ans = query(key, index, keywordDelete);
				if (ans.size() > 0)
					deleteElement(keywordDelete, index, key);
				else
					System.out.println("The entered keyword was not found in any file.");

				break;
			case 3:
				queryTool(key, index);
				break;
			default:
				System.out.println("You did not select a valid number. Try Again.");
				break;
			}
		}

	}

	public static void test2()
			throws InputMismatchException, IOException, NumberFormatException, ClassNotFoundException {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("--------------> 				test2	       	        <------------------");
		System.out.println("------------------------------------------------------------------------");
		System.out.println("--------------> Choose one of the following options: <------------------");
		System.out.println("1. Encrypt and index a new set of files.");
		System.out.println("2. Work with an existing index and previously encrypted data.");
		System.out.println("------------------------------------------------------------------------");

		int option = Integer.parseInt(reader.readLine());

		byte[] key = null;
		HashMap<String, byte[]> index = null;
		String pathName = "";

		if (option == 1) {
			key = generateKey();

			if (key == null)
				return;

			System.out.println(
					"Enter the absolute path name of the FOLDER that contains the files that you want to encrypt and make searchable.");
			pathName = reader.readLine();
			index = buildIndex(key, pathName);
			
			

			if (index == null)
				return;

			// 2. Work with an existing index and previously encrypted data."
			// Complete  (1)
		} else if (option == 2) {
			System.out.println("Enter the absolute path name of the FILE where you previously saved your index.");
			String indexPath = reader.readLine();
			index = (HashMap<String, byte[]>) Utils.readObject(indexPath);

			System.out.println(	"Enter the absolute path name of the FILE that contains the secret key needed to perform your queries.");
			String keyPath = reader.readLine();
			key = (byte[]) Utils.readObject(keyPath);

			System.out.println("Enter the absolute path name of the FOLDER with the previously encrypted files.");
			pathName = reader.readLine();
			
			System.out.println("------------------------------------------------------------------------");
			System.out.println("--------------> 				Complete  (1)	        <------------------");
			System.out.println("------------------------------------------------------------------------");
			
//			* Debe	completar	los	métodos	test2()	y	queryToolWithDecryption()	de	la	clase	TestDynRH.java,	para	
//			manejar	cifrado	de	los	archivos 	y	búsquedas	sobre	los	mismos.	Esta 	clase	se	encuentra	en	la	siguiente	
//			ruta:	SSELab/src/main/java/lab/
//			• En 	esta	 clase,	debe 	adicionar	código	en	los	lugares	señalados	con	el	indicador	“//Complete”.	
//			• Recuerde	que	queremos	 extender	el	segundo	comando	de	la	segunda	opción	del	menú	 que 	venimos	
//			trabajando	(implementación	dinámica	>	Probar	cifrado	de	archivos	 y	búsqueda	 sobre	esos	 archivos).		
//			Dado	 que	con	este	comando	del	menú es	posible	agregar	archivos 	desde	distintos	directorios,	se	
//			maneja	una	variable	de	tipo	 HashTable	(directories).	Esta	 variable	permite	guardar	información	que	
//			asocia	 el	 nombre	 de	 un	 archivo.	 con	 la	 ruta	 completa	 donde	 se	 encuentra.	 
//			Almacenar	 esta	
//			información	permite,	posteriormente,	descifrar	los	archivos.
			
			//////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////
			// a. Crear	 la	lista	de	archivos 	que	se	encuentran	en	el	directorio	que	el	usuario	ingresa	por	
			// parámetro	(observe 	la	variable	 pathname).	
			ArrayList<File> listOfFile = new ArrayList<File>();
			TextProc.listf(pathName, listOfFile);
			
//			b. A	continuación,	es	necesario	recorrer	 la	lista	de	archivos 	y	adicionar	a	la	tabla	
//			de	hash	 una 	pareja	(llave,valor)	por	cada	 archivo.
//			La	llave	es	el	nombre	del	archivo	y	el	valor	es	
//			la	ruta 	del	directorio	donde	se	encuentra	almacenado.	
			for(File f: listOfFile)
	        {
	            String k = f.getName();
	            String v = f.getPath();
	            System.out.println("****************");
	            System.out.println(k);
	            System.out.println(v);
	            directories.put(k, v);
	        }
			
			
			
//			c. Finalmente,	 cifre	 los	 archivos	 que	 se	 encuentran	 en	 el	 directorio,	 con	 el	 método	
//			correspondiente	de	la	clase	Utils.java.	
//			Recomendación:	Para	 realizar	 este	punto,	puede	revisar	los	métodos	de	la	clase	Utils.java,	
//			donde	se	implementan	los	mecanismos	de	cifrado.	 
//			Asimismo,	para 	tener	una	guía de	cómo	
//			se	utilizan,	puede	consultar	el	método	test2()	de	la	clase	TestRR2Lev.java;	allí 	se	cifraron	
//			archivos.
			
            
            File dir = new File(pathName);
            File[] filelist = dir.listFiles();
            Utils.encryptFiles(filelist, key);
			
			
			//////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////


		} else {
			throw new InputMismatchException();
		}

		int test2Option = -1;

		while (test2Option != 0) {
			System.out.println("-----------------------------------------------------------");
			System.out.println("---------> Choose one of the following options: <----------");
			System.out.println("1: Add new files to your index");
			System.out.println("2: Delete key-value tuples from your index");
			System.out.println("3: Perform queries over your files using SSE");
			System.out.println("0: Return");
			System.out.println("-----------------------------------------------------------");

			test2Option = Integer.parseInt(reader.readLine());

			switch (test2Option) {
			case 0:
				break;
			case 1:
				System.out.println("Enter the absolute path name of the folder that contains the files to add:");
				pathName = reader.readLine();
				updateIndex(key, index, pathName);

//				2. El	segundo	//Complete	corresponde	a	añadir	nuevos	archivos 	al	índice.	Ahora	debe:	
				
//				a. En	 este	 caso,	 nuevamente,	 debe	 crear	 la	 lista	 de	 archivos	 que	 se	 encuentran	 en	 el	
//				directorio	que	el	usuario	ingresa	como	 parámetro.	
				
//				b. Adicionar 	a	la	 tabla	 de	 hash	las	parejas	 (llave,valor)	 para	asociar	los	 nombres	 de	los	
//				archivos	con	la	ruta	donde	se	encuentran.
				
//				c. Finalmente,	cifrar	todos	los	archivos	del	directorio	correspondiente	usando	el	mismo	
//				método	de	la	clase	Utils.java
				
				// Complete (2)
				
				System.out.println("------------------------------------------------------------------------");
				System.out.println("--------------> 				Complete  (2)	        <------------------");
				System.out.println("------------------------------------------------------------------------");
				
				ArrayList<File> listOfFile = new ArrayList<File>();
				TextProc.listf(pathName, listOfFile);
				
				for(File f: listOfFile)
		        {
		            String k = f.getName();
		            String v = f.getParent();
		            System.out.println("****************");
		            System.out.println(k);
		            System.out.println(v);
		            directories.put(k, v);
		        }
				
				File dir = new File(pathName);
	            File[] filelist = dir.listFiles();
	            Utils.encryptFiles(filelist, key);
				
				
				
				break;
			case 2:
				System.out.println(
						"Enter a keyword that appears in some file whose key-value pair you want to delete (only use lowercase letters):");
				String keywordDelete = reader.readLine();
				List<String> ans = query(key, index, keywordDelete);
				if (ans.size() > 0)
					deleteElement(keywordDelete, index, key);
				else
					System.out.println("The entered keyword was not found in any file.");
				break;
			case 3:
				queryToolWithDecryption(key, index);
				break;
			default:
				System.out.println("You did not select a valid number. Try Again.");
				break;
			}
		}

	}

	public static void queryToolWithDecryption(byte[] key, HashMap<String, byte[]> index) throws IOException {
		System.out.println("\n --------------- >> SSE Lab - Query Tool << ---------------\n");
		while (true) {
			System.out.println(":::::::::: SSE Lab - New Query ::::::::::::\n");
			System.out.println("Enter the keyword to search for (only use lowercase letters):");

			String keyword = reader.readLine();

			if (keyword.equals("."))
				break;

			List<String> ans = query(key, index, keyword);

			int size = ans.size();
			if (size > 0) {
				System.out.println("------------------------------------------------------------------------");
				System.out.println("-----> Do you want to decrypt the files returned by your query? <-------");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.println("------------------------------------------------------------------------");

				try {
					int decrypt = Integer.parseInt(reader.readLine());

					if (decrypt == 1) {
						// Complete  (3)
						System.out.println("------------------------------------------------------------------------");
						System.out.println("--------------> 				Complete  (3)	        <------------------");
						System.out.println("------------------------------------------------------------------------");
//						3. El	 último	 //Complete	 está	 en	 el	 método	 queryToolWithDecryption().	 
//						En	 este	 caso	 debe:	
//						descifrar	los	archivos	 que	se	retornaron	como	 producto	de	la	consulta	(revise	el	código	arriba	
//						del	Complete).	Puede	consultar	al	método	 test2()	de	la	clase	
//						TestRR2Lev.java	para	entender		mejor	cómo	invocar	el	método para	descifrar	los	archivos.
//						
//						a. Pida	al	usuario	el	directorio	(con 	la	ruta	completa)	donde	quiere	almacenar	los	archivos	
//						descifrados.	
						
						System.out.println("Enter the absolute path name of the FOLDER where you want to save the decripted files .");
						String pathNameDestiny = reader.readLine();
//						
//						b. Cree	(new)	un	array	par a	almacenar	los	nombres	de	los	archivos	 que	 va	a	descifrar.	
						
						ArrayList<File> listOfFiles = new ArrayList<File>();
						
//						
//						c. Almacene	los	nombres	de	los	archivos,	con	el	path	 completo,	en	el	array	que	acaba	de	
//						crear.	Para	 ello	use	la	lista	de	resultados	del	query	por	palabra	clave	(ans)	y	la	tabla	de	
//						hash 	para 	determinar	la	ruta	 completa	al	archivo.	
						
						for(String filename: ans)
				        {
				            System.out.println("****************");
				            System.out.println(filename);
				            String parentFolder = directories.get(filename);
				            System.out.println(parentFolder);
				            File f = new File(parentFolder, filename);
				            listOfFiles.add(f);
				           
				        } 
//						
//						d. Finalmente,	 descifre	los	archivos.	 Para	ello	 invoque	el	método	apropiado	 de	la	 clase	
//						Utils.java.
						
						 Utils.decryptFiles(listOfFiles.toArray(new File[listOfFiles.size()]), key, pathNameDestiny);
						
						
					}
				} catch (InputMismatchException | NumberFormatException e) {
					System.out.println("\n You did not select a valid number");
					System.out.println("Try again.\n");
				}

			} else {
				System.out.println("The entered keyword was not found in any file.");
			}

			System.out.println(":::::::::::::::::::::::::::::::::::::::::::\n");

			System.out.println(" ----> If you want to STOP querying, enter . (one dot) instead of your keyword \n");

		}

		System.out.println(":::::::::::::::::::::::::::::::::::::::::::\n");
		System.out.println(" ----> If you want to STOP querying, enter . (one dot) instead of your keyword \n");

	}

	public static void queryTool(byte[] key, HashMap<String, byte[]> index) throws IOException {
		System.out.println("\n --------------- >> SSE Lab - Query Tool << ---------------\n");
		while (true) {
			System.out.println(":::::::::: SSE Lab - New Query ::::::::::::\n");
			System.out.println("Enter the keyword to search for (only use lowercase letters):");

			String keyword = reader.readLine();

			if (keyword.equals("."))
				break;

			query(key, index, keyword);

			System.out.println(":::::::::::::::::::::::::::::::::::::::::::\n");
			System.out.println(" ----> If you want to STOP querying, enter . (one dot) instead of your keyword \n");
		}
	}

	public static byte[] generateKey() {
		byte[] key = null;
		try {
			System.out.println("Enter a password as a seed for the key generation:");

			String pass = reader.readLine();

			key = DynRH.keyGen(128, pass, "salt/salt", 100000);

			System.out.println("Enter the absolute path name of the FOLDER where you want to save the secret key");

			String pathName2 = reader.readLine();
			Utils.saveObject(pathName2 + File.separator + "keyDynRH", key);

		} catch (Exception exp) {
			System.out.println();
			System.out.println("An error occurred while generating the key \n");
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}
		return key;
	}

	public static HashMap<String, byte[]> buildIndex(byte[] key, String pathName) {
		HashMap<String, byte[]> emm = null;
		try {
			ArrayList<File> listOfFile = new ArrayList<File>();
			TextProc.listf(pathName, listOfFile);

			TextProc.TextProc(false, pathName);

			// Construction of the encrypted multi-map
			// The setup will be performed as multiple update operations
			System.out.println("\n Beginning of Encrypted Multi-map creation \n");
			emm = DynRH.setup();

			// start
			long startTime = System.nanoTime();

			// Generate the updates
			// This operation will generate update tokens for the entire data set
			TreeMultimap<String, byte[]> tokenUp = DynRH.tokenUpdate(key, TextExtractPar.lp1);

			// Update the encrypted Multi-map
			DynRH.update(emm, tokenUp);

			// end
			long endTime = System.nanoTime();

			// time elapsed
			long output = endTime - startTime;

			System.out.println("\nElapsed time in seconds: " + output / 1000000000);
			System.out.println("Number of keywords " + TextExtractPar.lp1.keySet().size());
			System.out.println("Number of pairs " + TextExtractPar.lp1.keys().size());

			// Empty the previous multimap
			// to avoid adding the same set of documents for every update

			System.out.println("\n Enter the absolute path name of the FOLDER where you want to save the Index");

			String pathName2 = reader.readLine();
			Utils.saveObject(pathName2 + File.separator + "indexDynRH", emm);

			TextExtractPar.lp1 = ArrayListMultimap.create();

		} catch (Exception e) {
			System.out.println();
			System.out.println("An error occurred while generating the index \n");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return emm;
	}

	public static void updateIndex(byte[] key, HashMap<String, byte[]> emm, String pathName) {
		try {

			ArrayList<File> listOfFile = new ArrayList<File>();
			TextProc.listf(pathName, listOfFile);
			TextProc.TextProc(false, pathName);

			System.out.println("\n Beginning of Encrypted Multi-map update \n");

			long startTime = System.nanoTime();
			// This operation is similar to the one performed in the generateIndex() method
			TreeMultimap<String, byte[]> tokenUp = DynRH.tokenUpdate(key, TextExtractPar.lp1);
			DynRH.update(emm, tokenUp);

			long endTime = System.nanoTime();

			long output = endTime - startTime;

			System.out.println("\nElapsed time in seconds: " + output / 1000000000);
			System.out.println("Number of keywords " + TextExtractPar.lp1.keySet().size());
			System.out.println("Number of pairs " + TextExtractPar.lp1.keys().size());

			System.out.println("Enter the absolute path name of the FOLDER where you want to save the updated Index");

			String pathName2 = reader.readLine();
			Utils.saveObject(pathName2 + File.separator + "indexDynRH", emm);

			// Empty the previous multimap
			// to avoid adding the same set of documents for every update
			TextExtractPar.lp1 = ArrayListMultimap.create();

			System.out.println("Your index has been successfully updated!");
		} catch (Exception e) {
			System.out.println();
			System.out.println("An error occurred while updating the index \n");
			System.out.println(e.getMessage());
		}

	}

	public static List<String> query(byte[] key, HashMap<String, byte[]> emm, String keyword) {
		List<String> ans = new ArrayList<>();
		try {
			byte[][] token = DynRH.genTokenFS(key, keyword);
			// start
			long startTime = System.nanoTime();
			ans = DynRH.resolve(key, DynRH.queryFS(token, emm));
			System.out.println(ans);
			// end
			long endTime = System.nanoTime();

			// time elapsed
			long output = endTime - startTime;

			System.out.println("\nElapsed time in microseconds: " + output / 1000);

		} catch (Exception exp) {
			System.out.println("An error occurred while performing your query \n");
			System.out.println(exp.getMessage());
		}
		return ans;
	}

	public static void deleteElement(String keyword, HashMap<String, byte[]> emm, byte[] key) {
		try {
			System.out.println("\n Enter the index of the element that you want to delete");
			System.out.println(
					"-This index is the position of the file within the array that you just saw (starting at 0)-");
			String index = reader.readLine();
			List<Integer> deletions = new ArrayList<Integer>();
			deletions.add(Integer.parseInt(index));
			byte[][] delToken = DynRH.delTokenFS(key, keyword, deletions);
			DynRH.deleteFS(delToken, emm);

			System.out.println("\n The selected key-value pair has been succesfully deleted from your index!");
		} catch (Exception exp) {
			System.out.println("An error occurred while deleting the selected element.\n");
			System.out.println(exp.getMessage());
		}
	}
}
