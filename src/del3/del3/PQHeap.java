package del3;

import java.util.*;
/*
Kristoffer Ganer      - krgan09@student.sdu.dk
Rasmus Aasø Henriksen - rhenr17@student.sdu.dk
Henrik Flindt         - hefli17@student.sdu.dk
*/

public class PQHeap implements PQ {

	private ArrayList<Element> heap;

	/**
	 * Constructor, takes an argument "maxElms" and creates an arraylist with that
	 * size
	 */
	public PQHeap(int maxElms) {
		heap = new ArrayList<Element>(maxElms);
	}

	/** return size of the PQHeap list */
	public int PQHeapSize() {
		return heap.size();
	}

	/**
	 * creates a temporary pointer to the first element in the array, then swaps the
	 * first and last element, once this has been done, the last element of the
	 * array is removed, then heapify is called on the first element, at the end the
	 * temporary pointer is returned
	 * 
	 * if called on an empty array, an exception is thrown.
	 */
	public Element extractMin() {
		/*if (heap.size() < 0) { // throwing an exception if
			throw new Exception("Can't extract from an empty heap");
		}*/
		Element min_index = heap.get(0);
		// Creating a temporary pointer to the smallest key
		heap.set(0, heap.get(heap.size() - 1));
		// making the first pointer of the array point to the
		// element in the last spot
		heap.remove(heap.size() - 1);
		// removing the duplicate pointer to the last element
		heapify(0);
		// calling heapify on the first element of the array,
		// to ensure that the heap is still a minheap
		return min_index;
		// returning the pointer to the original smallest key
	}

	/**
	 * method for inserting an element into the heap
	 * 
	 * this is done by adding it to the end of the array, and then swapping it with
	 * it's parent, if the parent is larger
	 */
	public void insert(Element e) {
		heap.add(e);
		int index = (heap.size() - 1);
		// the index of the element is gotten by subtracting one from the size
		// of the array, the reason for subtracting one is that the array is
		// 0-indexed, while the size() method returns the number of elements
		// in the array
		int parentIndex = ((index - 1) / 2);
		// the index of the parent is gotten by subtracting 1 from the index,
		// then doing integer division by 2
		// the reason for subtracting one more is for rounding purposes
		while ((heap.get(parentIndex).getKey() > e.getKey()) && index > 0) {
			// runs if the key for parent is larger than the key of the element
			// & the index of the element is geater than 0
			heap.set(index, heap.get(parentIndex));
			// sets the position of the parent to the one of the element inserted
			heap.set(parentIndex, e);
			// inserts the element at the position of the parent
			index = parentIndex;
			// updates the index of the child
			parentIndex = ((index - 1) / 2);
			// updates parentIndex to reflect the index of the new parent
		}
		// printHeap(); /for debug purposes
	}

	/**
	 * Supporting method for ensuring that a given parent is smaller than any of its
	 * children
	 * 
	 * Calls itself recursively to ensure that if a parent and child is swapped, the
	 * rest of that branch is still a minheap
	 */
	private void heapify(int index) {
		int left = (index * 2 + 1);
		int right = (index * 2 + 2);
		// gets the index of the left and right child of the parent
		int min_index = index;
		// variable to keep track of which key is smallest
		if (left < heap.size()) {
			// if statement prevents an ArrayIndexOutOfBoundsException if the
			// parent has no children
			if (heap.get(left).getKey() < heap.get(min_index).getKey()) {
				min_index = left;
				// then if the key of the left child is less than that of the
				// parent the min_index variable is reassigned to point at that
				// child
			}
		}
		if (right < heap.size()) { // the same is then done for the right child
			if (heap.get(right).getKey() < heap.get(min_index).getKey()) {
				min_index = right;
			}
		}
		// at this point min_index will be the index of the smallest key
		// between the parent, and the two children
		if (min_index != index) {
			// thus if min_index and index aren't the same, it means that the
			// parent should be swapped with one of the children
			Element temp = heap.get(index);
			// This is done in the same way as in the insert method, except
			// here a temporary pointer is created to point to the parent
			// Element
			heap.set(index, heap.get(min_index));
			heap.set(min_index, temp);
			heapify(min_index);
		}
	}

	/**
	 * A supporting method that was used to print the heap to the terminal for
	 * debugging purposes
	 */
	private void printHeap() {
		System.out.println("The current heap: ");
		for (int i = 0; i < heap.size(); i++) {
			System.out.print(heap.get(i).getKey() + " , ");
		}
		System.out.println("");
	}
}
