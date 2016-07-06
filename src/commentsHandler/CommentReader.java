package commentsHandler;

import java.util.Arrays;

public class CommentReader {


	
	private int sourcePosition;
	private int commentPosition;
	private int statutPosition;
	
	public CommentReader(String comment) {
		this.comment = comment;
		splitComment();
		sourcePosition = getStringPosition("SOURCE:", words);
		commentPosition = getStringPosition("COMMENT:", words);
		statutPosition = getStringPosition("STATUT:", words);
	}
	
	public void splitComment() {
		
		words = comment.split("\\s+"); // This regex means "one or more spaces"
		
	}
	
	
	private final String comment;
	
	private String[] words;
	
	// Returns the position of a String in an array. If the string is absent, will return -1
	public int getStringPosition(String searchedWord, String[] text) {
		return java.util.Arrays.asList(text).indexOf(searchedWord);
	}
	
	// Returns the words after "SOURCE:" until "COMMENT:", "STATUT:" or the end of the comment
	// Returns null if not inside : Change to return exception?
	// 
	public String[] getSource() {

		if (sourcePosition == -1) {
			return null;
		}
		else {
			if (commentPosition == -1) {
				if (statutPosition == -1) {
					return Arrays.copyOfRange(words, sourcePosition + 1, words.length - 1);
				}
				else {
					if (statutPosition > sourcePosition) {
						return Arrays.copyOfRange(words, sourcePosition + 1, statutPosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, sourcePosition + 1, words.length - 1);
					}
				}
			}
			else {
				if (statutPosition == -1) {
					if (commentPosition > sourcePosition) {
						return Arrays.copyOfRange(words, sourcePosition + 1, commentPosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, sourcePosition + 1, words.length - 1);
					}
				}
				else {
					if (sourcePosition < Math.min(statutPosition, commentPosition)) {
						return Arrays.copyOfRange(words, sourcePosition + 1, Math.min(statutPosition, commentPosition) - 1);
					}
					else if (sourcePosition > Math.max(statutPosition, commentPosition)) {
						return Arrays.copyOfRange(words, sourcePosition + 1, words.length - 1);
					}
					else {
						return Arrays.copyOfRange(words, sourcePosition + 1, Math.max(statutPosition, commentPosition));
					}
				}
			}
		}
	}
		
	public String[] getComment() {

		if (commentPosition == -1) {
			return null;
		}
		else {
			if (sourcePosition == -1) {
				if (statutPosition == -1) {
					return Arrays.copyOfRange(words, commentPosition + 1, words.length - 1);
				}
				else {
					if (statutPosition > commentPosition) {
						return Arrays.copyOfRange(words, commentPosition + 1, statutPosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, commentPosition + 1, words.length - 1);
					}
				}
			}
			else {
				if (statutPosition == -1) {
					if (sourcePosition > commentPosition) {
						return Arrays.copyOfRange(words, commentPosition + 1, sourcePosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, commentPosition + 1, words.length - 1);
					}
				}
				else {
					if (commentPosition < Math.min(statutPosition, sourcePosition)) {
						return Arrays.copyOfRange(words, commentPosition + 1, Math.min(statutPosition, sourcePosition) - 1);
					}
					else if (commentPosition > Math.max(statutPosition, sourcePosition)) {
						return Arrays.copyOfRange(words, commentPosition + 1, words.length - 1);
					}
					else {
						return Arrays.copyOfRange(words, commentPosition + 1, Math.max(statutPosition, sourcePosition));
					}
				}
			}
		}	
	}
	
	public String[] getStatut() {

		if (statutPosition == -1) {
			return null;
		}
		else {
			if (commentPosition == -1) {
				if (sourcePosition == -1) {
					return Arrays.copyOfRange(words, statutPosition + 1, words.length - 1);
				}
				else {
					if (sourcePosition > statutPosition) {
						return Arrays.copyOfRange(words, statutPosition + 1, sourcePosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, statutPosition + 1, words.length - 1);
					}
				}
			}
			else {
				if (sourcePosition == -1) {
					if (commentPosition > statutPosition) {
						return Arrays.copyOfRange(words, statutPosition + 1, commentPosition - 1);
					}
					else {
						return Arrays.copyOfRange(words, statutPosition + 1, words.length - 1);
					}
				}
				else {
					if (statutPosition < Math.min(sourcePosition, commentPosition)) {
						return Arrays.copyOfRange(words, statutPosition + 1, Math.min(sourcePosition, commentPosition) - 1);
					}
					else if (statutPosition > Math.max(sourcePosition, commentPosition)) {
						return Arrays.copyOfRange(words, sourcePosition + 1, words.length - 1);
					}
					else {
						return Arrays.copyOfRange(words, statutPosition + 1, Math.max(sourcePosition, commentPosition));
					}
				}
			}
		}
	}
		
 	// copyOfRange(byte[] original, int from, int to)
}
