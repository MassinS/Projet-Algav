#!/bin/bash
# verifier si les deux arguments sont present
if [ " $#" -ne 2 ]; then
echo " Usage : $0 <x > <y >"
exit 1
fi
x=$1
y=$2
# verifier si x est 1 ou 0
if [ "$x" -eq 0 ]; then
javac -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/particia_trie/PatriciaTrieNode.java
javac -cp src  src/particia_trie/PatriciaTrie.java
javac -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/particia_trie/profondeurMoyenne_pat.java
java -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/particia_trie/profondeurMoyenne_pat.java "$y"
elif [ "$x" -eq 1 ]; then
javac -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/com/Hybrides/Noeud.java
javac -cp src  src/com/Hybrides/Tries_Hybrides.java
javac -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/com/Hybrides/profondeurMoyenne_hyb.java
java -cp src:jackson-annotations-2.18.1.jar:jackson-core-2.18.1.jar:jackson-databind-2.18.1.jar  src/com/Hybrides/profondeurMoyenne_hyb.java "$y"
else
echo " Error : x must be 0 or 1"
exit 1
fi



