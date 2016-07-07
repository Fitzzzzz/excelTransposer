package commentsHandler;

import java.util.Arrays;

public class CommentReader {


	
	private int sourcePosition;
	public int getSourcePosition() {
		return sourcePosition;
	}

	public void setSourcePosition(int sourcePosition) {
		this.sourcePosition = sourcePosition;
	}


	private int commentPosition;
	public int getCommentPosition() {
		return commentPosition;
	}

	public void setCommentPosition(int commentPosition) {
		this.commentPosition = commentPosition;
	}


	private int statutPosition;
	
	public int getStatutPosition() {
		return statutPosition;
	}

	public void setStatutPosition(int statutPosition) {
		this.statutPosition = statutPosition;
	}

	public CommentReader(String comment) {
		
		String[] tmp = {comment};
		String[] tmp2 = split(tmp, "SOURCE:");
		String[] tmp3 = split(tmp2, "COMMENT:");
		words = split(tmp3, "STATUT:");
		
		sourcePosition = getStringPosition("SOURCE:", words);
		commentPosition = getStringPosition("COMMENT:", words);
		statutPosition = getStringPosition("STATUT:", words);
	}
	

	
	public String[] split(String[] text, String keyWord) {
		
		int tmp;
		String[] matchedArray;

		for (int i = 0; i < text.length; i++) {
			
			if (text[i].contains(keyWord)) {
				tmp = i;
				matchedArray = text[i].split(keyWord);

				String[] newText = new String[text.length + 2];
				
				for (int j = 0; j < tmp; j++) {
					newText[j] = text[j];
				}
				newText[tmp] = matchedArray[0];
				newText[tmp + 1] = keyWord;
				newText[tmp + 2] = matchedArray[1];
				
				for (int j = tmp + 1; j < text.length; j++) {
					newText[j + 2] = text[j];
				}
					
				return newText;
					
			}	
		}
		
		return text;
		
	}
	
	

	
	private String[] words;
	
	// Returns the position of a String in an array. If the string is absent, will return -1
	public int getStringPosition(String searchedWord, String[] text) {
		return java.util.Arrays.asList(text).indexOf(searchedWord);
	}
	
	public String stringArrayToString(String[] array) {
		
		String text = "";
		for (String word : array) {
			text = text + " " + word;
		}
		return text;
	}
	
	// Returns the words after "SOURCE:" until "COMMENT:", "STATUT:" or the end of the comment
	// Returns null if not inside : Change to return exception?
	// 
	public String getSource() {

		if (sourcePosition == -1) {
			return null;
		}
		else {
			if (commentPosition == -1) {
				if (statutPosition == -1) {
					return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, words.length));
				}
				else {
					if (statutPosition > sourcePosition) {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, statutPosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, words.length));
					}
				}
			}
			else {
				if (statutPosition == -1) {
					if (commentPosition > sourcePosition) {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, commentPosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, words.length));
					}
				}
				else {
					if (sourcePosition < Math.min(statutPosition, commentPosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, Math.min(statutPosition, commentPosition)));
					}
					else if (sourcePosition > Math.max(statutPosition, commentPosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, words.length));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, Math.max(statutPosition, commentPosition)));
					}
				}
			}
		}
	}
		
	public String getComment() {

		if (commentPosition == -1) {
			return null;
		}
		else {
			if (sourcePosition == -1) {
				if (statutPosition == -1) {
					return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, words.length));
				}
				else {
					if (statutPosition > commentPosition) {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, statutPosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, words.length));
					}
				}
			}
			else {
				if (statutPosition == -1) {
					if (sourcePosition > commentPosition) {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, sourcePosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, words.length));
					}
				}
				else {
					if (commentPosition < Math.min(statutPosition, sourcePosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, Math.min(statutPosition, sourcePosition)));
					}
					else if (commentPosition > Math.max(statutPosition, sourcePosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, words.length));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, commentPosition + 1, Math.max(statutPosition, sourcePosition)));
					}
				}
			}
		}	
	}
	
	public String getStatut() {

		if (statutPosition == -1) {
			return null;
		}
		else {
			if (commentPosition == -1) {
				if (sourcePosition == -1) {
					return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, words.length));
				}
				else {
					if (sourcePosition > statutPosition) {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, sourcePosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, words.length));
					}
				}
			}
			else {
				if (sourcePosition == -1) {
					if (commentPosition > statutPosition) {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, commentPosition));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, words.length));
					}
				}
				else {
					if (statutPosition < Math.min(sourcePosition, commentPosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, Math.min(sourcePosition, commentPosition)));
					}
					else if (statutPosition > Math.max(sourcePosition, commentPosition)) {
						return stringArrayToString(Arrays.copyOfRange(words, sourcePosition + 1, words.length));
					}
					else {
						return stringArrayToString(Arrays.copyOfRange(words, statutPosition + 1, Math.max(sourcePosition, commentPosition)));
					}
				}
			}
		}
	}
		
 	// copyOfRange(byte[] original, int from, int to)
}
