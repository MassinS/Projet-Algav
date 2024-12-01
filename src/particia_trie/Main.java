package particia_trie;

public class Main {
	
	public static void main (String [] args) {
		
		
		PatriciaTrieNode root = new PatriciaTrieNode("");
		PatriciaTrie trie = new PatriciaTrie();
		
		
		trie.insert(root, "zz");
		trie.insert(root, "zb");
		trie.insert(root, "zba");
		trie.insert(root, "zzba");
		trie.insert(root, "zze");
		
		trie.ListeMots(root).forEach(System.out::println);
		 
	    System.out.println(trie.Suppression(root, "zz", 0) );
	    
	    trie.ListeMots(root).forEach(System.out::println);
		
	    trie.AfficherLesMots(root, "");
	    
	    PatriciaTrieNode root1 = new PatriciaTrieNode("");
	    
	    trie.insert(root, "zz");
	    
	    PatriciaTrieNode root2=trie.FusionArbre(root, root1);
	    trie.AfficherLesMots(root2, "");
	    
	}
	
	
}
