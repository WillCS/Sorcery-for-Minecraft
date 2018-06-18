package sorcery.lib;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sorcery.api.SorceryAPI;
import sorcery.api.research.BlockInfo;
import sorcery.api.research.Page;
import sorcery.api.research.Research;
import sorcery.api.research.ResearchNode;
import sorcery.api.research.ResearchRecipe;
import sorcery.api.research.Structure;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.lib.utils.Utils;

public class ResearchLoader {
	public static Document research = null;
	
	public static HashMap<String, Entity> entityList = new HashMap<String, Entity>();
	public static HashMap<String, Structure> structureList = new HashMap<String, Structure>();
	
	public static String info;
	
	public static void loadResearch() {
		ResearchLoader.info = "sorcery";
		try {
			InputStream file = Config.class.getResourceAsStream("/assets/sorcery/research/research_en_US.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			research = builder.parse(file);
			research.getDocumentElement().normalize();
			
		} catch(Exception e) {
			Utils.log(Level.ERROR, "Failed to load the research document! Something is very wrong!");
		}
		
		try {
			NodeList list = research.getElementsByTagName("node");
			Research newResearch = new Research();
			
			for(int i = 0; i < list.getLength(); i++)
				newResearch.nodes.add(loadNode(list.item(i)));
			
			SorceryAPI.research = newResearch;
			
		} catch(Exception e) {
			e.printStackTrace();
			Utils.log(Level.ERROR, "Failed to parse the research document! Something is very wrong!");
		}
		ResearchLoader.info = null;
	}
	
	public static void reloadResearch() {
		System.out.println("sdad");
		research = null;
		SorceryAPI.research = null;
		loadResearch();
	}
	
	public static void loadExternalResearch(String resource, String info) {
		ResearchLoader.info = info;
		try {
			InputStream file = Config.class.getResourceAsStream("/assets/" + info + "/research/" + resource + ".xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			research = builder.parse(file);
			research.getDocumentElement().normalize();
			
		} catch(Exception e) {
			Utils.log(Level.ERROR, "Failed to load research document: " + resource + "!");
		}
		
		try {
			NodeList list = research.getElementsByTagName("node");
			
			for(int i = 0; i < list.getLength(); i++)
				SorceryAPI.research.nodes.add(loadNode(list.item(i)));
			
		} catch(Exception e) {
			e.printStackTrace();
			Utils.log(Level.ERROR, "Failed to parse research document: " + resource + "!");
		}
		
		ResearchLoader.info = null;
	}
	
	public static ResearchNode loadNode(Node node) {
		if(node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element)node;
			
			NodeList list = element.getChildNodes();
			ResearchNode newNode = new ResearchNode();
			
			if(element.hasAttribute("tier"))
				newNode.tier = Integer.parseInt(element.getAttribute("tier"));
			else newNode.tier = 0;
			
			if(element.hasAttribute("hidden"))
				newNode.hidden = Boolean.parseBoolean(element.getAttribute("hidden"));
			else newNode.hidden = false;
						
			for(int i = 0; i < list.getLength(); i++) {
				if(list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element)list.item(i);
					
					String name = element2.getNodeName();
					
					if(name.equals("page")) {
						newNode.pages.add(loadPage(element2));
					} else if(name.equals("title"))
						newNode.title = getCleanTextContent(element2);
					else if(name.equals("desc"))
						newNode.description = getCleanTextContent(element2);
					else if(name.equals("parent"))
						newNode.parents.add(getCleanTextContent(element2));
					else if(name.equals("hint"))
						newNode.hint = getCleanTextContent(element2);
					else if(name.equals("position")) {
						newNode.xCoord = element2.hasAttribute("x") ? Integer.parseInt(element2.getAttribute("x")) : 0;
						newNode.yCoord = element2.hasAttribute("y") ? -Integer.parseInt(element2.getAttribute("y")) : 0;
					} else if(name.equals("spellcomponent")) {
						newNode.components.add(SpellComponentBase.getComponentByName(
								getCleanTextContent(element2)));
						System.out.println(newNode.components.get(0).getName());
					} else if(name.equals("unlock")) {
						if(element2.getAttribute("type").equals("auto"))
							newNode.autoUnlocked = true;
						else if(element2.getAttribute("type").equals("field")) {
							Node researchObj = element2.getChildNodes().item(0);
							if(researchObj.getNodeName().equals("block")) {
								BlockInfo info = new BlockInfo();
								info.setBlock(getBlock(((Element)researchObj).getAttribute("name")));
								info.setMeta(((Element)researchObj).hasAttribute("damage") ?
										Integer.parseInt(((Element)researchObj).getAttribute("damage")) : 0);
								
								SorceryAPI.fieldResearchRecipes.add(new ResearchRecipe(info, newNode.title));
							} else if(researchObj.getNodeName().equals("entity")) {
								String entityType = ((Element)researchObj).getAttribute("type");
								SorceryAPI.fieldResearchRecipes.add(new ResearchRecipe(entityList.get(entityType), newNode.title));
							}
						}
					} else if(name.equals("icon")) {
						String type = element2.getAttribute("type");
						if(type.equals("icon")) {
							newNode.icon = new ResourceLocation(
									info + ":/research/textures/icons/" + element2.getAttribute("icon") + ".png");
						} else if(type.equals("item")) {
							Node researchObj = element2.getChildNodes().item(0);
							String itemID = element2.getAttribute("name");
							int itemDamage = element2.hasAttribute("damage") ? Integer.parseInt(element2.getAttribute("damage")) : 0;
							int itemAmount = element2.hasAttribute("amount") ? Integer.parseInt(element2.getAttribute("amount")) : 1;
							newNode.item = getItem(itemID, itemAmount, itemDamage);
						}
					} else if(name.equals("subnode")) {
						newNode.hasSubNodes = true;
						newNode.nodes.add(loadNode(list.item(i)));
					}
				}
			}
			return newNode;
		}
		
		return null;
	}
	
	public static Page loadPage(Node node) {
		if(node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element)node;
			
			Page page = new Page();

			page.dataType = element.getAttribute("type");
			page.heading = element.getAttribute("heading");
			page.hint = Boolean.parseBoolean(element.getAttribute("hint"));
			
			if(element.getAttribute("type").equals("text"))
				page.data = getCleanTextContent(element);
			else if(element.getAttribute("type").equals("image"))
				page.data = new ResourceLocation(
						info + ":/research/textures/" + getCleanTextContent(element) + ".png");
			else if(element.getAttribute("type").equals("recipe")) {
				NodeList list = element.getChildNodes();
				Node recipeNode = list.item(0);
				if(recipeNode.getNodeName().equals("recipe")) {
					Element tempNode = (Element)recipeNode;
					String id = tempNode.getAttribute("name");
					int damage = tempNode.hasAttribute("damage") ? Integer.parseInt(tempNode.getAttribute("damage")) : 0;
					int amount = tempNode.hasAttribute("amount") ? Integer.parseInt(tempNode.getAttribute("amount")) : 1;
					page.extraData = tempNode.getAttribute("type");
					page.data = getItem(id, damage, amount);
				}
			} else if(element.getAttribute("type").equals("enitity")) {
				if(entityList.containsKey(getCleanTextContent(element)))
						page.data = entityList.get(getCleanTextContent(element));
			} else if(element.getAttribute("type").equals("structure")) {
				if(structureList.containsKey(getCleanTextContent(element)))
					page.data = structureList.get(getCleanTextContent(element));
			}
			
			return page;
		}
		return null;
	}
	
	private static String getCleanTextContent(Element element) {
		return element.getTextContent().replaceAll("\n", " ").replaceAll("\t", "");
	}
	
	private static ItemStack getItem(String name, int damage, int amount) {
		ItemStack item;
		if((Item)Item.itemRegistry.getObject(name) != null) {
			Item stackItem = (Item)Item.itemRegistry.getObject(name);
			item = new ItemStack(stackItem, amount, damage);
			return item;
		} else if((Block)Block.blockRegistry.getObject(name) != null) {
			ItemBlock stackItem = new ItemBlock((Block)Block.blockRegistry.getObject(name));
			item = new ItemStack(stackItem, amount, damage);
			return item;
		} else return null;
	}
	
	private static Block getBlock(String identifier) {
		return (Block)Block.blockRegistry.getObject(identifier);
	}
	
	public static void addEntity(String key, Entity entity) {
		entityList.put(key, entity);
	}
	
	public static void addStructure(String key, Structure structure) {
		structureList.put(key, structure);
	}
}
