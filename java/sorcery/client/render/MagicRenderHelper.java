package sorcery.client.render;

import java.util.HashMap;

import sorcery.api.spellcasting.ModelWandComponent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagicRenderHelper {
	
	@SideOnly(Side.CLIENT)
	public static HashMap<String, ModelWandComponent> models = new HashMap<String, ModelWandComponent>();
	
}
