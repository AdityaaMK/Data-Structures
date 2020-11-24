import java.util.*;

public class SuperListRunner {

	public SuperListRunner() {
		SuperList<Integer> arrayList = new SuperList<>();
		for (int i = 0; i < 30; i++) {
			int rand = (int) (Math.random() * 1000) + 1;
			arrayList.add(rand);
		}
		System.out.println("List: " + arrayList);
		System.out.println("Size: " + arrayList.size());

		SuperList<Integer> stackList = new SuperList<>();
		while (!arrayList.isEmpty()) {
			int temp = arrayList.remove(arrayList.size() - 1);
			stackList.push(temp);
		}
		SuperList<Integer> queueList = new SuperList<>();
		System.out.print("Stack List: ");
		while (!stackList.isEmpty()) {
			int temp = stackList.pop();
			System.out.print(temp + " ");
			queueList.add(temp);
		}
		System.out.print("\nQueue List: ");
		while (!queueList.isEmpty()) {
			int temp = queueList.poll();
			System.out.print(temp + " ");
			arrayList.add((int) (Math.random() * arrayList.size()), temp);
		}
		int sum = 0;
		int evenSum = 0;
		int oddSum = 0;

		SuperList<Integer> evensList = new SuperList<>();
		for (int i = 0; i < arrayList.size(); i++) {
			sum += arrayList.get(i);
			if (i % 2 == 0) {
				evenSum += arrayList.get(i);
			} else
				oddSum += arrayList.get(i);
			if (arrayList.get(i) % 2 == 0) {
				evensList.add(arrayList.get(i));
			}
		}
		for (int i = 0; i < evensList.size(); i++) {
			arrayList.add(evensList.get(i));
		}
		System.out.println("\nSum: " + sum);
		System.out.println("Even Sum: " + evenSum);
		System.out.println("Odd Sum: " + oddSum);
		System.out.println("Duplicated Evens: " + arrayList);
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) % 3 == 0) {
				arrayList.remove(i);
			}
		}
		System.out.println("List without mod 3 values: " + arrayList);
		arrayList.add(3, 55555);
		System.out.println("List with 55555: " + arrayList);
		for (int i = 0; i < arrayList.size() - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < arrayList.size(); j++) {
				if (arrayList.get(j) < arrayList.get(minIndex))
					minIndex = j;
			}
			int temp = arrayList.remove(minIndex);
			arrayList.add(minIndex, arrayList.get(i));
			arrayList.remove(i);
			arrayList.add(i, temp);
		}
		System.out.println("Sorted List in Ascending Order: " + arrayList);

		double median;
		if (arrayList.size() % 2 == 1) {
			median = arrayList.get(arrayList.size() / 2);
		} else {
			median = ((double) arrayList.get(arrayList.size() / 2) + (double) arrayList.get(arrayList.size() / 2 - 1))
					/ 2;
		}

		System.out.println("Median: " + median);
		System.out.print("Before Median: ");
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) < median)
				System.out.print(arrayList.get(i) + " ");
		}
		System.out.print("\nAfter Median: ");
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) > median)
				System.out.print(arrayList.get(i) + " ");
		}

		SuperList<String> wordList = new SuperList<>();
		String sentence = "Hi, my name is Adityaa. How are you doing today? Pleasure to meet you.";
		String[] arr = sentence.split("\\W+");
		for (String s : arr)
			wordList.add(s);
		System.out.println("\nEntire List: " + wordList);
		for (int i = 0; i < wordList.size(); i++)
			if (wordList.get(i).length() <= 3) {
				wordList.remove(i);
				i--;
			}
		System.out.println("Words longer than 3 chars: " + wordList);

		for (int i = 1; i < wordList.size(); i++) {
			int key = wordList.get(i).length();
			int j = i - 1;
			while (j >= 0 && wordList.get(j).length() > key) {
				j--;
			}
			wordList.add(j + 1, wordList.get(i));
			wordList.remove(i + 1);
		}
		System.out.println("Sorted Word List in Ascending Order: " + wordList);

		double avgLength = 0;
		for (int i = 0; i < wordList.size(); i++)
			avgLength += wordList.get(i).length();
		System.out.println("Average Word Length: " + avgLength / wordList.size());
	}

	public static void main(String[] args) {
		new SuperListRunner();
	}

}