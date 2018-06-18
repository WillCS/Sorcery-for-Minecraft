package sorcery.lib;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import sorcery.core.Sorcery;

public class Lang {
	private static String loadedLanguage = Sorcery.proxy.getLanguage();
	private static Properties defaultLang = new Properties();
	private static Properties lang = new Properties();
	
	public static void loadLanguage(String language) {
		InputStream stream = null;
		Properties temp = new Properties();
		
		try {
			stream = Lang.class.getResourceAsStream("/assets/sorcery/lang/" + language + ".lang");
			temp.load(stream);
			lang.putAll(temp);
			stream.close();
			
			stream = Lang.class.getResourceAsStream("/assets/sorcery/lang/en_US.lang");
			if(temp != null)
				temp.clear();
			temp.load(stream);
			defaultLang.putAll(temp);
			stream.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stream != null)
					stream.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!sorcery.lib.Properties.spellNames.containsKey(loadedLanguage))
			sorcery.lib.Properties.spellNames.put(loadedLanguage, new HashMap<String, String>());
		
		Iterator iterator = lang.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry)iterator.next();
			String add = (String)pairs.getKey();
			String name = (String)pairs.getValue();
			
			if(add != null && add.startsWith("sorcery.spell.component.") && add.endsWith((".name"))) {
				add = add.replaceAll("sorcery.spell.component.", "").replaceAll(".name", "").replace('.', '_');
				System.out.println(add);
				
				sorcery.lib.Properties.spellNames.get(loadedLanguage).put(name, add);
			}
		}
	}
	
	public static String getName(String local) {
		if(Sorcery.proxy.getLanguage() == null)
			return local;
		
		if(!Sorcery.proxy.getLanguage().equals(loadedLanguage)) {
			defaultLang.clear();
			lang.clear();
			loadLanguage(Sorcery.proxy.getLanguage());
			loadedLanguage = Sorcery.proxy.getLanguage();
		}
		
		return lang.getProperty(local, defaultLang.getProperty(local, local));
	}
}
