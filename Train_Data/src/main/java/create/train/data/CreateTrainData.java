package create.train.data;

import java.util.ArrayList;
import java.util.List;

public class CreateTrainData
{

	public static void main(String[] args)
	{
		CreateTrainData trainData = new CreateTrainData();
		trainData.run();
	}
	
	
	public void run()
	{
		List<String> objects = getObjects();
		List<String> attributes = getAttributes();
		List<String> colors = getColors();
		List<String> alignments = getAlignments();
		List<String> sizes = getSizes();
		List<String> sentences = createSentences(objects, attributes, colors, alignments, sizes);
		printSentences(sentences);
	}
	
	
	public List<String> getObjects()
	{
		List<String> objects = new ArrayList<String>();
		objects.add("header");
		objects.add("footer");
		objects.add("menu");
		objects.add("menu item");
		return objects;
	}
	
	
	public List<String> getAttributes()
	{
		List<String> attributes = new ArrayList<String>();
		attributes.add("color");
		attributes.add("height");
		attributes.add("width");
		attributes.add("alignment");
		attributes.add("create");
		return attributes;
	}
	
	
	public List<String> getColors()
	{
		List<String> values = new ArrayList<String>();
		values.add("blue");
		values.add("red");
		values.add("green");
		values.add("orange");
		values.add("yellow");
		return values;
	}

	
	public List<String> getSizes()
	{
		List<String> sizes = new ArrayList<String>();
		sizes.add("smaller");
		sizes.add("bigger");
		return sizes;
	}


	public List<String> getAlignments()
	{
		List<String> alignments = new ArrayList<String>();
		alignments.add("left");
		alignments.add("right");
		return alignments;
	}
	
	
	public List<String> createSentences(List<String> objects, List<String> attributes, List<String> colors,List<String> alignments, List<String> sizes)
	{
		List<String> sentences = new ArrayList<String>();
		
		for(String object : objects)
		{
			for(String attribute : attributes)
			{
				if(attribute.equals("color"))
				{
					for(String color : colors)
					{
						List<String> colorSentences = createColorSentences(object, attribute, color);
						sentences.addAll(colorSentences);
					}
				}
				if(attribute.equals("alignment"))
				{
					for(String alignment : alignments)
					{
						List<String> alignmentSentences = createAlignmentSentences(object, attribute, alignment);
						sentences.addAll(alignmentSentences);
					}
				}
				if(attribute.equals("height") || attribute.equals("width"))
				{
					for(String size : sizes)
					{
						List<String> sizeSentences = createSizeSentences(object, attribute, size);
						sentences.addAll(sizeSentences);
					}
				}
				if(attribute.equals("create"))
				{
					List<String> createSentences = createCreateSentences(object, attribute);
					sentences.addAll(createSentences);
				}
			}
		}		
		return sentences;
	}
	
	
	public List<String> createCreateSentences(String object, String attribute)
	{
		List<String> createSentences = new ArrayList<String>();
		//Make header
		createSentences.add("Make " + object + ", " + object + ", " + attribute);
		//Create header
		createSentences.add("Create " + object + ", " + object + ", " + attribute);
		//Add header
		createSentences.add("Add " + object + ", " + object + ", " + attribute);
		
		return createSentences;
	}


	public List<String> createSizeSentences(String object, String attribute, String size)
	{
		List<String> sizeSentences = new ArrayList<String>();
		//Make footer bigger
		sizeSentences.add("Make " + object + " " + size + ", " + object + ", " + attribute + ", " + size);
		//Make the footer bigger
		sizeSentences.add("Make the " + object + " " + size + ", " + object + ", " + attribute + ", " + size);
		//Make footer width bigger
		sizeSentences.add("Make " + object + " " + attribute + " " + size + ", " + object + ", " + attribute + ", " + size);
		//Make the width of the footer bigger
		sizeSentences.add("Make the " + object + " of the " + attribute + " " + size + ", " + object + ", " + attribute + ", " + size);
		//Increase footer width
		sizeSentences.add("Increase " + object + " " + attribute +  ", " + object + ", " + attribute + ", " + size);
		//Increase the footer width
		sizeSentences.add("Increase the " + object + " " + attribute +  ", " + object + ", " + attribute + ", " + size);
		//Increase the width of the footer
		sizeSentences.add("Increase the " + attribute + " of the " + object +  ", " + object + ", " + attribute + ", " + size);
		//Decrease footer width
		sizeSentences.add("Decrease " + object + " " + attribute +  ", " + object + ", " + attribute + ", " + size);
		//Decrease the footer width
		sizeSentences.add("Decrease the " + object + " " + attribute +  ", " + object + ", " + attribute + ", " + size);
		//Decrease the width of the footer
		sizeSentences.add("Decrease the " + attribute + " of the " + object +  ", " + object + ", " + attribute + ", " + size);
		return sizeSentences;
	}


	public List<String> createAlignmentSentences(String object, String attribute, String alignment)
	{
		List<String> alignmentSentences = new ArrayList<String>();
		//Move footer to left
		alignmentSentences.add("Move " + object + " to " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Move footer to the left
		alignmentSentences.add("Move " + object + " to the " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Move the footer to the left
		alignmentSentences.add("Move " + object + " to the " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Move the footer to the left side
		alignmentSentences.add("Move the " + object + " to the " + alignment + "side, " + object + ", " + attribute + ", " + alignment);
		//Set alignment of footer to left
		alignmentSentences.add("Set " + attribute + " of " + object + " to " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Set the alignment of footer to left
		alignmentSentences.add("Set the " + attribute + " of " + object + " to " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Set the alignment of the footer to left
		alignmentSentences.add("Set the " + attribute + " of the " + object + " to " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Set the alignment of the footer to the left
		alignmentSentences.add("Set the " + attribute + " of the " + object + " to the " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Set the alignment of the footer to the left side
		alignmentSentences.add("Set the " + attribute + " of the " + object + " to the " + alignment + "side, " + object + ", " + attribute + ", " + alignment);
		//Place footer left
		alignmentSentences.add("Place " + object + " " +  alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Place the footer left
		alignmentSentences.add("Place the " + object + " " +  alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Place the footer on the left
		alignmentSentences.add("Place the " + object + " on the " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Place footer on the left side
		alignmentSentences.add("Place " + object + " on the " + alignment + " side, " + object + ", " + attribute + ", " + alignment);
		//Place the footer on the left side
		alignmentSentences.add("Place the " + object + " on the " + alignment + " side, " + object + ", " + attribute + ", " + alignment);
		//Put footer left
		alignmentSentences.add("Put " + object + " " +  alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Put the footer left
		alignmentSentences.add("Put the " + object + " " +  alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Put the footer on the left
		alignmentSentences.add("Put the " + object + " on the " + alignment + ", " + object + ", " + attribute + ", " + alignment);
		//Put footer on the left side
		alignmentSentences.add("Put " + object + " on the " + alignment + " side, " + object + ", " + attribute + ", " + alignment);
		//Put the footer on the left side
		alignmentSentences.add("Put the " + object + " on the " + alignment + " side, " + object + ", " + attribute + ", " + alignment);
		
		
	
		return alignmentSentences;
	}


	public List<String> createColorSentences(String object, String attribute, String color)
	{
		List<String> colorSentences = new ArrayList<String>();
		//Make footer green
		colorSentences.add("Make " + object + " " +  color + ", " + object + ", " + attribute + ", " + color);
		//Make the footer green
		colorSentences.add("Make the " + object + " " +  color + ", " + object + ", " + attribute + ", " + color);
		//Change color of footer to green
		colorSentences.add("Change " + attribute + " of " + object + " to " + color + ", " + object + ", " + attribute + ", " + color);
		//Change the color of footer to green
		colorSentences.add("Change the " + attribute + " of " + object + " to " + color + ", " + object + ", " + attribute + ", " + color);
		//Change the color of the footer to green
		colorSentences.add("Change the " + attribute + " of the " + object + " to " + color + ", " + object + ", " + attribute + ", " + color);
		//I would like the footer to be green
		colorSentences.add("I would like the " + object + " to be " + color + ", " + object + ", " + attribute + ", " + color);
		//I would like a green footer
		colorSentences.add("I would like a " + color + " " + object + ", " + object + ", " + attribute + ", " + color);
		//I would like a green footer please
		colorSentences.add("I would like a " + color + " " + object + " please, " + object + ", " + attribute + ", " + color);
		//Give me a green footer
		colorSentences.add("Give me a " + color + " " +   object + ", " + object + ", " + attribute + ", " + color);
		//Get me a green footer
		colorSentences.add("Get me a " + color + " " +   object + ", " + object + ", " + attribute + ", " + color);
		//Make the footer green please
		colorSentences.add("Make the " + object + " " +  color + " please, " + object + ", " + attribute + ", " + color);
		//Can I get a green footer please
		colorSentences.add("Can I get a " + color + " " +  object + " please, " + object + ", " + attribute + ", " + color);
		//Can I get a green footer
		colorSentences.add("Can I get a " + color + " " +  object + ", " + object + ", " + attribute + ", " + color);
		//Is a green footer possible
		colorSentences.add("Is a " + color + " " +  object + " posible, " + object + ", " + attribute + ", " + color);
		//Can you make the footer green
		colorSentences.add("Can you make the " + object + " " +  color + ", " + object + ", " + attribute + ", " + color);
		//Make me a green footer please
		colorSentences.add("Make me a " + color + " " +  object + " please, " + object + ", " + attribute + ", " + color);
		//I want a green footer
		colorSentences.add("I want a " + color + " " +  object + " please, " + object + ", " + attribute + ", " + color);
		//I want a green footer
		colorSentences.add("I want a " + color + " " +  object + ", " + object + ", " + attribute + ", " + color);
		//The footer needs to be green
		colorSentences.add("The " + object + " needs to be " +  color + ", " + object + ", " + attribute + ", " + color);
		//The color of the footer needs to be green
		colorSentences.add("The " + attribute + " of the " +  object + " needs to be " + color + ", " + object + ", " + attribute + ", " + color);
		//Set the color of the footer to green
		colorSentences.add("Set the " + attribute + " of the " +  object + " to " + color + ", " + object + ", " + attribute + ", " + color);
		//Set the footer color to green
		colorSentences.add("Set the " + object + " " +  attribute + " to " + color + ", " + object + ", " + attribute + ", " + color);
		
		return colorSentences;
	}


	public void printSentences(List<String> sentences)
	{
		for(String sentence : sentences)
		{
			System.out.println(sentence);
		}
		
	}
}
