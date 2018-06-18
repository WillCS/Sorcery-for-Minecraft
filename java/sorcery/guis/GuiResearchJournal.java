package sorcery.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sorcery.api.research.Research;
import sorcery.containers.ContainerResearchJournal;
import sorcery.lib.utils.Utils;

public class GuiResearchJournal extends GuiContainer {
	public ItemStack book;
	public EntityPlayer player;
	
	public int page;
	public Research currentView;
	public Research research;
	
	public int debounce = 0;
	
	public static ResourceLocation background = Utils.getResource("textures/guis/researchJournal.png");
	
	public GuiResearchJournal(EntityPlayer player, World world, int x, int y, int z) {
		super(new ContainerResearchJournal(player.inventory, world, x, y, z));
		this.xSize = 256;
		this.ySize = 166;
		this.player = player;
		this.book = player.getCurrentEquippedItem();
		this.research = Research.readFromNBT(player);
		this.currentView = this.research;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
	}
	
	/*protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if(this.debounce > 0)
			this.debounce--;
		
		if(this.currentView.getClass() == Research.class)
			this.fontRendererObj.drawString("Contents", 70 - (fontRenderer.getStringWidth("Contents") / 2), 15, 4210752);
		else if(this.currentView.components.isEmpty() && this.currentView.categories.isEmpty() && !this.currentView.pages.isEmpty()) {
			if(this.currentView.pages.size() >= this.page - 1) {
				if(this.page == 0) {
					this.fontRendererObj.drawString(this.currentView.title, 70 - (fontRenderer.getStringWidth(this.currentView.title) / 2), 15, 4210752);
					this.drawPage(10, 0, this.currentView.pages.get(page));
				} else {
					this.fontRendererObj.drawString(this.currentView.pages.get(this.page).heading, 70 - (fontRenderer.getStringWidth(this.currentView.pages.get(this.page).heading) / 2), 15, 4210752);
					this.drawPage(10, 0, this.currentView.pages.get(page));
				}
			}
			
			if(this.currentView.pages.size() >= (this.page + 1)) {
				this.fontRendererObj.drawString(this.currentView.pages.get(this.page + 1).heading, 190 - (fontRenderer.getStringWidth(this.currentView.pages.get(this.page + 1).heading) / 2), 15, 4210752);
				this.drawPage(130, 0, this.currentView.pages.get(page + 1));
			}
		} else {
			if(this.page == 0) {
				this.fontRendererObj.drawString(this.currentView.title, 70 - (fontRenderer.getStringWidth(this.currentView.title) / 2), 15, 4210752);
				if(!this.currentView.pages.isEmpty())
					this.drawPage(10, 0, this.currentView.pages.get(page));
				if(!this.currentView.pages.isEmpty()) {
					this.fontRendererObj.drawString(this.currentView.title, 190 - (fontRenderer.getStringWidth(this.currentView.title) / 2), 15, 4210752);
					this.drawPage(130, 0, this.currentView.pages.get(page));
				}
			} else {
				if(this.currentView.pages.size() >= (this.page - 1)){
					this.fontRendererObj.drawString(this.currentView.pages.get(this.page - 1).heading, 70 - (fontRenderer.getStringWidth(this.currentView.pages.get(this.page - 1).heading) / 2), 15, 4210752);
					this.drawPage(130, 0, this.currentView.pages.get(page - 1));
				}
				
				if(this.currentView.pages.size() >= this.page) {
					this.fontRendererObj.drawString(this.currentView.pages.get(this.page).heading, 190 - (fontRenderer.getStringWidth(this.currentView.pages.get(this.page).heading) / 2), 15, 4210752);
				}
			}
		}
	}
	
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
	}
	
	public void initGui() {
		if(this.player.worldObj.isRemote) {
			this.mc.sndManager.playSoundFX(Properties.ASSET_PREFIX + "book.page", 1.0F, 1.0F);
		}
		
		super.initGui();
		this.buttonList.clear();
		
		int i = 0;
		
		for(int j = 0; j < this.currentView.components.size(); j++) {
			this.buttonList.add(new GuiButtonResearch(i, this.currentView.components.get(j), this.guiLeft + 20, this.guiTop + 30 + (20*i)));
			i++;
		}
		
		for(int j = 0; j < this.currentView.categories.size(); j++) {
			this.buttonList.add(new GuiButtonResearch(i, this.currentView.categories.get(j), this.guiLeft + 20, this.guiTop + 30 + (20*i)));
			i++;
		}
	}
	
	@Override
	public void handleKeyboardInput() {
		super.handleKeyboardInput();
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {		
		if(this.currentView.parent != null) {
			if(par1 > this.guiLeft + 8 && par1 < this.guiLeft + 8 + 24 && par2 > this.guiTop + 8 && par2 < this.guiTop + 8 + 23) {
				this.currentView = this.currentView.parent;
				this.initGui();
				this.page = 0;
			}
		}
		
		if(!this.currentView.pages.isEmpty()) {
			if(this.page != 0) {
				if(par1 > this.guiLeft + 8 && par1 < this.guiLeft + 8 + 24 && par2 > this.guiTop + 133 && par2 < this.guiTop + 133 + 23)
					this.page -= 2;
					this.initGui();
			}
				
			if(this.currentView.pages.size() - 1 > this.page + 1) {
				if(par1 > this.guiLeft + 216 + 8 && par1 < this.guiLeft + 216 + 8 + 24 && par2 > this.guiTop + 133 && par2 < this.guiTop + 133 + 23)
					this.page +=2;
					this.initGui();
			}
		}
		
		super.mouseClicked(par1, par2, par3);
	}

	protected void actionPerformed(GuiButton button) {
		if(button == null || this.debounce != 0)
			return;
		
		if(button instanceof GuiButtonResearch) {
			this.currentView = ((GuiButtonResearch)button).research;
			this.initGui();
		}
		
		if(this.debounce == 0)
			this.debounce = 15;
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		RenderUtils.bindTexture(background);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.drawTexturedModalRect(this.guiLeft + 18, this.guiTop + 22, 0, 166, 102, 5);
		
		if(this.currentView.components.isEmpty() && this.currentView.categories.isEmpty() && !this.currentView.pages.isEmpty()) {
			if(this.currentView.pages.size() >= this.page)
				this.drawTexturedModalRect(this.guiLeft + 18, this.guiTop + 22, 0, 166, 102, 5);
			
			if(this.currentView.pages.size() >= (this.page + 1))
				this.drawTexturedModalRect(this.guiLeft + 138, this.guiTop + 22, 0, 166, 102, 5);
		} else {
			if(this.page == 0) {
				this.drawTexturedModalRect(this.guiLeft + 18, this.guiTop + 22, 0, 166, 102, 5);
				if(!this.currentView.pages.isEmpty())
					this.drawTexturedModalRect(this.guiLeft + 138, this.guiTop + 22, 0, 166, 102, 5);
			} else {
				if(this.currentView.pages.size() >= (this.page - 1))
					this.drawTexturedModalRect(this.guiLeft + 18, this.guiTop + 22, 0, 166, 102, 5);
				
				if(this.currentView.pages.size() >= this.page)
					this.drawTexturedModalRect(this.guiLeft + 138, this.guiTop + 22, 0, 166, 102, 5);
			}
		}
		
		if(this.currentView.parent != null) {
			if(par2 > this.guiLeft + 8 && par2 < this.guiLeft + 8 + 24 && par3 > this.guiTop + 8 && par3 < this.guiTop + 8 + 23)
				this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 8, 0, 217, 24, 23);
			else
				this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 8, 24, 217, 24, 23);
		}
		
		if(!this.currentView.pages.isEmpty()) {
			if(this.page != 0) {
				if(par2 > this.guiLeft + 8 && par2 < this.guiLeft + 8 + 24 && par3 > this.guiTop + 134 && par3 < this.guiTop + 134 + 23)
					this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 133, 0, 171, 24, 23);
				else
					this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 133, 24, 171, 24, 23);
			}
				
			if(this.currentView.pages.size() - 1 > this.page + 1) {
				if(par2 > this.guiLeft + 216 + 8 && par2 < this.guiLeft + 216 + 8 + 24 && par3 > this.guiTop + 134 && par3 < this.guiTop + 134 + 23)
					this.drawTexturedModalRect(this.guiLeft + 216 + 8, this.guiTop + 133, 0, 194, 24, 23);
				else
					this.drawTexturedModalRect(this.guiLeft + 216 + 8, this.guiTop + 133, 24, 194, 24, 23);
			}
		}
	}
	
	@Override
	public void onGuiClosed() {
		if(this.player.worldObj.isRemote) {
			this.mc.sndManager.playSoundFX(Properties.ASSET_PREFIX + "book.close", 1.0F, 1.0F);
		}
	}
	
	protected void drawToolTip(Spell spell, int par2, int par3, Minecraft minecraft) {
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		int var5 = 0;
		
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add(spell.name);
		if(spell.description != null)
			tooltip.add(spell.description);
		Iterator var6 = tooltip.iterator();
		
		while(var6.hasNext()) {
			String var7 = (String)var6.next();
			int var8 = minecraft.fontRenderer.getStringWidth(var7);
			
			if(var8 > var5) {
				var5 = var8;
			}
		}
		
		int var15 = par2 + 12;
		int var16 = par3 - 12;
		int var9 = 8;
		
		if(tooltip.size() > 1) {
			var9 += 2 + (tooltip.size() - 1) * 10;
		}
		
		this.zLevel = 300.0F;
		itemRenderer.zLevel = 300.0F;
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
			minecraft.fontRenderer.drawStringWithShadow(var14, var15, var16, -1);
			
			if(var13 == 0) {
				var16 += 2;
			}
			
			var16 += 10;
		}
		
		this.zLevel = 0.0F;
		itemRenderer.zLevel = 0.0F;
	}
	
	public void drawPage(int xOffset, int yOffset, Page page) {
		if(page.dataType.equals("text")) {
			this.fontRendererObj.drawSplitString((String)page.data, xOffset + 10, yOffset + 30, 100, 4210752);
		}
		
		RenderUtils.bindTexture(background);
	}*/
}
