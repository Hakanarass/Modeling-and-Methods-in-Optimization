
public class node implements Comparable<node> {

	double profit;
	double weight;
	double ratio; // profit / weight
	int index;
	int contains;
	
	node(int index, double profit, double weight){
		
		this.index = index;
		this.profit = profit; 
		this.weight = weight;
		contains = 0;
		ratio = profit/weight;
			
	}
	public void setContains(int contains) {		
		this.contains = contains;
		 
	}

	public int getContains() {
		return contains;
	}
	
	public int getIndex() {
		return index;
	}
	
	public double getWeight() {
		return weight;
	}
	public double getProfit() {
		return profit;
	}
	public double getRatio() {
		return ratio;
	}

	@Override
	public int compareTo(node o) {
		if(ratio < o.getRatio()) {
			return 1;
		}else if (ratio > o.getRatio()) {
			return -1;
		}
		
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	
}
