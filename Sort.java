class Sort{
	static public void quickSort(double dist[], int ind[]){
		Sort.quickSort(dist,ind,0,dist.length-1);
	}
	static private void quickSort(double dist[],int ind[], int begin, int end) {
	    if (begin < end) {
	        int partitionIndex = partition(dist, ind, begin, end);
	        quickSort(dist, ind, begin, partitionIndex-1);
	        quickSort(dist, ind, partitionIndex+1, end);
	    }
	}
	static private int partition(double dist[], int ind[], int begin, int end) {
	    double pivot = dist[end];
	    int i = (begin-1);
	 
	    for (int j = begin; j < end; j++) {
	        if (dist[j] <= pivot) {
	            i++;
	 
	            int swapTemp = ind[i];
	            ind[i] = ind[j];
	            ind[j] = swapTemp;

	            double swap = dist[i];
	            dist[i] = dist[j];
	            dist[j] = swap;
	        }
	    }
	 
	    int swapTemp = ind[i+1];
	    ind[i+1] = ind[end];
	    ind[end] = swapTemp;

	 	double swap = dist[i+1];
	 	dist[i+1] = dist[end];
	 	dist[end] = swap;

	    return i+1;
	}
}