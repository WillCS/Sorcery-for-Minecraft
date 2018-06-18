package sorcery.guis;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import sorcery.api.ISpellBook;
import sorcery.api.SorceryAPI;
import sorcery.api.element.ElementStack;
import sorcery.api.research.Research;
import sorcery.api.spellcasting.CastInfo;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.SpellComponentBase.ComponentType;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;
import sorcery.api.spellcasting.SpellComponentBase.SpellFocus;
import sorcery.client.render.RenderUtils;
import sorcery.client.render.geometry.Sector;
import sorcery.containers.ContainerDesk;
import sorcery.core.Sorcery;
import sorcery.guis.button.GuiButtonFixed;
import sorcery.guis.button.GuiButtonItemBoolean;
import sorcery.guis.button.GuiButtonSorcery;
import sorcery.items.ItemResearchJournal;
import sorcery.items.ItemSpellTome;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import sorcery.network.TileEntityPacket;
import sorcery.tileentities.TileEntityDesk;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDesk extends GuiContainerSorcery {
	private TileEntityDesk inventory;
	private static ResourceLocation[] backgrounds = new ResourceLocation[] {
			Utils.getResource("textures/guis/wandcrafting.png"),
			Utils.getResource("textures/guis/spellbookEditor.png"),
			Utils.getResource("textures/guis/spellcrafting.png")
	};
	private int debounce = 0;
	
	private int offset = 0;
	
	private ArrayList<GuiButtonFixed> buttons = new ArrayList<GuiButtonFixed>();

	private int scroll = 0;
	private int selectedNo = -1;
	
	/** Spellbook Fields*/
	private GuiTextField spellSearch;
	
	/** Spellcrafting Fields */
	private ComponentType selectedType;
	private GuiTextField componentSearch;
	private GuiTextField[] fields;
	private int selectedField = -1;
	private int lastSelectedField = -1;
	private static int[][][] fieldData = new int[][][] {
		new int[][] { new int[] { 11, 69 }, new int[] { 156, 14 } },
		new int[][] { new int[] { 11, 87 }, new int[] { 69, 14 } },
		new int[][] { new int[] { 82, 87 }, new int[] { 69, 14 } },
		new int[][] { new int[] { 153, 87 }, new int[] { 14, 14 } },
		new int[][] { new int[] { 11, 103 }, new int[] { 156, 14 } },
		new int[][] { new int[] { 11, 121 }, new int[] { 69, 14 } },
		new int[][] { new int[] { 82, 121 }, new int[] { 69, 14 } },
		new int[][] { new int[] { 153, 121 }, new int[] { 14, 14 } } };
	private static ComponentType[] fieldTypes = new ComponentType[] {
		null, 							ComponentType.action, 	ComponentType.focus, 
		null,		   					ComponentType.focusAdj, ComponentType.effect,
		ComponentType.effectCondition,	null };
	public ArrayList<SpellComponent> components = new ArrayList<SpellComponent>();
	public Research research;
	public int mojoCost;
	public ElementStack[] elementcost;
	public Sector mojo = new Sector();
	public Sector[] elements;
	public boolean spellComplete = false;
	public int levelCost = 0;
	
	public GuiDesk(InventoryPlayer player, TileEntityDesk tile, World world, int i, int j, int k) {
		super(new ContainerDesk(player, tile, world, i, j, k), "wandcrafting");
		this.inventory = tile;
		//this.sendRequestPacket();
	}
	
	@Override
	public void initGui() {
		this.ySize = 166 + 40;
		if(this.inventory.currentActivity == 1 || this.inventory.currentActivity == 2) {
			this.xSize = 176 + 176;
			this.offset = 176;
		} else {
			this.xSize = 176 + 40;
			this.offset = 20;
		}
		
        Keyboard.enableRepeatEvents(true);
		super.initGui();
		
		for(int i = 0; i < 3; i++) {
			if(this.inventory.IconItems[i] != null) {
				int buttonOffset = 0;
				if(this.offset == 20) buttonOffset = 20;
				else buttonOffset = 88;
					
				this.buttonList.add(
						new GuiButtonItemBoolean(i, this.guiLeft + buttonOffset + 5 + i * 19, this.guiTop - 5, this.inventory.IconItems[i], this.inventory.currentActivity == i));
			}
		}
		
		this.addButtons();
	}
	
	private void addButtons() {
		this.buttons.clear();
		this.spellSearch = null;
		this.componentSearch = null;
		
		if(this.inventory.currentActivity == 1 && this.inventory.spells != null && !this.inventory.spells.isEmpty()) {
			this.spellSearch = new GuiTextField(this.fontRendererObj, this.guiLeft + 100, this.guiTop + 24, 52, 16);
			this.spellSearch.setEnabled(true);
			this.spellSearch.setVisible(true);
			this.spellSearch.setEnableBackgroundDrawing(true);
			
			this.buttons.add(new GuiButtonSorcery(5, this.guiLeft + 15, this.guiTop + 23, "/\\"));
			this.buttons.add(new GuiButtonSorcery(6, this.guiLeft + 36, this.guiTop + 23, "\\/"));
			this.buttons.add(new GuiButtonSorcery(7, this.guiLeft + 57, this.guiTop + 23, ">"));
			this.buttons.add(new GuiButtonSorcery(8, this.guiLeft + 78, this.guiTop + 23, "x2"));
		}
		
		if(this.inventory.currentActivity == 2) {
			this.componentSearch = new GuiTextField(this.fontRendererObj, this.guiLeft + 100, this.guiTop + 24, 52, 16);
			this.componentSearch.setEnabled(true);
			this.componentSearch.setVisible(true);
			this.componentSearch.setEnableBackgroundDrawing(true);
			
			this.buttons.add(new GuiButtonSorcery(10, this.guiLeft + 57, this.guiTop + 23, ">"));
			
			this.fields = new GuiTextField[8];
			
			for(int i = 0; i < this.fields.length; i++) {
				this.fields[i] = new GuiTextField(this.fontRendererObj, this.getFieldPosition(i, 0), this.getFieldPosition(i, 1), 
						this.getFieldSize(i, 0), this.getFieldSize(i, 1));
				
				this.fields[i].setEnabled(true);
				this.fields[i].setVisible(true);
			}
		
			if(this.inventory.spell != null) {
				if(this.inventory.spell.name != null && !this.inventory.spell.name.isEmpty())
					this.fields[0].setText(this.inventory.spell.name);
				
				if(this.inventory.spell.action != null)
					this.fields[1].setText(this.reverseTranslate(this.inventory.spell.action.component.getName()));
				
				if(this.inventory.spell.focus != null) {
					this.fields[2].setText(this.reverseTranslate(this.inventory.spell.focus.component.getName()));
					this.fields[3].setText(this.inventory.spell.focus.magnitude + "");
					
					if(this.inventory.spell.focus.adjectives != null) {
						for(int i = 0; i < this.inventory.spell.focus.adjectives.length; i++) {
							SpellAdjective adj = this.inventory.spell.focus.adjectives[i];
							if(adj != null) {
								String splitter = inventory.spell.focus.adjectives.length == i + 1 ? "" : ", ";
								this.fields[4].setText(this.fields[4].getText() + this.reverseTranslate(adj.getName()) + splitter);
							}
						}
					}
				}
				
				if(this.inventory.spell.effect != null) {
					this.fields[5].setText(this.reverseTranslate(this.inventory.spell.effect.component.getName()));
					if(this.inventory.spell.effect.condition != null) 
						this.fields[6].setText(this.reverseTranslate(this.inventory.spell.effect.condition.getName()));
					this.fields[7].setText(this.inventory.spell.effect.magnitude + "");
				}
			}
			
			this.fields[3].setMaxStringLength(1);
			this.fields[7].setMaxStringLength(1);
		}
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.desk." + this.inventory.currentActivity), this.offset + 8, 26, 4210752);
		if(this.inventory.currentActivity != 2)
			this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), this.offset + 8, this.ySize - 136 + 22, 4210752);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-this.guiLeft, -this.guiTop, 0F);
		for(int i = 0; i < this.buttons.size(); i++) {
			this.buttons.get(i).drawButton(this.mc, par1, par2);
		}
		
		GL11.glPopMatrix();
		
		switch(this.inventory.currentActivity) {
			case 0: this.doWandCraftingForegroundUpdate();
				break;
			case 1: this.doBookWritingForegroundUpdate();
				break;
			case 2: this.doSpellCraftingForegroundUpdate();
				break;
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.guiWidth = (this.width - this.xSize) / 2;
		this.guiHeight = (this.height - this.ySize) / 2;
		this.guiTexture = this.backgrounds[this.inventory.currentActivity];
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(this.guiTexture);
		this.drawTexturedModalRect(this.guiWidth + this.offset, this.guiHeight + 20, 0, 0, 176, 166);

		RenderUtils.bindTexture(this.backgrounds[0]);
		int buttonOffset = 0;
		if(this.offset == 20) buttonOffset = 20;
		else buttonOffset = 88;
		this.drawTexturedModalRect(this.guiWidth + buttonOffset, this.guiHeight - 10, 176, 0, 66, 28);
		
		for(int i = 0; i < 3; i++) {
			GuiButtonItemBoolean button = (GuiButtonItemBoolean)this.buttonList.get(i);
			button.Icon = this.inventory.IconItems[i];
		}
		
		RenderUtils.bindTexture(((GuiButtonItemBoolean)this.buttonList.get(0)).texture);
		
		for(int i = 0; i < 3; i++) {
			if(this.inventory.currentActivity == i)
				this.drawTexturedModalRect(((GuiButton)this.buttonList.get(i)).xPosition, 
						((GuiButton)this.buttonList.get(i)).yPosition, 182, 18, 18, 18);
			else
				this.drawTexturedModalRect(((GuiButton)this.buttonList.get(i)).xPosition, 
						((GuiButton)this.buttonList.get(i)).yPosition, 182, 0, 18, 18);
		}
		
		if(this.scroll < 0)
			this.scroll = 0;
		
		RenderUtils.bindTexture(this.guiTexture);
		switch(this.inventory.currentActivity) {
			case 0: this.doWandCraftingBackgroundUpdate();
				break;
			case 1: this.doBookWritingBackgroundUpdate();
				break;
			case 2: this.doSpellCraftingBackgroundUpdate();
				break;
		}
		
		if(this.inventory.currentActivity == 0) this.xSize -= 20;
		this.guiTop += 20;
		this.drawHelpButton();
		this.guiTop -= 20;
		if(this.inventory.currentActivity == 0) this.xSize += 20;
	}
	
	public void doWandCraftingBackgroundUpdate() {
		
	}

	public void doWandCraftingForegroundUpdate() {
		
	}
	
	public void doBookWritingBackgroundUpdate() {
		if(this.inventory.getStackInSlot(0) != null && this.inventory.getStackInSlot(0).getItem() instanceof ISpellBook) {
			if(!(this.inventory.getStackInSlot(0).getItem() instanceof ItemSpellTome))
				this.drawTexturedModalRect(this.guiWidth - 20 + this.offset, this.guiHeight + 20, 176, 112, 26, 26);
		}
		
		ArrayList<Spell> tempSpells = new ArrayList<Spell>();
		
		if(this.spellSearch != null && !this.spellSearch.getText().isEmpty()) {
			for(int i = 0; i < this.inventory.spells.size(); i++) {
				if(this.inventory.spells.get(i).name.startsWith(this.spellSearch.getText())) {
					tempSpells.add(this.inventory.spells.get(i));
				}
			}
		} else if(this.inventory.spells != null) {
			tempSpells = this.inventory.spells;
		}
		
		if(tempSpells != null && tempSpells.size() > 0) {
			int j = 0;
			for(int i = this.scroll; i < this.scroll + 12; i++) {
				if(i < tempSpells.size()) {
					if(i == this.selectedNo)
						this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 223, 160, 11);
					else if(i % 2 == 0)
						this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 234, 160, 11);
					else
						this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 245, 160, 11);
					j++;
				}

			}
		}
		
		if(this.selectedNo > tempSpells.size() - 1)
			this.selectedNo = -1;
		
		if(this.inventory.spells == null)
			this.scroll = 0;
		
		if(this.inventory.spells == null && !this.buttons.isEmpty()) {
			this.addButtons();
		} else if(this.inventory.spells != null && this.buttons.isEmpty()) {
			this.addButtons();
		}
		
		for(int i = 0; i < this.buttons.size(); i++) {
			this.buttons.get(i).enabled = this.selectedNo == -1 ? false : true;
			
			if(this.buttons.get(i).id == 8 && this.selectedNo != -1) {
				this.buttons.get(i).enabled = 
						(this.inventory.getStackInSlot(1) != null && this.inventory.getStackInSlot(1).getItem() == Items.paper);
			}
		}
		
		if(this.spellSearch != null) {
			if(this.spellSearch.isFocused() || !this.spellSearch.getText().isEmpty()) {
				this.buttons.get(0).enabled = false;
				this.buttons.get(1).enabled = false;
			} else if(!this.spellSearch.isFocused() && this.spellSearch.getText().isEmpty()){
				this.buttons.get(0).enabled = true;
				this.buttons.get(1).enabled = true;
			}
			
			this.spellSearch.drawTextBox();
		}
	}
	
	public void doBookWritingForegroundUpdate() {
		if(this.inventory.getStackInSlot(0) == null && this.debounce == 0) {
			this.fontRendererObj.drawSplitString( StatCollector.translateToLocal("sorcery.research.insertSpellbook"),
					0, 80, 176, -1);
			
		} else if(this.inventory.spells != null && this.inventory.spells.size() > 0) {
			ArrayList<Spell> tempSpells = new ArrayList<Spell>();
			
			if(this.spellSearch != null && !this.spellSearch.getText().isEmpty()) {
				for(int i = 0; i < this.inventory.spells.size(); i++) {
					if(this.inventory.spells.get(i).name.startsWith(this.spellSearch.getText())) {
						tempSpells.add(this.inventory.spells.get(i));
					}
				}
			} else if(this.inventory.spells != null) {
				tempSpells = this.inventory.spells;
			}
			
			int j = 0;
			int k = tempSpells.size() > 12 ? 12 : tempSpells.size();
			for(int i = this.scroll; i < this.scroll + k; i++) {
				if(i < tempSpells.size()) {
					String name = tempSpells.get(i).name;
					if(name.length() > 25)
						name = name.substring(0, 25) + "...";
					this.drawString(this.fontRendererObj, name, -160 + this.offset, 50 + (11 * j), Utils.encodeColour(255, 255, 255));
					j++;
				}
			}
		}
	}
	
	public void doSpellCraftingBackgroundUpdate() {		
		if(this.inventory.getStackInSlot(0) != null && this.inventory.getStackInSlot(0).getItem() instanceof ItemResearchJournal) {
			if(this.components == null) {
				ArrayList<SpellComponent> components =
						SpellHelper.instance.getSpellComponentsFromResearch(this.inventory.getStackInSlot(0), this.inventory.getWorldObj());
				
				if(!components.isEmpty() && this.selectedType != null) {
					int i = 0;
					while(i > components.size()) {
						if(components.get(i).component.type != this.selectedType)
							components.remove(i);
						else i++;
					}
				}
				
				this.components = components;
			} else this.components = null;
		} else this.components = null;
		
		if(this.componentSearch!= null)
			this.componentSearch.drawTextBox();
		
		if(this.fields != null) {
			for(GuiTextField field : this.fields) {
				field.drawTextBox();
			}
		}
		
		if(this.research != null && this.selectedType != null) {
			ArrayList<SpellComponent> tempComponents = this.getCompatibleComponents();
			
			if(tempComponents != null && tempComponents.size() > 0) {
				int j = 0;
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				RenderUtils.bindTexture(this.backgrounds[1]);
				for(int i = this.scroll; i < this.scroll + 12; i++) {
					if(i < tempComponents.size()) {
						if(i == this.selectedNo)
							this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 223, 160, 11);
						else if(i % 2 == 0)
							this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 234, 160, 11);
						else
							this.drawTexturedModalRect(this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 0, 245, 160, 11);
						j++;
					}
	
				}
			}
			
			if(this.selectedNo > tempComponents.size() - 1)
				this.selectedNo = -1;
			
			if(tempComponents == null || tempComponents.isEmpty())
				this.scroll = 0;
			
			if(this.selectedNo > tempComponents.size() - 1)
				this.selectedNo = -1;
		}
		
		for(int i = 0; i < this.buttons.size(); i++) {
			this.buttons.get(i).enabled = this.selectedNo == -1 ? false : true;
			
			if(this.buttons.get(i).id == 8 && this.selectedNo != -1) {
				this.buttons.get(i).enabled = 
						(this.inventory.getStackInSlot(1) != null && this.inventory.getStackInSlot(1).getItem() == Items.paper);
			}
		}
		
		if(this.fields != null) {
			for(int i = 0; i < fieldTypes.length; i++) {
				if(this.fields[i] != null && this.fields[i].isFocused()) {
					this.fields[i].setTextColor(14737632);
					if(fieldTypes[i] != null) {
						this.selectedType = fieldTypes[i];
					} else this.selectedType = null;
				}
			}
			
			int currentSelectedField = -1;
			for(int i = 0; i < this.fields.length; i++) {
				if(this.fields[i].isFocused()) {
					currentSelectedField = i;
					this.lastSelectedField = i;
				}
				
				if(this.fields[i] != null && !this.checkField(i).isEmpty()) {
					this.fields[i].setTextColor(15763748);
					this.spellComplete = false;
				} else this.fields[i].setTextColor(14737632);
			}
			
			if(this.spellComplete) {
				CastInfo info = this.inventory.spell.getCastInfo();
				int mojo = info.mojoCost;
				int maxMojo = SpellHelper.instance.getPlayerMaxMojo(FMLClientHandler.instance().getClientPlayerEntity());
				double proportion = ((double)mojo) / ((double)maxMojo);
				
				ElementStack[] elements = info.elements;
				this.elements = new Sector[elements.length];
				int total = 0;
				for(ElementStack element : elements) {
					if(element != null)
						total += element.amount;
				}
				
				this.mojo.setColour(Utils.encodeColour(0, 10, 207, 255), Utils.encodeColour(74, 79, 180, 255));
				this.mojo.setAngle(-proportion * (2.0D * Math.PI), Math.PI);
				this.mojo.draw(10, this.guiLeft + 251, this.guiTop + 51);
				
				double previousAngle = 0.0D;
				
				for(int i = 0; i < elements.length; i++) {
					ElementStack element = elements[i];
					if(element != null) {
						this.elements[i] = new Sector();
						proportion = ((double)element.amount / ((double)total));
						
						float[] rgb = element.element.getFloatColour();
						float[] rgb2 = new float[rgb.length];
						
						for(int j = 0; j < rgb.length; j++) {
							rgb2[j] = rgb[j] * 0.8F;
						}
						
						this.elements[i].setColour(Utils.encodeColour(rgb2[0], rgb2[1], rgb2[2], 1.0F),
							Utils.encodeColour(rgb[0], rgb[1], rgb[2], 1.0F));
						this.elements[i].setAngle(-proportion * (2.0D * Math.PI), Math.PI - (previousAngle * (2.0D * Math.PI)));
						this.elements[i].draw(10, this.guiLeft + 275, this.guiTop + 51);
						
						previousAngle += proportion;
					}
				}
			}
			
			RenderUtils.bindTexture(this.backgrounds[2]);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.guiLeft + 240, this.guiTop + 40, 176, 0, 22, 22);
			this.drawTexturedModalRect(this.guiLeft + 264, this.guiTop + 40, 176, 0, 22, 22);
			
			if(currentSelectedField != this.selectedField && this.selectedField != -1) {
				this.sendPacket(this.selectedField);
				if(!this.checkField(this.selectedField).isEmpty()) {
					this.fields[this.selectedField].setTextColor(15763748);
				} else this.fields[this.selectedField].setTextColor(14737632);
			}
			
			this.selectedField = currentSelectedField;
		}
		
		if(this.selectedType == null && this.componentSearch != null) {
			this.componentSearch.setEnabled(false);
			//this.componentSearch.setVisible(false);
			this.buttons.get(0).enabled = false;
		} else if(this.componentSearch != null) {
			this.componentSearch.setEnabled(true);
			//this.componentSearch.setVisible(true);
			this.buttons.get(0).enabled = true;
		}
		
		if(this.inventory.getStackInSlot(0) != null && this.research == null) {
			ItemStack item = this.inventory.getStackInSlot(0);
			if(item.getItem() instanceof ItemResearchJournal && item.hasTagCompound()) {
				String owner = item.stackTagCompound.getString("owner");
				if(owner.equals("cheaty"))
					this.research = SorceryAPI.research;
				else if(this.inventory.getWorldObj().getPlayerEntityByName(owner) != null)
					this.research = Research.readFromNBT(this.inventory.getWorldObj().getPlayerEntityByName(owner));
				else if(this.inventory.getWorldObj().getPlayerEntityByName(owner) == null)
					this.research = Research.readFromNBT(FMLClientHandler.instance().getClientPlayerEntity());
			}
		} else if(this.inventory.getStackInSlot(0) == null && this.research != null) {
			this.research = null;
		}
	}
	
	public void doSpellCraftingForegroundUpdate() {
		if(this.spellComplete && this.inventory.spell != null) {
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			CastInfo info = this.inventory.spell.getCastInfo();
			int mojo = info.mojoCost;
			int maxMojo = SpellHelper.instance.getPlayerMaxMojo(FMLClientHandler.instance().getClientPlayerEntity());
			double proportion = ((double)mojo) / ((double)maxMojo);
			this.levelCost = (int)(30 * proportion * 2);
			String printString = this.levelCost <= 40 ?
					StatCollector.translateToLocalFormatted("sorcery.container.desk.2.info.level", levelCost) :
					StatCollector.translateToLocal("sorcery.container.desk.2.warning.level");

			this.fontRendererObj.drawString(printString, 176 + 50, 70, 4210752);
		}
		
		if(this.inventory.getStackInSlot(0) == null && this.debounce == 0) {
			this.fontRendererObj.drawSplitString(
					StatCollector.translateToLocal("sorcery.research.insertJournal"), 0, 80, 176, -1);
		} else if(this.inventory.getStackInSlot(1) == null && this.debounce == 0) {
			this.fontRendererObj.drawSplitString(
					StatCollector.translateToLocal("sorcery.research.insertPage"), 0, 80, 176, -1);
		}
		
		if(this.research != null && this.selectedType != null) {
			ArrayList<SpellComponent> tempComponents = this.getCompatibleComponents();
			
			int j = 0;
			int k = tempComponents.size() > 12 ? 12 : tempComponents.size();
			for(int i = this.scroll; i < this.scroll + k; i++) {
				if(i < tempComponents.size()) {
					String name = this.reverseTranslate(tempComponents.get(i).component.getName());
					if(name.length() > 25)
						name = name.substring(0, 25) + "...";
					this.drawString(this.fontRendererObj, name, -160 + this.offset, 50 + (11 * j), Utils.encodeColour(255, 255, 255));
					j++;
				}
			}
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.spellComplete = true;
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		
		if(this.debounce != 0)
			this.debounce--;
		
		if(this.debounce < 0)
			this.debounce = 0;
		
		if(this.inventory.currentActivity == 2 && this.fields != null) {
			if(this.isMouseInsideArea(par1, par2, this.guiLeft + 242, this.guiTop + 42, 18, 18)) {
				for(int i = 0; i < this.fields.length; i++) {
					if(this.fields[i] != null && !this.checkField(i).isEmpty()) {
						this.spellComplete = false;
						break;
					}
				}
				
				if(this.spellComplete) {
					ArrayList<String> list = new ArrayList<String>();
					list.add(StatCollector.translateToLocal("sorcery.container.desk.2.info.mojo"));
					list.add(EnumChatFormatting.GRAY + "" + 
							this.inventory.spell.getCastInfo().mojoCost + " / " + SpellHelper.instance.getPlayerMaxMojo(
							FMLClientHandler.instance().getClientPlayerEntity()));
					this.drawToolTip(list, par1, par2);
				}
			}
			
			if(this.isMouseInsideArea(par1, par2, this.guiLeft + 266, this.guiTop + 42, 18, 18)) {
				for(int i = 0; i < this.fields.length; i++) {
					if(this.fields[i] != null && !this.checkField(i).isEmpty()) {
						this.spellComplete = false;
						break;
					}
				}
				
				if(this.spellComplete) {
					ArrayList<String> list = new ArrayList<String>();
					list.add(StatCollector.translateToLocal("sorcery.container.desk.2.info.element"));
					for(ElementStack element : this.inventory.spell.getCastInfo().elements) {
						if(element != null) {
							list.add(EnumChatFormatting.GRAY + "" + 
									StatCollector.translateToLocal("sorceryapi.element." + element.element.getName() + ".name") +
									" x" + element.amount);
						}
					}
					this.drawToolTip(list, par1, par2);
				}
			}
		}
	}
	
	@Override
	public ArrayList<String> getHelpTooltip(int mouseX, int mouseY) {
		if(this.inventory.currentActivity == 2) {
			if(this.fields != null) {
				for(int i = 0; i < this.fields.length; i++) {
					if(this.fields[i] != null) {
						if(this.isMouseInsideArea(mouseX, mouseY, this.getFieldPosition(i, 0), this.getFieldPosition(i, 1), 
								this.getFieldSize(i, 0), this.getFieldSize(i, 1))) {
							ArrayList<String> list = new ArrayList<String>();
							String[] lines = StatCollector.translateToLocal("sorcery.container.desk.2.info." + i).split("//");
							for(String line : lines) {
								list.add(line);
							}
							
							if(!this.checkField(i).isEmpty()) {
								lines = this.checkField(i).split("_~-'<");
								if(lines.length != 0) {
									list.add(StatCollector.translateToLocal(lines[0]));
									if(lines.length > 1) {
										for(int j = 1; j < lines.length; j++) {
											list.add(lines[j]);
										}
									}
								}
							}
							return list;
						}
					}
				}
			}
		}
		
		return super.getHelpTooltip(mouseX, mouseY);
	}
	
	public String checkField(int field) {
		if(field == 0) {
			if(this.fields[field].getText() == null || this.fields[field].getText().isEmpty())
				return StatCollector.translateToLocal("sorcery.container.desk.2.warning.required");
			else return "";
		} else if(field == 3 || field == 7) {
			if(field == 3) {
				if(this.fields[field].getText() == null || this.fields[field].getText().isEmpty())
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.required");
			} else {
				if(this.fields[field].getText() != null && !this.fields[field].getText().isEmpty()) {
					if(this.fields[5].getText() == null || this.fields[5].getText().isEmpty())
						return StatCollector.translateToLocal("sorcery.container.desk.2.warning.noEffect");
					else if(this.fields[5].getText() != null && !this.fields[5].getText().isEmpty())
						return StatCollector.translateToLocal("sorcery.container.desk.2.warning.magnitude");
				} else if(this.fields[field].getText() == null || this.fields[field].getText().isEmpty()) {
					return "";
				}
			}
			
			try {
				Integer.parseInt(this.fields[field].getText());
			} catch(Exception e) {
				return StatCollector.translateToLocal("sorcery.container.desk.2.warning.numbers");
			}
				if(Integer.parseInt(this.fields[field].getText()) == 0)
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.numbers");
		} else {
			if(field == 1 || field == 2 || field == 3 || field == 4) {
				if(this.fields[field].getText() == null || this.fields[field].getText().isEmpty())
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.required");
			}
			
			String text = (this.fields[field].getText() != null && !this.fields[field].getText().isEmpty()) ?
					this.fields[field].getText().toLowerCase() : "null";
					
			if(text.equals("null") || text.equals("")) {
				if(field == 1 || field == 2 || field == 3) {
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.required");
				} else if(field == 5 || field == 6) {
					return "";
				}
			}
			
			if(text.contains("  ") || text.contains(" , ") || text.contains(",,"))
				return StatCollector.translateToLocal("sorcery.container.desk.2.warning.invalid");

			String[] decodedNames = text.split(", ");
			
			for(int i = 0; i < decodedNames.length; i++) {
				decodedNames[i] = decodedNames[i].replace(",", "");
				
				if(decodedNames[i].endsWith(" "))
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.invalid");
			}
			
			if(field != 4 && decodedNames.length > 1)
				return StatCollector.translateToLocal("sorcery.container.desk.2.warning.tooMany");
				
			String unrecognized = "";
			
			for(int i = 0; i < decodedNames.length; i++) {
				if(decodedNames[i] == null) {
					continue;
				} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) != null) {
					if(this.research != null) {
						SpellComponent component = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(
								Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i])));
						boolean contains = false;
						for(SpellComponent c : this.research.getSpellComponents()) {
							if(c.component.equals(component.component)) contains = true;
						}
						if(!contains)
							unrecognized = unrecognized + "_~-'<" + decodedNames[i];
					} else unrecognized = unrecognized + "_~-'<" + decodedNames[i];
				} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) == null) {
					if(this.research != null) {
						for(SpellComponent component : this.research.getSpellComponents()) {
							if(component != null && component.component.getName().equals(decodedNames[i]) && component.hasCustomData())
								continue;
						}
					}
					unrecognized = unrecognized + "_~-'<" + decodedNames[i];
				}
 			}
			
			if(!unrecognized.isEmpty())
				return StatCollector.translateToLocal("sorcery.container.desk.2.warning.unrecognized") + unrecognized;
			
			
			if(field != 4) {
				if(SpellComponentBase.getComponentByName(
								Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(this.fields[field].getText()))  != null) {
					SpellComponentBase component = SpellComponentBase.getComponentByName(
							Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(this.fields[field].getText()));
					
					if(component.getType() != this.fieldTypes[field]) {
						return StatCollector.translateToLocal("sorcery.container.desk.2.warning.incorrectType");
					}
				}
			} else if(field == 4) {
				unrecognized = "";

				SpellAdjective[] components1 = new SpellAdjective[decodedNames.length];
				ArrayList<SpellAdjective> incompatible1 = new ArrayList<SpellAdjective>();
				
				for(int i = 0; i < decodedNames.length; i++) {
					if(decodedNames[i] == null) {
						continue;
					} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) != null) {
						if(this.research != null) {
							SpellComponentBase component = SpellComponentBase.getComponentByName(
									Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]));
							if(component != null) {
								if(component.getType() != ComponentType.focusAdj)
									unrecognized = unrecognized + "_~-'<" + decodedNames[i];
								else
									components1[i] = (SpellAdjective)component;
							}
						}
					}
				}
					
				if(!unrecognized.isEmpty())
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.notAdjective") + unrecognized;

				for(int i = 0; i < components1.length; i++) {
					for(int j = 0; j < components1.length; j++) {
						if(i != j && !components1[i].isComponentCompatible(components1[j])) {
							incompatible1.add(components1[i]);
							incompatible1.add(components1[j]);
						}
						
						if(components1[i] == components1[j] && i != j) {
							return StatCollector.translateToLocal("sorcery.container.desk.2.warning.duplicateAdjective");
						}
					}
				}
				
				if(!incompatible1.isEmpty()) {
					String ret = StatCollector.translateToLocal("sorcery.container.desk.2.warning.conflict") + "_~-'<";
					
					for(int k = 0; k < incompatible1.size(); k += 2) {
						ret = ret + reverseTranslate(incompatible1.get(k).getName());
						ret = ret + ", " + reverseTranslate(incompatible1.get(k + 1).getName());
						ret = ret + "_~-'<";
					}
					
					return ret;
				}
			}
			
			if(field == 2) {
				text = (this.fields[4].getText() != null && !this.fields[4].getText().isEmpty()) ?
						this.fields[4].getText().toLowerCase() : "null";
						
				if(text.equals("null") || text.equals("")) {
					if(!this.getComponentsInField(2).isEmpty()) {
						SpellComponent<SpellFocus> c = this.getComponentsInField(2).get(0);
						if(c.component.needsAdjectives())
							return StatCollector.translateToLocal("sorcery.container.desk.2.warning.adjective");
					}
				}
				
				if(text.contains("  ") || text.contains(" , ") || text.contains(",,"))
					return "";

				decodedNames = text.split(", ");
				
				for(int i = 0; i < decodedNames.length; i++) {
					decodedNames[i] = decodedNames[i].replace(",", "");
					
					if(decodedNames[i].endsWith(" "))
						return "";
				}
				
				SpellFocus action = (SpellFocus)SpellComponentBase.getComponentByName(
						Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(this.fields[field].getText()));
						
				SpellAdjective[] components = new SpellAdjective[decodedNames.length];
				ArrayList<SpellAdjective> incompatible = new ArrayList<SpellAdjective>();
				
				for(int i = 0; i < decodedNames.length; i++) {
					if(decodedNames[i] == null) {
						continue;
					} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) != null) {
						if(this.research != null) {
							SpellComponent component = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(
									Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i])));
							
							if(component != null && component.component instanceof SpellAdjective)
								components[i] = (SpellAdjective)component.component;
							
						}
					} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) == null) {
						if(this.research != null) {
							for(SpellComponent component : this.research.getSpellComponents()) {
								if(component != null && component.component.getName().equals(decodedNames[i]) && component.component instanceof SpellAdjective)
									components[i] = (SpellAdjective)component.component;
							}
						}
					}
				}
				
				for(SpellAdjective component : components) {
					if(action != null && component != null && !action.isComponentCompatible(component))
						incompatible.add(component);
				}
				
				if(!incompatible.isEmpty()) {
					String ret = StatCollector.translateToLocal("sorcery.container.desk.2.warning.compatible");
					
					for(SpellAdjective adj : incompatible) {
						ret = ret + "_~-'<" + reverseTranslate(adj.getName());
					}
					
					return ret;
				}
			}
			
			if(this.fields[5].getText() == null || this.fields[5].getText().isEmpty()) {
				if(field == 6)
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.condition");
				if(field == 7)
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.magnitude");
			} else if(this.fields[5].getText() != null && !this.fields[5].getText().isEmpty()) {
				if(field == 6 && (this.fields[6].getText() == null || this.fields[6].getText().isEmpty()))
					return StatCollector.translateToLocal("sorcery.container.desk.2.warning.noEffect");
			}
		}
		
		return "";
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(this.debounce != 0)
			return;
		
		this.debounce = 5;
		
		if(par1GuiButton.id <= 2) {
			this.inventory.currentActivity = par1GuiButton.id;
			this.inventory.cooldown = 2;
			this.inventory.sendPlayerPacket(par1GuiButton.id);
			this.inventory.markDirty();
		}
		
		if(par1GuiButton.id == 5) {
			if(this.selectedNo != 0)
				this.scrollUp();
		} else if(par1GuiButton.id == 6 && this.selectedNo != this.inventory.spells.size() - 1) {
			this.scrollDown();
		} else if(par1GuiButton.id == 7) {
			this.removeSpell();
		} else if(par1GuiButton.id == 8) {
			if(this.inventory.getStackInSlot(1) != null && this.inventory.getStackInSlot(1).getItem() == Items.paper) {
				this.copySpell();
			}
		} else if(par1GuiButton.id == 10) {
			if(this.selectedNo != -1)
				this.addComponent();
		}
	}
	
	private void scrollUp() {
		if(this.selectedNo == -1)
			return;
		
		this.inventory.sendDataPacket((byte)2, this.selectedNo, -1);
		this.inventory.moveSpell(this.selectedNo, -1);
		this.selectedNo--;
		
		if(this.selectedNo < this.scroll) {
			this.scroll--;
			if(this.scroll < 0)
				this.scroll = 0;
		}
	}
	
	private void scrollDown() {
		if(this.selectedNo == -1)
			return;
		
		this.inventory.sendDataPacket((byte)2, this.selectedNo, 1);
		this.inventory.moveSpell(this.selectedNo, 1);
		this.selectedNo++;
		
		if(this.selectedNo > this.scroll + 11) {
			this.scroll++;
			if(this.scroll > this.inventory.spells.size() - 11)
				this.scroll = this.inventory.spells.size() - 11;
		}
	}
	
	protected void keyTyped(char par1, int par2) {
		if(this.inventory.currentActivity == 1 && this.spellSearch != null && this.spellSearch.isFocused()) {
			if(par2 == 1) {
				this.spellSearch.setFocused(false);
				this.spellSearch.setText("");
				return;
			}
			
			this.spellSearch.textboxKeyTyped(par1, par2);
			this.handleMouseInput();
			return;
		}
		
		if(this.inventory.currentActivity == 2 && this.componentSearch != null && this.componentSearch.isFocused()) {
			if(par2 == 1) {
				this.componentSearch.setFocused(false);
				this.componentSearch.setText("");
				return;
			}
			
			this.componentSearch.textboxKeyTyped(par1, par2);
			this.handleMouseInput();
			return;
		}
		
		if(this.inventory.currentActivity == 2 && this.fields != null) {
			for(int i = 0; i < this.fields.length; i++) {
				if(this.fields[i].isFocused()) {
					if(par2 == 1) {
						this.fields[i].setFocused(false);
						return;
					}
					
					if(par1 == 13) {
						this.fields[i].setFocused(false);
						if(i != 7)	{
							this.fields[i + 1].setFocused(true);
						} else {
							this.fields[0].setFocused(true);
						}
					}
					
					this.fields[i].textboxKeyTyped(par1, par2);
					this.sendPacket(i, this.fields[i].getText());
					
					this.handleMouseInput();
					return;
				}
			}
			
			if(this.spellSearch != null && !this.spellSearch.getText().isEmpty())
				if(par2 == Keyboard.KEY_UP || par2 == Keyboard.KEY_DOWN)
					return;
		}
		
		super.keyTyped(par1, par2);
		
		if(this.debounce != 0)
			return;
		this.debounce = 5;
		
		if(par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.inventory.sendDataPacket((byte)1, 0);
			this.inventory.isInUse = false;
		}
		
		if(this.inventory.currentActivity == 1) {
			if(this.inventory.spells != null) {
				if(par2 == Keyboard.KEY_UP) {
					if(this.selectedNo != 0) {
						this.scrollUp();
					}
				} else if(par2 == Keyboard.KEY_DOWN) {
					if(this.selectedNo != this.inventory.spells.size() - 1) {
						this.scrollDown();
					}
				} else if(par2 == Keyboard.KEY_RIGHT) {
					this.removeSpell();
				}
			}
		}
		
		if(this.inventory.currentActivity == 2) {
			if(this.research != null && this.selectedType != null) {
				if(par2 == Keyboard.KEY_RIGHT) {
					this.addComponent();
				}
			}
		
		}
	}
	
	public ArrayList<SpellComponent> getCompatibleComponents() {
		ArrayList<SpellComponent> tempComponents = this.getComponentsForSelectedType();
		ArrayList<SpellComponent> ret = new ArrayList<SpellComponent>();
		
		for(SpellComponent c: this.getComponentsForSelectedType()) {
			for(SpellComponent d : this.getComponentsInField(this.lastSelectedField)) {
				if(c != null && d != null && c.component.isComponentCompatible(d.component) &&
						d.component.isComponentCompatible(c.component)) {
					boolean compat = true;
					for(SpellComponent e : this.getComponentsInField(this.lastSelectedField)) {
						if(c != null && e != null && (!c.component.isComponentCompatible(e.component) ||
								!e.component.isComponentCompatible(c.component)))
							compat = false;
					}
					if(compat && !ret.contains(c))
						ret.add(c);
				}
			}
		}
		
		if(ret.isEmpty())
			return tempComponents;
		
		return ret;
	}
	
	public ArrayList<SpellComponent> getComponentsForSelectedType() {
		ArrayList<SpellComponent> tempComponents = new ArrayList<SpellComponent>();
		if(this.componentSearch != null && !this.componentSearch.getText().isEmpty()) {
			for(SpellComponent comp : this.research.getSpellComponents()) {
				if(comp != null && this.reverseTranslate(comp.component.getName()).startsWith(this.componentSearch.getText())
						&& comp.component.getType() == this.selectedType) {
					tempComponents.add(comp);
				}
			}
		} else if(this.research != null) {
			for(SpellComponent comp : this.research.getSpellComponents()) {
				if(comp != null && comp.component.getType() == this.selectedType) {
					tempComponents.add(comp);
				}
			}
		}
		
		return tempComponents;
	}
	
	public ArrayList<SpellComponent> getComponentsInField(int field) {
		ArrayList<SpellComponent> ret = new ArrayList<SpellComponent>();
		
		String text = (this.fields[field].getText() != null && !this.fields[field].getText().isEmpty()) ?
				this.fields[field].getText().toLowerCase() : "null";
				
		if(text.equals("null") || text.equals(""))
			return new ArrayList<SpellComponent>();

		String[] decodedNames = text.split(", ");
		
		for(int i = 0; i < decodedNames.length; i++) {
			decodedNames[i] = decodedNames[i].replace(",", "");
		}
		
		for(int i = 0; i < decodedNames.length; i++) {
			if(decodedNames[i] == null) {
				continue;
			} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) != null) {
				if(this.research != null) {
					SpellComponent component = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(
							Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i])));
					boolean contains = false;
					for(SpellComponent c : this.research.getSpellComponents()) {
						if(c.component.equals(component.component)) contains = true;
					}
					if(contains)
						ret.add(component);
				}
			} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) == null) {
				if(this.research != null) {
					for(SpellComponent component : this.research.getSpellComponents()) {
						if(component != null && component.component.getName().equals(decodedNames[i]) && component.hasCustomData())
							ret.add(component);
					}
				}
			}
		}
		
		return ret;
	}
	
	public void addComponent() {
		if(this.selectedNo != -1) {
			ArrayList<SpellComponent> tempComponents = this.getCompatibleComponents();
			
			if(this.fields != null && this.fields[this.lastSelectedField] != null && !tempComponents.isEmpty()) {
				if(!this.fields[this.lastSelectedField].getText().isEmpty()) {
					if(this.fields[this.lastSelectedField].getText().endsWith(","))
						this.fields[this.lastSelectedField].setText(this.fields[this.lastSelectedField].getText() + " ");
					else if(!this.fields[this.lastSelectedField].getText().endsWith(", "))
						this.fields[this.lastSelectedField].setText(this.fields[this.lastSelectedField].getText() + ", ");
				}
				
				if(this.lastSelectedField == 4) {
					this.fields[this.lastSelectedField].setText(this.fields[this.lastSelectedField].getText() + 
							this.reverseTranslate(tempComponents.get(this.selectedNo).component.getName()));
				} else {
					this.fields[this.lastSelectedField].setText(
							this.reverseTranslate(tempComponents.get(this.selectedNo).component.getName()));
				}
			}
		}
		
		this.sendPacket(this.lastSelectedField);
	}
	
    public boolean isPunctuation(char c) {
        return c == ','
            || c == '.'
            || c == '!'
            || c == '?'
            || c == ':'
            || c == '&'
            || c == '['
            || c == ']'
    		|| c == '"'
    		|| c == '>'
     		|| c == '<'
            || c == '=';
    }
	
	public void removeSpell() {
		if(this.selectedNo == -1)
			return;
		
		ArrayList<Spell> tempSpells = new ArrayList<Spell>();
		
		if(!this.spellSearch.getText().isEmpty()) {
			for(int i = 0; i < this.inventory.spells.size(); i++) {
				if(this.inventory.spells.get(i).name.startsWith(this.spellSearch.getText())) {
					tempSpells.add(this.inventory.spells.get(i));
				}
			}
		} else if(this.inventory.spells != null) {
			tempSpells = this.inventory.spells;
		}
		
		Spell spell = tempSpells.get(this.selectedNo);
		int noToRemove = -1;
		
		for(int i = 0; i < this.inventory.spells.size(); i++) {
			if(this.inventory.spells.get(i) == spell)
				noToRemove = i;
		}
		
		if(noToRemove != -1) {
			this.inventory.sendDataPacket((byte)3, noToRemove);
			tempSpells.remove(this.selectedNo);
		}
		
		if(this.selectedNo == tempSpells.size() - 1) {
			this.selectedNo--;
			
			if(this.selectedNo == 12)
				this.selectedNo--;
			
			this.scroll--;
			if(this.scroll == -1)
				this.scroll = 0;
		}
	}
	
	public void copySpell() {
		if(this.selectedNo == -1)
			return;
		
		ArrayList<Spell> tempSpells = new ArrayList<Spell>();
		
		if(!this.spellSearch.getText().isEmpty()) {
			for(int i = 0; i < this.inventory.spells.size(); i++) {
				if(this.inventory.spells.get(i).name.startsWith(this.spellSearch.getText())) {
					tempSpells.add(this.inventory.spells.get(i));
				}
			}
		} else if(this.inventory.spells != null) {
			tempSpells = this.inventory.spells;
		}
		
		Spell spell = tempSpells.get(this.selectedNo);
		int noToCopy = -1;
		
		for(int i = 0; i < this.inventory.spells.size(); i++) {
			if(this.inventory.spells.get(i) == spell)
				noToCopy = i;
		}
		
		if(noToCopy != -1) {
			this.inventory.sendDataPacket((byte)4, noToCopy);
		}
	}
	
	public void handleMouseInput() {
		super.handleMouseInput();
		
		if(Mouse.getEventDWheel() != 0) {
			if(this.inventory.currentActivity == 1 && this.inventory.spells != null) {
				int scrollAmount = Mouse.getEventDWheel() / 120;
				
				this.scroll -= scrollAmount;

				if(this.scroll < 0)
					this.scroll = 0;
				
				ArrayList<Spell> tempSpells = new ArrayList<Spell>();
				
				if(!this.spellSearch.getText().isEmpty()) {
					for(int i = 0; i < this.inventory.spells.size(); i++) {
						if(this.inventory.spells.get(i).name.startsWith(this.spellSearch.getText())) {
							tempSpells.add(this.inventory.spells.get(i));
						}
					}
				} else if(this.inventory.spells != null) {
					tempSpells = this.inventory.spells;
				}
				
				if(this.scroll > tempSpells.size() - 12)
					this.scroll = tempSpells.size() - 12;
			}
			
			if(this.inventory.currentActivity == 2 && this.research != null && this.selectedType != null) {
				int scrollAmount = Mouse.getEventDWheel() / 120;
				
				this.scroll -= scrollAmount;

				if(this.scroll < 0)
					this.scroll = 0;
				
				ArrayList<SpellComponent> tempComponents = new ArrayList<SpellComponent>();
				
				if(this.componentSearch != null && !this.componentSearch.getText().isEmpty()) {
					for(SpellComponent comp : this.research.getSpellComponents()) {
						if(comp != null && this.reverseTranslate(comp.component.getName()).startsWith(this.componentSearch.getText())
								&& comp.component.getType() == this.selectedType) {
							tempComponents.add(comp);
						}
					}
				} else if(this.research != null) {
					for(SpellComponent comp : this.research.getSpellComponents()) {
						if(comp != null && comp.component.getType() == this.selectedType) {
							tempComponents.add(comp);
						}
					}
				}
				
				if(this.scroll > tempComponents.size() - 12)
					this.scroll = tempComponents.size() - 12;
			}
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		if(this.inventory.currentActivity == 2) {
			if(this.isMouseInsideArea(par1, par2, this.guiLeft + this.offset + 152, this.guiTop + 43, 18, 18)) {
				if(this.spellComplete && this.inventory.getStackInSlot(2) != null && this.mc.thePlayer.experienceLevel >= this.levelCost) {
					this.sendPacket(-1);
				} else if(this.spellComplete && this.inventory.getStackInSlot(2) != null && this.mc.thePlayer.capabilities.isCreativeMode) {
					this.sendPacket(-1);
				}
			}
		}
		
		super.mouseClicked(par1, par2, par3);
		
		if(this.inventory.currentActivity == 0) this.xSize -= 20;
		this.guiTop += 20;
		
		if(this.isMouseInsideArea(par1, par2, this.guiLeft + this.xSize - 22, this.guiTop - 25, 18, 18)) {
			this.helpMode = !this.helpMode;
		} else if(this.helpMode){
			this.helpMode = false;
		}
		
		this.guiTop -= 20;
		if(this.inventory.currentActivity == 0) this.xSize += 20;

		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		int j = 0;
		for(int i = this.scroll; i < this.scroll + 12; i++) {
			if(this.isMouseInsideArea(par1, par2, this.guiWidth - 162 + this.offset, this.guiHeight + 48 + (11 * j), 160, 11))
				this.selectedNo = i;
			
			j++;
		}
		
		if(par3 == 0) {
			for(int l = 0; l < this.buttons.size(); ++l) {
				if(this.buttons.get(l).enabled) {
					GuiButtonFixed guibutton = (GuiButtonFixed)this.buttons.get(l);
					if(this.isMouseInsideArea(par1, par2, guibutton.xPosition, guibutton.yPosition,
							guibutton.width, guibutton.height)) {
						guibutton.func_146113_a(this.mc.getSoundHandler());
						this.actionPerformed(guibutton);
					}
				}
			}
		} 
		
		
		if(this.spellSearch != null) {
			if(this.isMouseInsideArea(par1, par2, this.guiLeft + 100, this.guiTop + 23, 50, 20)) {
				this.spellSearch.setFocused(true);
			} else
				this.spellSearch.setFocused(false);
		}
		
		if(this.componentSearch != null) {
			if(this.isMouseInsideArea(par1, par2, this.guiLeft + 100, this.guiTop + 23, 50, 20)) {
				this.componentSearch.setFocused(true);
			} else
				this.componentSearch.setFocused(false);
		}
		
		if(this.inventory.currentActivity == 2 && this.fields != null) {
			for(int i = 0; i < this.fields.length; i++) {
				if(this.fields[i] != null) {
					if(this.isMouseInsideArea(par1, par2, this.getFieldPosition(i, 0), this.getFieldPosition(i, 1), 
							this.getFieldSize(i, 0), this.getFieldSize(i, 1))) {
						this.fields[i].setFocused(true);
					} else
						this.fields[i].setFocused(false);
				}
			}
		}
	}
	
	public boolean isMouseInsideArea(int mouseX, int mouseY, int areaLeft, int areaTop, int width, int height) {
		if(mouseX >= areaLeft && mouseX <= areaLeft + width)
			if(mouseY >= areaTop && mouseY <= areaTop + height)
				return true;
		
		return false;
	}
	
	private int getFieldPosition(int field, int orientation) {
		return (orientation == 0 ? this.guiLeft + 175 : 19 + this.guiTop) + fieldData[field][0][orientation];
	}
	
	private int getFieldSize(int field, int orientation) {
		return fieldData[field][1][orientation];
	}
	
	private void sendPacket(int field) {
		if(field != -1)
			this.sendPacket(field, this.fields[field].getText());
		else {
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("id", 6);
			data.setString("name", this.mc.thePlayer.getCommandSenderName());
			if(this.spellComplete) {
				if(this.mc.thePlayer.capabilities.isCreativeMode) {
					data.setInteger("cost", 0);
				} else if(this.levelCost <= this.mc.thePlayer.experienceLevel) {
					data.setInteger("cost", this.levelCost);
				}
			}
			
			TileEntityPacket packet = new TileEntityPacket(data, this.inventory.xCoord, this.inventory.yCoord, this.inventory.zCoord);
			this.mc.thePlayer.experienceLevel -= this.levelCost;
			Sorcery.packetPipeline.sendToServer(packet);
		}
	}
	
	private void sendPacket(int field, String str) {
		NBTTagCompound data = new NBTTagCompound();
		if(field != 0) {
			if(field == 3 || field == 7) {
				int magnitude = 0;
				try {
					magnitude = Integer.parseInt(str);
				} catch(Exception e) {
					magnitude = 0;
				} finally {
					data.setInteger("magnitude", magnitude);
				}
			} else {
				NBTTagList list = new NBTTagList();
				String text = (str != null && !str.isEmpty()) ?
						str.toLowerCase() : "null";
				String[] decodedNames = text.split(", ");
				for(int i = 0; i < decodedNames.length; i++) {
					while(decodedNames[i].endsWith(" ") || decodedNames[i].endsWith(","))
						decodedNames[i] = decodedNames[i].substring(0, decodedNames[i].length() - 1);
					
				}
				
				for(int i = 0; i < decodedNames.length; i++) {
					NBTTagCompound tempTag = new NBTTagCompound();
					if(decodedNames[i] != null && Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) != null) {
						tempTag.setString("component", Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]));
						list.appendTag(tempTag);
					} else if(decodedNames[i] != null) {
						tempTag.setString("component", "null");
						list.appendTag(tempTag);
					} else if(Properties.spellNames.get(Sorcery.proxy.getLanguage()).get(decodedNames[i]) == null) {
						if(this.research != null) {
							for(SpellComponent component : this.research.getSpellComponents()) {
								tempTag.setString("component", decodedNames[i]);
								tempTag.setBoolean("custom", component.hasCustomData());
								tempTag.setTag("customData", component.customData);
								list.appendTag(tempTag);
							}
						}
					}
				}
				data.setTag("components", list);
			}
		} else {
			if(str == null || str.isEmpty()) 
				data.setString("name", "\n");
			
			data.setString("name", str);
		}
			
		data.setInteger("field", field);
		data.setInteger("id", 5);
		
		if(this.checkField(field) == null || this.checkField(field).isEmpty())
			this.spellComplete = true;
		else this.spellComplete = false;
		
		if(this.spellComplete &&
				(this.levelCost <= this.mc.thePlayer.experienceLevel || this.mc.thePlayer.capabilities.isCreativeMode))
			data.setBoolean("complete", this.spellComplete);
		
		TileEntityPacket packet = new TileEntityPacket(data, this.inventory.xCoord, this.inventory.yCoord, this.inventory.zCoord);
		this.inventory.setSpell(data);
		Sorcery.packetPipeline.sendToServer(packet);
	}

	public void sendRequestPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		TileEntityPacket packet = new TileEntityPacket(tag, this.inventory.xCoord, this.inventory.yCoord, this.inventory.zCoord);
		packet.setSpecialID(TileEntityPacket.REQUEST_ID);
		Sorcery.packetPipeline.sendToServer(packet);
	}
	
	public String reverseTranslate(String text) {
		text = "sorcery.spell.component." + text.replaceAll("_", ".") + ".name";
		return StatCollector.translateToLocal(text);
	}
}
