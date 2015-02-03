package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//import modelmapping.lookupTable;

/*************************************************************************
 *  Compilation:  javac BTree.java
 *  Execution:    java BTree
 *
 *  B-tree.
 *
 *  Limitations
 *  -----------
 *   -  Assumes M is even and M >= 4
 *   -  should b be an array of children or list (it would help with
 *      casting to make it a list)
 *
 *************************************************************************/


public class BTree<Key extends Comparable<Key>, Value>  {
    private static final int M = 40;    // max children per B-tree node = M-1

    private Node root;             // root of the B-tree
    private int HT;                // height of the B-tree
    private int N;                 // number of key-value pairs in the B-tree

    // helper B-tree node data type
    private static final class Node {
        private int m;                             // number of children
        private Entry[] children = new Entry[M];   // the array of children
        private Node(int k) { m = k; }             // create a node with k children
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Entry {
        private Comparable key;
        private Object value;
        private Node next;     // helper field to iterate over array entries
        public Entry(Comparable key, Object value, Node next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    // constructor
    public BTree() { root = new Node(0); }
 
    // return number of key-value pairs in the B-tree
    public int size() { return N; }

    // return height of B-tree
    public int height() { return HT; }


    // search for given key, return associated value; return null if no such key
    public Value get(Key key) { return search(root, key, HT); }
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].value;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    // insert key-value pair
    // add code to check for duplicate keys
    public void put(Key key, Value value) {
        Node u = insert(root, key, value, HT); 
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        HT++;
    }


    private Node insert(Node h, Key key, Value value, int ht) {
        int j;
        Entry t = new Entry(key, value, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, value, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // for debugging
    public String toString() {
        return toString(root, HT, "") + "\n";
    }
    private String toString(Node h, int ht, String indent) {
        String s = "";
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s += indent + children[j].key + " " + children[j].value + "\n";
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s += indent + "(" + children[j].key + ")\n";
                s += toString(children[j].next, ht-1, indent + "     ");
            }
        }
        return s;
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }


   /*************************************************************************
    *  test client
 * @throws IOException 
    *************************************************************************/
    /*public static void main(String[] args) throws IOException {
    	/*
    	BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>>>> KIND
    	= new BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>>>>();
    	
    	BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>>> DOMAIN
    	= new BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>>>();
    	
    	BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>> CATEGORY
    	= new BTree<Integer,BTree<Integer,BTree<Integer,BTree<Integer,String>>>>();
    	
    	BTree<Integer,BTree<Integer,BTree<Integer,String>>> SUBCATEGORY
    	= new BTree<Integer,BTree<Integer,BTree<Integer,String>>>();
    	
    	BTree<Integer,BTree<Integer,String>> SPECIFIC
    	= new BTree<Integer, BTree<Integer,String>>();
    	
    	BTree<Integer,String> EXTRA
    	= new BTree<Integer,String>();
    	
        BTree<Integer,String> country
        = new BTree<Integer,String>();           
        */

  //      lookupTable lat = new lookupTable();
        
        
        
        //country = readCountries(country,countryMapFile);  
        //System.out.println("242:   	"+country.get(242));
        //System.out.println("225:   	"+country.get(225));
        //System.out.println("161:   	"+country.get(161));
        //kind = readKinds(kind,kindMapFile);  
        //System.out.println("0:   	"+KIND.get(0));
        //System.out.println("1:   	"+KIND.get(1));
        //System.out.println("5:   	"+KIND.get(5));
        //platformDomain = readDomain(platformDomain,platformDomainMapFile);
        //System.out.println("0:   	"+platformDomain.get(0));
        //System.out.println("1:   	"+platformDomain.get(1));
        //System.out.println("5:   	"+platformDomain.get(5));
        //munitionDomain = readDomain(munitionDomain,munitionDomainMapFile);  
        //radioDomain = readDomain(radioDomain,radioDomainMapFile);
        //sensorDomain = readDomain(sensorDomain,sensorDomainMapFile);        
        
        //domainList.add(kind);
        //domainList.add(country);
        //domainList.add(platformDomain);
        //domainList.add(munitionDomain);
        //domainList.add(radioDomain);
        //domainList.add(sensorDomain);
        //System.out.println(domainList.get(2));
        //System.out.println(domainList.get(1));
        //System.out.println(domainList.get(0));
    //}
    
    
    
	private static BTree<Integer, BTree<Integer,String>> readDomain(BTree<Integer, BTree<Integer,String>> domain, String filename) throws IOException 
	{
		BTree<Integer, BTree<Integer,String>> ss = domain;
		BTree<Integer, String> newTree;
		String[] info;		
		String s;
		int idx;
      FileReader fr = new FileReader(new File(filename));
      BufferedReader br = new BufferedReader(fr);
      while((s = br.readLine()) != null)
      {
    	  newTree = new BTree<Integer,String>();
    	  info = s.split("\t");
    	  idx = Integer.parseInt(info[0]);
    	  newTree.put(idx,info[1]);   	
    	  //ss.put(idx, newTree);
      }
      try {
                      br.close();
              } catch (IOException e) {
                      e.printStackTrace();
              }
      return ss;	
	}

	private static BTree<Integer, String> readKinds(BTree<Integer, String> bt, String filename) throws IOException {
		BTree<Integer, String> ss = bt;
		String[] info;		
		String s;
		int idx;
      FileReader fr = new FileReader(new File(filename));
      BufferedReader br = new BufferedReader(fr);
      while((s = br.readLine()) != null)
      {
    	  info = s.split("\t");
    	  idx = Integer.parseInt(info[0]);
    	  ss.put(idx,info[1]);   	  
      }
      try {
                      br.close();
              } catch (IOException e) {
                      e.printStackTrace();
              }
      return ss;
		
		
}

	public static BTree<Integer, String> readCountries(BTree<Integer, String> bt, String filename) throws IOException
	{
		BTree<Integer, String> ss = bt;
		String[] info;		
		String s;
		int idx;
      FileReader fr = new FileReader(new File(filename));
      BufferedReader br = new BufferedReader(fr);
      while((s = br.readLine()) != null)
      {
    	  info = s.split("\t");
    	  idx = Integer.parseInt(info[0]);
    	  ss.put(idx,info[1]);    	  
      }
      try {
                      br.close();
              } catch (IOException e) {
                      e.printStackTrace();
              }
      return ss;
	}
}
