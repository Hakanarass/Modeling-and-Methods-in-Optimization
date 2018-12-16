import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Modeling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] weighted = { 382745, 799601, 909247, 729069, 467902, 44328, 34610, 698150, 823460, 903959, 853665, 551830,
				610856, 670702, 488960, 951111, 323046, 446298, 931161, 31385, 496951, 264724, 224916, 169684 };

		int[] profit = { 825594, 1677009, 1676628, 1523970, 943972, 97426, 69666, 1296457, 1679693, 1902996, 1844992,
				1049289, 1252836, 1319836, 953277, 2067538, 675367, 853655, 1826027, 65731, 901489, 577243, 466257,
				369261 };
		double knapsack_capacity = 6404180;

		ArrayList<node> pList = new ArrayList<node>();

		node[] nodeList = new node[24];

		for (int i = 0; i < profit.length; i++) { // Her node teker teker array elemanlarını ekliyor

			double k = profit[i];
			double l = weighted[i];
			nodeList[i] = new node(i, k, l);

		}

		for (int i = 0; i < nodeList.length; i++) { // Arrayliste nodeları atıyor
			pList.add(nodeList[i]);
		}

		Collections.sort(pList); // Arraylist içindeki nodeları ratio göre sort ediyor

		double tempWeight = 0;

		for (node p : pList) {

			if (p.weight <= knapsack_capacity - tempWeight) {
				tempWeight = tempWeight + p.weight;
				p.setContains(1);
			}
		}
		double temp = 0;
		double tempProfit = 0;

		//		for (node p : pList) {
		//			System.out.println(p.index +" s" +p.contains);
		//		}



	
	//		for (node p : pList) {
	//			System.out.println(p.index +" k" +p.contains);
	//
	//		}

	for (node p : pList) { // sort edilmiş arrayi bize basıyor
		if (p.getContains() == 1) {
			temp = temp + p.weight;
			tempProfit = tempProfit + p.profit;
			//		System.out.println(p.profit);
		}

	}
	System.out.println("Kapasite: " + knapsack_capacity + " Total:" + temp + " Profit " + (int) tempProfit);

	pList.get(5).setContains(0);
	tempWeight = tempWeight - pList.get(5).weight;
	
	for(int i = 6; i< pList.size();i++) {

		if(pList.get(i).getContains()==0 && pList.get(i).weight <= knapsack_capacity - tempWeight) {
			tempWeight = tempWeight + pList.get(i).weight;
			pList.get(i).setContains(1);

		}
	}
	
	for (node p : pList) { // sort edilmiş arrayi bize basıyor
		if (p.getContains() == 1) {
			temp = temp + p.weight;
			tempProfit = tempProfit + p.profit;
			//		System.out.println(p.profit);
		}

	}
	System.out.println("Kapasite: " + knapsack_capacity + " Total:" + temp + " Profit " + (int) tempProfit);

	}
}
