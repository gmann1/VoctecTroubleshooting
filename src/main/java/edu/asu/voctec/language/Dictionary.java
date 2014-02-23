package edu.asu.voctec.language;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.asu.voctec.GameDefaults;

public class Dictionary extends HashMap<LabelName, String> implements
		GameDefaults
{
	private static final long							serialVersionUID		= 9207065977205039134L;
	protected static final HashMap<String, Dictionary>	supportedLanguages		= new HashMap<>();
	
	protected final String								languageName;
	private static ArrayList<Character>					extraCharacters			= new ArrayList<>();
	private static char[]								extraCharactersArray	= {};
	
	private Dictionary(String languageName)
	{
		// construct the desired dictionary
		super();
		this.languageName = languageName.toLowerCase();
		
		// add dictionary to map
		supportedLanguages.put(this.languageName, this);
		
		// create a label name for the language (to be used by translation
		// buttons
		new LabelName(this.languageName, capitalize(this.languageName));
		
		// ensure all characters in languageName are accounted for
		addExtraCharacters(languageName);
	}
	
	// TODO move to utilities
	public static String capitalize(String string)
	{
		string = string.toLowerCase();
		String firstLetter = string.substring(0, 1);
		String tailLetters = string.substring(1);
		
		return firstLetter.toUpperCase() + tailLetters;
	}
	
	public static Dictionary constructDictionary(String languageName)
	{
		Dictionary matchingDictionary = supportedLanguages.get(languageName
				.toLowerCase());
		
		if (matchingDictionary == null)
			matchingDictionary = new Dictionary(languageName);
		
		return matchingDictionary;
	}
	
	public static Dictionary getDictionary(String language)
	{
		return supportedLanguages.get(language.toLowerCase());
	}
	
	public static String[] getSupportedLanguages()
	{
		Set<String> keySet = supportedLanguages.keySet();
		
		return keySet.toArray(new String[keySet.size()]);
	}
	
	public static boolean loadDictionaries()
	{
		return loadDictionaries(new File(XMLPaths.Dictionary));
	}
	
	public static boolean loadDictionaries(File dictionaryFile)
	{
		System.out.println("Loading Dictionaries...");
		boolean loadSuccessful = false;
		
		try
		{
			// Create and parse XML document
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			Document dictionaryDocument = documentBuilder.parse(dictionaryFile);
			NodeList supportedLanguageNodes; // List of nodes containing the
												// language of each dictionary
												// //There will be one
												// dictionary for each supported
												// language
			NodeList labelNodes; // List of nodes containing the names and
									// translations of all LabelNames
			
			// Eliminate empty nodes and normalize document
			dictionaryDocument.getDocumentElement().normalize();
			
			// Get supported languages and labels from document
			supportedLanguageNodes = dictionaryDocument
					.getElementsByTagName(DictionaryTags.SUPPORTED_LANGUAGE);
			labelNodes = dictionaryDocument
					.getElementsByTagName(DictionaryTags.LABEL_NAME);
			
			// Parse supportedLanguageNodes and create a dictionary for each
			// supported language
			for (int index = 0; index < supportedLanguageNodes.getLength(); index++)
			{
				Node currentNode = supportedLanguageNodes.item(index);
				Element languageElement = (Element) currentNode;
				String languageName = languageElement.getTextContent()
						.toLowerCase();
				Dictionary.constructDictionary(languageName);
				System.out.println("Supported Language: " + languageName);
			}
			
			// Parse labelNodes and add each translation to the appropriate
			// dictionary
			for (int labelIndex = 0; labelIndex < labelNodes.getLength(); labelIndex++)
			{
				// Parse the name of the current label
				Node currentNode = labelNodes.item(labelIndex);
				Element labelElement = (Element) currentNode;
				LabelName label = LabelName
						.getLabelNameByXMLListing(labelElement
								.getAttribute("id"));
				
				// Ignore all invalid/unsupported labels
				if (label != null)
				{
					System.out.println("Supported Label: " + label.xmlListing);
					// Get all translations of the current label
					NodeList translations = labelElement.getChildNodes();
					for (int languageIndex = 0; languageIndex < translations
							.getLength(); languageIndex++)
					{
						
						Node currentSubNode = translations.item(languageIndex);
						String languageName = currentSubNode.getNodeName()
								.toLowerCase();
						String translation = currentSubNode.getTextContent();
						
						// For each translation, add the translation to the
						// appropriate dictionary
						// Ignore invalid/unsupported languages
						Dictionary correspondingDictionary = supportedLanguages
								.get(languageName.toLowerCase());
						if (correspondingDictionary != null)
						{
							correspondingDictionary.put(label, translation);
							System.out.println("\t" + languageName + ": "
									+ translation);
						}
					}
				}
			}
			
			loadSuccessful = true;
			System.out.println("Dictionaries Loaded Successfully.");
			
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return loadSuccessful;
	}
	
	@Override
	public String put(LabelName key, String translation)
	{
		addExtraCharacters(translation);
		return super.put(key, translation);
	}
	
	public String getLanguageName()
	{
		return languageName;
	}
	
	public static char[] getExtraCharacters()
	{
		return Dictionary.extraCharactersArray;
	}
	
	public boolean addExtraCharacters(String extraCharacters)
	{
		return addExtraCharacters(extraCharacters.toCharArray());
	}
	
	public boolean addExtraCharacters(char[] extraCharacters)
	{
		boolean success = true;
		
		for (char character : extraCharacters)
		{
			if ((character > 256)
					&& (!Dictionary.extraCharacters
							.contains((Character) character)))
				success = success && Dictionary.extraCharacters.add(character);
		}
		
		updateExtraCharactersArray();
		
		return success;
	}
	
	private void updateExtraCharactersArray()
	{
		// TODO optimize: copy existing array to new array, and add new elements
		// only
		char[] extraCharacters = new char[Dictionary.extraCharacters.size()];
		
		// cast each Character object in this.extraCharacters to a primitive
		// char and add it to the extraCharacters array
		for (int index = 0; index < Dictionary.extraCharacters.size(); index++)
		{
			Character character = Dictionary.extraCharacters.get(index);
			extraCharacters[index] = character.charValue();
		}
		
		Dictionary.extraCharactersArray = extraCharacters;
	}
}
