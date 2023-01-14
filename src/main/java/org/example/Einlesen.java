package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Einlesen {
		static double[][] inputValues; //Muster
		public static double[]      expectedValues; //Klasse
		static int n;        //Dimension, d.h. Anzahl der Merkmale bzw. Eingaenge in das Perzeptron incl. BIAS
		static int m;        //Anzahl der Muster bzw. Trainingsbeispiele bzw. Datens�tze
	
		public static void ausgeben() {
			for(int i=0;i<inputValues.length;i++){
	        	System.out.print(i + " ");
	        	for(int j=0;j<inputValues[i].length;j++){
	        		System.out.print(inputValues[i][j] + " ");
	        	}
	        	System.out.print(expectedValues[i]);
	        	System.out.println();
	        }
		}
		

		
		public static void einlesenVorlesungsbeispiele(File file) {
			//Es wird angenommen, dass alle Eingabedaten im Intervall [0, 100] liegen
			m = 0;//Anzahl Muster	        
			n = 2;
			try{
				//1. Anzahl m der Muster bestimmen 
	    		Scanner scanner      = new Scanner(file);            
	            while(scanner.hasNext()) {
	            	double x1 = Double.valueOf (scanner.next());
	                double x2 = Double.valueOf (scanner.next());
	            	int     expectedValues = Integer.valueOf(scanner.next());
	            	//hier koennte man die minimalen und maximalen Eingabewerte ermitteln
	            	//um sie beim Einlesen auf den Bereich [0, 1] zu skalieren
					m++;
	            }

	            inputValues = new double[m][n];//2 Merkmale
	            expectedValues = new double[m];
	            scanner.close();
	            
	            //2. Muster einlesen  
	            scanner = new Scanner(file);
	            int nr  = 0;
	            while(scanner.hasNext()) {
	            	double x1 = Double.valueOf (scanner.next());
	                double x2 = Double.valueOf (scanner.next());
					int    y0 = Integer.valueOf(scanner.next());
	            	inputValues[nr][0] = x1/100.;
	            	inputValues[nr][1] = x2/100.;
	            	expectedValues[nr]    = y0;
					nr++;
	            } 
	            scanner.close();	            
	        }
	        catch(FileNotFoundException e){
				System.out.println(e.getMessage());
	        }
 
		}
	}
