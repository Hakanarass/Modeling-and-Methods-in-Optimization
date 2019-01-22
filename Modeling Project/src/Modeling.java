import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Modeling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Double> profitList = new ArrayList<Double>();
		ArrayList<Double> weightList = new ArrayList<Double>();
		double knapsack_capacity = 0;
		int numberOfItem = 0;

		Scanner inputDocumentName = new Scanner(System.in);
		String userInputDocumentName = "";
		System.out.println("============================");
		System.out.println("Please enter document name with .xlsx For example : Instance_24_6404180.xlsx");
		System.out.print("ENTER YOUR CHOICE > ");
		userInputDocumentName = inputDocumentName.nextLine();
		System.out.println("Please enter pK 0 between 1: ");
		Scanner inputProbability = new Scanner(System.in);
		double userInputProbability = 0;
		userInputProbability = inputProbability.nextDouble();

		excelList(userInputDocumentName, profitList, weightList);
		numberOfItem = excelNumberOfItem(userInputDocumentName, numberOfItem);
		knapsack_capacity = excelKnapsack_capacity(userInputDocumentName, knapsack_capacity);

		System.out.println("Document Name: " + userInputDocumentName + " Number of Item: " + numberOfItem
				+ " Knapsack Capacity: " + knapsack_capacity);

		ArrayList<node> tempList = new ArrayList<node>();
		ArrayList<node> initialSolution = new ArrayList<node>();

		node[] nodeList = new node[numberOfItem];
		node[] initialList = new node[numberOfItem];

		for (int i = 0; i < profitList.size(); i++) {
			double k = profitList.get(i);
			double l = weightList.get(i);
			nodeList[i] = new node(i, k, l);
			initialList[i] = new node(i, k, l);
		}

		for (int i = 0; i < nodeList.length; i++) {
			tempList.add(nodeList[i]);
			initialSolution.add(initialList[i]);
		}

		double weightTempList = 0;
		double weightInitialSolution = 0;
		double profitTempList = 0;
		double profitInitialSolution = 0;

		Scanner input = new Scanner(System.in);
		String userInput = "";

		System.out.println("============================");
		System.out.println("[1] List-Processing");
		System.out.println("[2] Random");
		System.out.println("[q] Quit");
		System.out.println("============================");
		System.out.print("ENTER YOUR CHOICE > ");
		userInput = input.nextLine();

		switch (userInput.charAt(0)) {
		case '1':

			Collections.sort(tempList);
			Collections.sort(initialSolution);

			for (node p : tempList) {

				if (p.weight <= knapsack_capacity - weightTempList) {
					weightTempList = weightTempList + p.weight;
					profitTempList = profitTempList + p.profit;
					p.setContains(1);
				}
			}

			for (node p : initialSolution) { 
				if (p.weight <= knapsack_capacity - weightInitialSolution) {
					weightInitialSolution = weightInitialSolution + p.weight;
					profitInitialSolution = profitInitialSolution + p.profit;
					p.setContains(1);
				}
			}
			break;
		case '2':
			int counterTemp = 0;
			while (weightTempList < knapsack_capacity * 0.5) {
				Random random90 = new Random(); 
				int tempRandomNumber90 = random90.nextInt(tempList.size());

				if (tempList.get(tempRandomNumber90).contains == 0) {
					tempList.get(tempRandomNumber90).setContains(1);
					weightTempList = weightTempList + tempList.get(tempRandomNumber90).weight;

					if (weightTempList > knapsack_capacity * 0.5) {
						tempList.get(tempRandomNumber90).setContains(0);
						weightTempList = weightTempList - tempList.get(tempRandomNumber90).weight;
						counterTemp = counterTemp + 1;
					}
					if (counterTemp > 3 * tempList.size()) {
						changeList(initialSolution, tempList);
						break;
					}
				}
				changeList(initialSolution, tempList);
			}
			break;
		case 'q':
			System.out.println("Quitting...");
			System.exit(0);
		}

		weightInitialSolution = calculateCapacity(initialSolution);
		profitInitialSolution = calculateProfit(initialSolution);

		System.out.println("Capacity: " + weightInitialSolution + " Profit: " + (int) profitInitialSolution);

		double calculateTemperature = 0;
		calculateTemperature = Math.log(userInputProbability);
		double maxProfit = Collections.max(profitList);
		double minProfit = Collections.min(profitList);
		double temperature = -(maxProfit - minProfit) / calculateTemperature;
		double tempTemperature = 0;
		double alpha = 0.85;
		double pk = 0.0;
		double difference = 0.0;
		int counter = 0;
		double plato = tempList.size() + 1;
		int limit = initialSolution.size() / 10;

		System.out.println("Program runs with Temperature: " + temperature);

		while (temperature > tempTemperature) {

			Random random = new Random();
			int tempRandomNumber = random.nextInt(tempList.size());
			while (tempList.get(tempRandomNumber).contains == 1) {
				Random randomNumber2 = new Random();
				int tempRandomNumber2 = randomNumber2.nextInt(tempList.size());
				tempRandomNumber = tempRandomNumber2;
			}

			tempList.get(tempRandomNumber).contains = 1;
			weightTempList = calculateCapacity(tempList);
			weightInitialSolution = calculateCapacity(initialSolution);
			profitTempList = calculateProfit(tempList);
			profitInitialSolution = calculateProfit(initialSolution);

			if (weightTempList < knapsack_capacity) {
				if (profitTempList < profitInitialSolution) {
					difference = profitInitialSolution - profitTempList;
					double tempDifference = (difference / temperature);
					pk = Math.exp(-tempDifference);
					double tempPK = random.nextDouble();
					if (tempPK <= pk) {
						changeList(initialSolution, tempList);
						System.out.println(+(int) calculateProfit(tempList));
					} else {
						changeList(tempList, initialSolution);
						System.out.println((int) calculateProfit(tempList));
						break;
					}
				} else {
					changeList(initialSolution, tempList);
					System.out.println((int) calculateProfit(tempList));
				}
			} else {
				Random random5 = new Random();
				int tempRandomNumber5 = random5.nextInt(tempList.size());

				while (tempList.get(tempRandomNumber5).contains == 0) {
					Random randomNumber6 = new Random();
					int tempRandomNumber6 = randomNumber6.nextInt(tempList.size());
					tempRandomNumber5 = tempRandomNumber6;
				}

				tempList.get(tempRandomNumber5).contains = 0;

				weightTempList = calculateCapacity(tempList);
				weightInitialSolution = calculateCapacity(initialSolution);
				profitTempList = calculateProfit(tempList);
				profitInitialSolution = calculateProfit(initialSolution);

				if (weightTempList < knapsack_capacity) {
					if (profitTempList < profitInitialSolution) {
						difference = profitInitialSolution - profitTempList; 
						double tempDifference = (difference / temperature);
						pk = Math.exp(-tempDifference);
						double tempPK = random.nextDouble();
						if (tempPK <= pk) {
							changeList(initialSolution, tempList);
							System.out.println((int) calculateProfit(tempList));
						} else {
							changeList(tempList, initialSolution);
							System.out.println((int) calculateProfit(tempList));
							break;
						}
					}
				} else {
					for (int k = 0; k < limit; k++) {

						Random random15 = new Random(); // random generate number
						int tempRandomNumber15 = random15.nextInt(tempList.size());

						while (tempList.get(tempRandomNumber15).contains == 0) {
							Random randomNumber16 = new Random(); // random generate number
							int tempRandomNumber16 = randomNumber16.nextInt(tempList.size());
							tempRandomNumber15 = tempRandomNumber16;
						}

						tempList.get(tempRandomNumber15).contains = 0;

						if (calculateCapacity(tempList) < knapsack_capacity) {
							k = limit;
						} else {
							tempList.get(tempRandomNumber15).contains = 1;
						}
					}

					weightTempList = calculateCapacity(tempList);
					weightInitialSolution = calculateCapacity(initialSolution);
					profitTempList = calculateProfit(tempList);
					profitInitialSolution = calculateProfit(initialSolution);

					if (calculateCapacity(tempList) < knapsack_capacity) {
						if (profitTempList < profitInitialSolution) {
							difference = profitInitialSolution - profitTempList;
							double tempDifference = (difference / temperature);
							pk = Math.exp(-tempDifference);
							double tempPK = random.nextDouble();
							if (tempPK <= pk) {
								changeList(initialSolution, tempList);
								System.out.println((int) calculateProfit(tempList));
							} else {
								changeList(tempList, initialSolution);
								System.out.println((int) calculateProfit(tempList));
								break;
							}
						} else {
							changeList(initialSolution, tempList);
							System.out.println((int) calculateProfit(tempList));
						}
					} else {
						changeList(tempList, initialSolution);
						System.out.println((int) calculateProfit(tempList));
					}
				}
			}
			if (counter % plato == 0) {
				temperature = temperature * alpha;
			}
			counter = counter + 1;
		}

		weightInitialSolution = calculateCapacity(tempList);
		profitInitialSolution = calculateProfit(tempList);

		System.out.println("Capacity: " + weightInitialSolution + " Profit: " + (int) profitInitialSolution);
		System.out.println(counter + " " + temperature);

	}

	public static int excelNumberOfItem(String name, int numberOfItem) {
		try {
			FileInputStream file = new FileInputStream(new File(name));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getColumnIndex() == 1 && cell.getRowIndex() == 0) {
							numberOfItem = (int) cell.getNumericCellValue();
						}
						break;
					case Cell.CELL_TYPE_STRING:
						break;
					}
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numberOfItem;
	}

	public static double excelKnapsack_capacity(String name, double knapsack_capacity) {
		try {
			FileInputStream file = new FileInputStream(new File(name));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getColumnIndex() == 1 && cell.getRowIndex() == 1) {
							knapsack_capacity = cell.getNumericCellValue();
						}
						break;
					case Cell.CELL_TYPE_STRING:
						break;
					}
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return knapsack_capacity;
	}

	public static void excelList(String name, ArrayList<Double> profitList, ArrayList<Double> weightList) {
		try {
			FileInputStream file = new FileInputStream(new File(name));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getColumnIndex() == 0 && cell.getRowIndex() > 2) {
							profitList.add(cell.getNumericCellValue());
						}
						if (cell.getColumnIndex() == 1 && cell.getRowIndex() > 3) {
							weightList.add(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_STRING:
						break;
					}
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static double calculateProfit(ArrayList<node> p) {
		double totalProfit = 0;

		for (int i = 0; i < p.size(); i++) {

			if (p.get(i).contains == 1) {
				totalProfit = p.get(i).profit + totalProfit;
			}
		}

		return totalProfit;
	}

	public static double calculateCapacity(ArrayList<node> p) {
		double totalWeight = 0;

		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).contains == 1) {
				totalWeight = p.get(i).weight + totalWeight;
			}
		}

		return totalWeight;
	}

	public static void changeList(ArrayList<node> p, ArrayList<node> p1) {

		for (int i = 0; i < p.size(); i++) {

			p.get(i).contains = p1.get(i).contains;
			p.get(i).index = p1.get(i).index;
			p.get(i).weight = p1.get(i).weight;
			p.get(i).profit = p1.get(i).profit;
			p.get(i).ratio = p1.get(i).ratio;

		}

	}

}
