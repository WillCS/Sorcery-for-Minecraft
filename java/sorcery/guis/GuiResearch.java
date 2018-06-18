package sorcery.guis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.api.SorceryAPI;
import sorcery.api.research.Page;
import sorcery.api.research.Research;
import sorcery.api.research.ResearchNode;
import sorcery.client.render.RenderUtils;
import sorcery.guis.button.GuiButtonResearch;
import sorcery.lib.ResearchLoader;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;

public class GuiResearch extends GuiScreen {
	public ItemStack book;
	public EntityPlayer player;
	
	public int page;
	public ResearchNode openView;
	public ArrayList<ResearchNode> currentResearch;
	public Research research;
	public ArrayList<Page> currentPages;
	
	public int debounce = 0;
	
	public int mouseX;
	public int mouseY;
	public int lastMouseX;
	public int lastMouseY;
	
	public int offsetX;
	public int offsetY;
	public int lastOffsetX;
	public int lastOffsetY;
	
	public int xSize;
	public int ySize;
	
	public ScaledResolution res;
	
	public float zoom = 0;
	
	public static ResourceLocation background = Utils.getResource("textures/guis/researchViewer.png");
	
	public GuiResearch(EntityPlayer player, World world, int x, int y, int z) {
		this.xSize = this.width;
		this.ySize = this.height;
		this.player = player;
		this.book = player.getCurrentEquippedItem();
		if(book.stackTagCompound.getString("owner").equals("cheaty book"))
			this.research = SorceryAPI.research;
		else
			this.research = Research.readFromNBT(player);
		
		this.currentResearch = this.research.nodes;
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		this.mouseX = i;
		this.mouseY = j;
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		RenderUtils.bindTexture(this.background);
		
		this.drawResearch();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glPushMatrix();
		this.zLevel = -50.0F;
		RenderUtils.itemRenderer.zLevel = -50.0F;
		this.drawWorldBackground(0);
    	GL11.glTranslatef((float)(this.res.getScaledWidth() / 2), (float)(res.getScaledHeight() / 2), 0F);
		super.drawScreen(par1, par2, par3);
		
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		this.drawGuiContainerForegroundLayer(par1, par2);
		
		if(this.openView != null) {
	    	this.zLevel = -31.0F;
			this.drawResearchPage(this.openView);
		}
    	this.zLevel = 0.0F;
		
    	GL11.glTranslatef(-(float)(this.res.getScaledWidth() / 2), -(float)(this.res.getScaledHeight() / 2), 0F);
		GL11.glPopMatrix();
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
    public void initGui() {
    	if(this.openView != null) {
        	this.buttonList.clear();
    		
    		if(this.openView.unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book"))
    			this.currentPages = this.openView.pages;
    		else if(!this.openView.unlocked) {
    			 ArrayList<Page> temp = new ArrayList<Page>();
				 for(int i = 0; i < this.openView.pages.size(); i++) {
					 if(this.openView.pages.get(i).hint)
						 temp.add(this.openView.pages.get(i));
				 }
				 this.currentPages = temp;
    		}
    		
    		if(this.currentPages.size() != this.page + 1)
    			this.buttonList.add(new GuiButtonResearch(0, this.openView, 63, 70, this));
    		
    		if(this.page != 0)
    			this.buttonList.add(new GuiButtonResearch(1, this.openView, -80, 70, this));
    		
    	} else this.buttonList.clear();
    }
    
	@Override
	public void updateScreen() {
		if(Mouse.isButtonDown(0) && this.openView == null) {
			int localOffsetX = this.mouseX - this.lastMouseX;
			int localOffsetY = this.mouseY - this.lastMouseY;
			
			this.offsetX = this.lastOffsetX + localOffsetX;
			this.offsetY = this.lastOffsetY + localOffsetY;
		}
		
		if(this.debounce != 0)
			this.debounce--;
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
		this.lastMouseX = par1;
		this.lastMouseY = par2;
		
		this.lastOffsetX = offsetX;
		this.lastOffsetY = offsetY;
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

	    if(Mouse.getEventButtonState() && this.openView == null) {
			 int button = Mouse.getEventButton();
			 if(button == 0) {
				 for(int i = 0; i < this.currentResearch.size(); i++) {
					 if(this.isMouseInsideArea((int)(this.width / 2) + this.offsetX + (this.currentResearch.get(i).xCoord * 20) - 15, 
			    				(int)(this.height / 2) + this.offsetY + (this.currentResearch.get(i).yCoord * 20) - 15, 30, 30)) {
						 if(this.currentResearch.get(i).unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book")) {
							 if(!this.currentResearch.get(i).pages.isEmpty()) {
								 this.openView = this.currentResearch.get(i);
								 this.initGui();
							 }
							 
							 if(this.currentResearch.get(i).hasSubNodes) {
								 ResearchNode tempNode = this.currentResearch.get(i);
								 this.currentResearch = (ArrayList<ResearchNode>)this.currentResearch.get(i).nodes.clone();
								 this.currentResearch.add(tempNode);
								 this.initGui();
								 return;
							 }
						 }
						 
						 if(!this.currentResearch.get(i).unlocked) {
							 ArrayList<Page> temp = new ArrayList<Page>();
							 for(int j = 0; j < this.currentResearch.get(i).pages.size(); j++) {
								 if(this.currentResearch.get(i).pages.get(j).hint)
									 temp.add(this.currentResearch.get(i).pages.get(j));
							 }
							 
							 if(!temp.isEmpty()) {
								 this.openView = this.currentResearch.get(i);
								 this.initGui();
							 }
						 }
					 }
				 }
			 }
	    }
    }
	    
		/*if(Mouse.getEventDWheel() != 0) {
			int dir = Mouse.getEventDWheel() < 0 ? 1 : 0;
			
			if(dir == 0 && this.zoom < 5)
				this.zoom +=  0.5;
			else if(dir == 1 && this.zoom > -5)
				this.zoom -= 0.5;
			
			if(this.zoom > 5) this.zoom = 5;
			
			if(this.zoom < -5) this.zoom = -5;
		}*/
	
	public void handleKeyboardInput() {
		super.handleKeyboardInput();
		if(Keyboard.getEventKeyState()) {
			int i = Keyboard.getEventKey();
			
			if(i == Keyboard.KEY_E) {
				if(this.openView != null) {
					this.openView = null;
					this.page = 0;
					this.initGui();
				} else if(this.currentResearch != this.research.nodes) {
					this.currentResearch = this.research.nodes;
				}
				//else this.player.closeScreen();
			} else if(i == Keyboard.KEY_R) {
				ResearchLoader.reloadResearch();
				this.initGui();
				
				if(book.stackTagCompound.getString("owner").equals("cheaty book"))
					this.research = SorceryAPI.research;
				else
					this.research = Research.readFromNBT(player);
			}
		}
	}

	public boolean isMouseInsideArea(int areaLeft, int areaTop, int width, int height) {
		if(this.mouseX >= areaLeft && this.mouseX <= areaLeft + width)
			if(this.mouseY >= areaTop && this.mouseY <= areaTop + height)
				return true;
		
		return false;
	}
	
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawResearch() {
		int xBind = 0;
		int yBind = 175;
		if(this.research == null || this.currentResearch == null)
			return;
		
    	for(int i = 0; i < this.currentResearch.size(); i++) {
    		if(this.shouldDisplayResearch(this.currentResearch.get(i))) {
	    		switch(this.currentResearch.get(i).tier) {
		    		default:
		    			xBind = 60;
		    			break;
		    		case 1:
		    			xBind = 30;
		    			break;
		    		case 2:
		    			xBind = 0;
		    			break;
	    		}
	            RenderHelper.enableGUIStandardItemLighting();
	    		RenderUtils.bindTexture(this.background);
	    		
	    		int xDraw = (this.currentResearch.get(i).xCoord * 20) - 15;
	    		int yDraw = (this.currentResearch.get(i).yCoord * 20) - 15;
	    		this.zLevel = -49.0F;
	    		if(!this.currentResearch.get(i).parents.isEmpty()) {
	    			for(int k = 0; k < this.currentResearch.get(i).parents.size(); k++) {
	    				if(this.currentResearch.get(i).parents.get(k) != null) {
	    					ResearchNode node = this.getNodeFromName(this.currentResearch.get(i).parents.get(k));
	    					float[] colour = {1.0F, 1.0F, 1.0F};
	    					
	    					if(this.currentResearch.get(i).unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book"))
	    						colour = new float[] {0.0F, 0.5F, 0.0F};
	    						
	    					if(!this.currentResearch.get(i).unlocked && !this.book.stackTagCompound.getString("owner").equals("cheaty book")) {
	    						if(node.unlocked)
	    							colour = new float[] {0.3F, 0.3F, 0.0F};
	    						else
	    							colour = new float[] {0.2F, 0.2F, 0.2F};
	    					}
	    					
	    					if(this.shouldDisplayResearch(node)) {
	    						GL11.glColor3f(colour[0], colour[1], colour[2]);
    							int xOff = node.xCoord - this.currentResearch.get(i).xCoord;
    							int xNeg = xOff >= 0 ? 1 : -1;
    							int xExtra = xNeg == -1 ? 0 : 20;
    							if(xOff != 0) {
		    						for(int j = 0; j < Math.abs(xOff); j++) {
		    							this.drawTexturedModalRect(this.offsetX + xDraw + (xNeg * (j * 20)) - 6 + xExtra, this.offsetY + yDraw + 14, 90, 175, 20, 2);
		    						}
    							}
	    						
	    						int yOff = node.yCoord - this.currentResearch.get(i).yCoord;
	    						int yNeg = yOff >= 0 ? 1 : -1;
	    						int yExtra = yNeg == -1 ? 0 : 18;
	    						if(yOff != 0) {
		    						for(int j = 0; j < Math.abs(yOff); j++) {
		    							this.drawTexturedModalRect(this.offsetX + xDraw + 14 + (xOff * 20), this.offsetY + yDraw + (yNeg * (j * 20)) - 4 + yExtra, 90, 175, 2, 20);
		    						}
	    						}
	    					}
	    				}
	    			}
	    		}
	    		
	    		if(!this.currentResearch.get(i).unlocked && !this.book.stackTagCompound.getString("owner").equals("cheaty book")) {
	    			GL11.glColor3f(0.2F, 0.2F, 0.2F);
	    			xBind = 60;
	    			RenderUtils.itemRenderer.renderWithColor = false;
	    		} else GL11.glColor3f(1.0F, 1.0F, 1.0F);
	    	
	    		yBind = 175;
	    		
	    		if(this.currentResearch.get(i).hasSubNodes)
	    			yBind = 226;
	    		
	    		this.zLevel = -48.0F;
	    		RenderUtils.itemRenderer.zLevel = -47.0F;
	    		RenderUtils.bindTexture(this.background);
	    		this.drawTexturedModalRect(this.offsetX + xDraw, this.offsetY + yDraw, xBind, yBind, 30, 30);
	    		
	    		if(this.currentResearch.get(i).item != null && this.currentResearch.get(i).item.getItem() != null) {
	    			GL11.glEnable(GL11.GL_DEPTH_TEST);
	    			//GL11.glColor3f(1.0F, 1.0F, 1.0F);
	    			RenderHelper.disableStandardItemLighting();
	    			RenderUtils.itemRenderer.renderItemIntoGUI(this.fontRendererObj, mc.renderEngine,
	    					this.currentResearch.get(i).item, this.offsetX + xDraw + 7, this.offsetY + yDraw + 7);
	    		} else if(this.currentResearch.get(i).icon != null) {
	    			RenderUtils.bindTexture(this.currentResearch.get(i).icon);
	    			this.drawTexturedModalRect(this.offsetX + xDraw + 7, this.offsetY + yDraw + 7, xBind, yBind, 16, 16);
	    		}
	    		
	    		RenderUtils.itemRenderer.renderWithColor = true;
    		}
    	}
    	
    	if(this.openView == null) {
	    	for(int i = 0; i < this.currentResearch.size(); i++) {
	    		if(this.shouldDisplayResearch(this.currentResearch.get(i)))
		    		if(this.isMouseInsideArea((int)(this.res.getScaledWidth() / 2) + this.offsetX + (this.currentResearch.get(i).xCoord * 20) - 15, 
		    				(int)(res.getScaledHeight() / 2) + this.offsetY + (this.currentResearch.get(i).yCoord * 20) - 15, 30, 30))
		    			this.drawToolTip(this.currentResearch.get(i), this.mouseX - (this.res.getScaledWidth() / 2), this.mouseY - (this.res.getScaledHeight() / 2));
	    	}
    	}
    	
    	GL11.glColor3f(1F, 1F, 1F);
    }
    
    protected void drawToolTip(ResearchNode research, int par2, int par3) {
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		List<String> tooltip = new ArrayList<String>();
		tooltip.add(research.title);
		if(research.unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book")) {
			tooltip.add(EnumChatFormatting.GRAY + research.description);
		}
		
		if(!research.unlocked && !this.book.stackTagCompound.getString("owner").equals("cheaty book")) {
			tooltip.add(EnumChatFormatting.GRAY + "-" + research.hint + "-");
			
			if(!research.parents.isEmpty()) {
				tooltip.add("");
				tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("sorcery.research.requires"));
				for(int i = 0; i < research.parents.size(); i++)
					if(this.shouldDisplayResearch(this.getNodeFromName(research.parents.get(i))))
						tooltip.add(EnumChatFormatting.GRAY + "-" + research.parents.get(i));
			}
			
		}
		
		if(research.hasSubNodes && (research.unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book"))) {
			if(this.currentResearch == this.research.nodes) {
				tooltip.add("");
				tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("sorcery.research.clickToExpand"));
			}
		}
		
		if(!tooltip.isEmpty()) {
			int var5 = 0;
			Iterator var6 = tooltip.iterator();
			
			while(var6.hasNext()) {
				String var7 = (String)var6.next();
				int var8 = this.fontRendererObj.getStringWidth(var7);
				
				if(var8 > var5) {
					var5 = var8;
				}
			}
			
			if(par2 + var5 + 18 >= this.width) {
				par2 -= var5 + 24;
			}
			
			int var15 = par2 + 12;
			int var16 = par3 - 12;
			int var9 = 8;
			
			if(tooltip.size() > 1) {
				var9 += 2 + (tooltip.size() - 1) * 10;
			}
			
			int var10 = -267386864;
			this.drawGradientRect(var15 - 3, var16 - 4, var15 + var5 + 3, var16 - 3, var10, var10);
			this.drawGradientRect(var15 - 3, var16 + var9 + 3, var15 + var5 + 3, var16 + var9 + 4, var10, var10);
			this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 + var9 + 3, var10, var10);
			this.drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var9 + 3, var10, var10);
			this.drawGradientRect(var15 + var5 + 3, var16 - 3, var15 + var5 + 4, var16 + var9 + 3, var10, var10);
			int var11 = 1347420415;
			int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
			this.drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var15 + var5 + 2, var16 - 3 + 1, var15 + var5 + 3, var16 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 - 3 + 1, var11, var11);
			this.drawGradientRect(var15 - 3, var16 + var9 + 2, var15 + var5 + 3, var16 + var9 + 3, var12, var12);
			
			for(int var13 = 0; var13 < tooltip.size(); ++var13) {
				String var14 = (String)tooltip.get(var13);
				
				this.fontRendererObj.drawStringWithShadow(var14, var15, var16, -1);
				
				if(var13 == 0) {
					var16 += 2;
				}
				
				var16 += 10;
			}
		}
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
    
    public boolean shouldDisplayResearch(ResearchNode research) {
    	if(this.book == null)
    		return false;
    	
    	if(this.book.getTagCompound().getString("owner").equals("cheaty book"))
    		return true;
    	
    	if(research == null)
    		return false;

		if(!research.unlocked && research.hidden)
			return false;
    	
    	if(research.unlocked || this.book.stackTagCompound.getString("owner").equals("cheaty book"))
    		return true;
    	
    	if(!this.currentResearch.contains(research))
    		return false;
    	
    	if(research.parents.isEmpty())
    		return true;
    	
    	for(int i = 0; i < research.parents.size(); i++) {
    		for(int k = 0; k < this.currentResearch.size(); k++) {
    			if(this.currentResearch.get(k).title.equals(research.parents.get(i)) && this.currentResearch.get(k).unlocked) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public void drawResearchPage(ResearchNode research) {
    	RenderUtils.bindTexture(this.background);
    	this.drawTexturedModalRect(-90, -85, 0, 0, 181, 175);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	
    	Page page = this.currentPages.get(this.page);
    	
    	String title = page.heading.equals("") ? research.title : page.heading;
    	this.drawCenteredString(this.fontRendererObj, title, 0, -70, 4210752);
    	this.drawCenteredString(this.fontRendererObj, research.title, 0, 72, 666666);
    	
    	if(page.dataType.equals("text")) {
    		String text = (String)page.data;
    		this.fontRendererObj.drawSplitString(text, -70, -55, 148,  4210752);
    	} else if(page.dataType.equals("image")) {
    		ResourceLocation img = (ResourceLocation)page.data;
    		RenderUtils.bindTexture(img);
    		GL11.glScalef(0.5F, 0.5F, 0.5F);
    		this.drawTexturedModalRect(-128, -128, 0, 0, 256, 256);
    		RenderUtils.bindTexture(this.background);
    	} else if(page.dataType.equals("recipe")) {
    		
    	} else if(page.dataType.equals("structure")) {
    		
    	} else if(page.dataType.equals("entity")) {
    		
    	}
    }
    
    @Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton instanceof GuiButtonResearch && this.debounce == 0) {
			if(par1GuiButton.id == 0)
				this.page++;
			if(par1GuiButton.id == 1)
				this.page--;
			this.initGui();
			
			this.debounce = 2;
		}
	}

	public ResearchNode getNodeFromName(String name) {
    	for(int i = 0; i < this.currentResearch.size(); i++) {
    		if(this.currentResearch.get(i).title.equals(name))
    			return this.currentResearch.get(i);
    	}
    	
    	return null;
    }
    
    public void drawCenteredString(FontRenderer renderer, String string, int x, int y, int colour) {
    	renderer.drawString(string, x - renderer.getStringWidth(string) / 2, y, colour);
    }
}
