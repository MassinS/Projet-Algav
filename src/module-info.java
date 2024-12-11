/**
 * 
 */
/**
 * 
 */
module Projet_Algav {
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.annotation;
	opens particia_trie to com.fasterxml.jackson.databind;
	opens com.Hybrides to com.fasterxml.jackson.databind;
	requires org.jfree.jfreechart;
	requires java.desktop;

}