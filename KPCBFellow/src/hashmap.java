/*  !----!  ! -     - !    !-------    !-----
 *  !    !  !  -   -  !    !           !    !
 *  !    !  !   - -   !    !-----      !----- 
 *  !    !  !         !    !           !    --
 *  !----!  !		  !    !-------    !    ----
 */

//Notes On Hash Map
//Hash Map Assumes Node is a Pair of String key and random object type called value
//Hash Map resolves collisions through chaining
//Hash Map mapping function uses mod function


import java.io.*;
import java.util.Scanner;

public class hashmap {
	
	private static Scanner f2;
	private static Scanner f1;
	private static Scanner sc;

	static class Node <T>{
		String key; //key value
		Node next; //
		T value; //value of data 
		
			public Node(String key, Node next, T value){ 
				this.key = key;	
				this.value = value;
				this.next = next;	
			}
	}
	
	static class hash{
		
		int buckets; //amount of buckets
		int items; //amount of items in hash map
		Node [] hashmap; //hash map
		
		public hash(int buckets){ //constructor class. allocates space and sets the array of nodes
			this.hashmap = new Node[buckets];
			this.buckets = buckets;
			this.items = 0;
		}
		
		int mappingFunction (String word){
			
			if(word.hashCode() % buckets <0){ //if it is negative 
				return -1*(word.hashCode() % buckets);
			}
			
			return  word.hashCode() % buckets; //goes to the remainder 
		}
			
		float load(){ //calculates the load factor
			return (float) items/buckets;
		}
		
		Object get(String key){
			int arrayPos = mappingFunction(key);
			
			//Case 1, Null . No Element Can Be In The Linked List If There Is No Linked List 
			if(this.hashmap[arrayPos] == null){ return null; }
			
			//Case 2, Search
			for(Node ptr = hashmap[arrayPos]; ptr != null; ptr = ptr.next){ //goes to bucket and its corresponding linked list
				if(ptr.key.equals(key)){ //found
					return ptr.value;
				}				
			}

			return null; //Was Not Found
		}
		
		Object delete (String key){
		
			int arrayPos = mappingFunction(key);
			
			//Case 1, Null
			if(this.hashmap[arrayPos] == null){return null;}
			
			//Case 2 Delete First Item in array
			if(this.hashmap[arrayPos].equals(key)){
				Node ptr = this.hashmap[arrayPos];
				this.hashmap[arrayPos] = ptr.next;
				return ptr.value;
			}
			
			//delete middle/ end
			Node prev = null;
			for(Node ptr = hashmap[arrayPos]; ptr != null; ptr = ptr.next){ //goes to bucket and its corresponding linked list
				if(ptr.key.equals(key)){ //found
					prev.next = ptr.next; //skip ptr
					return ptr.value;
				}		
				prev = ptr;
			}
		
		//not found it gets here
		return null;
		}
		
		boolean set(String key, Object value){

			if(get(key) != null){ //checks if key is already in hashmap
				return false;
			}
		
			int arrayPos = mappingFunction(key); //calls the mapping function for array index

			if (hashmap[arrayPos] == null){ //no linked list is in the bucket
				Node newNode = new Node(key,null, value);
				hashmap[arrayPos] = newNode;
			}else{
				Node newNode = new Node(key,hashmap[arrayPos].next, value); //creates new node with next the linked list
				hashmap[arrayPos] = newNode;
			}
			
			
			return true;
		}
		
		
	}
	
	

	public static void main(String[] args) throws FileNotFoundException {
		
		
		//This project assumes that the text file will have pairs of words (one for key and one for word)
		//Thus Each Line Of The Test Input Must Be Even
		
		String fileName = "test2.txt";
		
		Scanner f1 = new Scanner(new File(fileName));
			
		//First Counts Size Of All Elements So We Know How Big We Should Make The Hashmap
		int size = 0;
		while(f1.hasNext()){
			
			String line = f1.nextLine();
			
			if(line.isEmpty()){
				continue;
			}
				
			String[] words = line.split("\\s+");
			size = size + words.length;			
		}
		
		hash hashmapz = new hash(size/2); //size is divided by two becuase we are dealing with pairs 
		
		f2 = new Scanner(new File(fileName));
		while(f2.hasNext()){ //This Enters Pair of Data in a text file. Examples include Name PhoneNumber, Name ID, Name Age, School Enrollment, Ect.
					
			String line = f2.nextLine();
		
			if(line.isEmpty()){
				continue;
			}
		
			String[] words = line.split("\\s+");
			
			for (int i = 0; i <words.length/2; i++){
				if(i%2==0){ //0,2,4,6					
					hashmapz.set(words[i], words[i+1]);
				}
			}	
			
		}	
	}
}
