------------------------------------------------------------------------
This is the project 1 README file.
------------------------------------------------------------------------




PROJECT TITLE: Deterministic Finite Automaton
PURPOSE OF PROJECT: The purpose of the project is to make a finite automaton that can check if a sentence is correctly formed or not. It accepts as input a text file containing sentences with alphanumeric characters, spaces, and punctuation.
DATE: May 14th 2019
HOW TO START THIS PROJECT: Download this project file (entitled FSM). Go to BlueJ and open the project. The class FSMReader should come up. Right click to and run void main(String[] args), with the pathname of a plain text file as an argument e.g. “/User/sentences.txt”. Make sure you put “” around the file name. 
AUTHORS: Alice Marbach and Greer Hoffmann
USER INSTRUCTIONS:




Here are the rules that FSM follows to determine correctness:




Baseline Rules (written by Professor Jonathan Gordon):
1.  It consists of one or more sentences, each of which includes one or more words. 
2. Words don’t need to be real words in English or any other language; they just need to conform to these specifications. 
3. Each sentence ends with a period (.), an exclamation mark (!), or a question mark (?).
4.  Each punctuation mark is followed by a space. (Exception: You can ignore missing spaces if it’s the end of a line.)
5.  Inside a sentence, a colon (:), a comma (,), or a semicolon (;) can occur (at the end of a word, before a space). 
6.  Each sentence begins with a word in which the first character is an uppercase letter. 
7.  A word can start with any letter followed by zero or more lowercase letters. 
8.  Numbers can occur in the sentence. A number is one or more of the the digits 0 through 
9.   Each word or number is followed by a space or one of the allowed punctuation marks. 
10. There should be no spaces before the beginning of the first sentence and no double spaces inside a sentence. Double spaces can occur after sentences. 
11.  Hyphens are permitted, but only inside a word, so there should be at least one letter before and at least one letter after the hyphen. A word may contain multiple hyphens. 
12. A single apostrophe can appear inside a word or at the end of a word, as in the sample sentence: His brothers’ cars are green and he’s going to his sister’s house.




Additional Clarification of Rules added by Alice and Greer:
1. Empty sentences will be considered incorrect.
2. Any unidentifiable symbols in a sentence, i.e. that are not alphanumeric characters, spaces, and punctuation, will be determined incorrect with an error message explaining which character is causing the error. Unfortunately, brackets are not considered legal punctuation for this DFA.
3. More than one punctuation  in a row is considered incorrect. Ellipsis (...) is therefore not correct. 
4. Can have capital letter after hyphen e.g. “Alice-Pete initiative”. Just a capital then hyphen also ok e.g. A-P Initiative
5. Numbers can’t have hyphens: none of 9-0 or a-9. Cannot see a number after a hyphen in any case.
6. No apostrophe after numbers
7. Can’t have any uppercase in middle of word. E.g. RuthAnne not allowed.
8. No numbers inside words. E.g. 1a not allowed.
9. No hanging hyphen. E.g. Alice- not allowed. Can’t have a space following a hyphen.
10. Apostrophes can be followed by punctuation that terminates a clause or sentence. E.g. It was the brothers’. But no apostrophe followed hyphen e.g. brothers’-cat not allowed
11. No numbers following apostrophe.




Below are the explanation for states in the FSM. A table of the transition function of the FSM can be found in “Transition Function.pdf”.


START: Is the start state. Needs to see a capital letter or rejects. 
SUPPER: Seen an uppercase letter.
WSPACE: Seen : ; , punctuation, and is waiting on a space to be legal
WSPACET: Seen terminal punctuation !. ?, waiting on space but allowed a second space
WSPACED: This will only be reached if WSPACET has, i.e. a terminal punctuation has been seen. Seen a terminal and 1 space. Can see a second space.
WSPACE2: Seen a terminal and 2 spaces. A third space is not allowed
WWORD:  Waiting on finishing a word i.e. still inside a word and can follow with letters, punctuation or space.
WNEW: Waiting on new word. Not same as START because does not have to be uppercase.
SHYPHEN: Seen a hyphen, Following character must be uppercase or lowercase. No numbers.
SAPOS: Seen an apostrophe, so need to follow with lowercase letter, space, or punctuation.
WNUMBER: Seen a number, can have either a space or another number.*/
REJ: Reject State. 
ACCEPT: Accept state.