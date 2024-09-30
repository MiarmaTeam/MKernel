package dev.gallardo.miarmacore.common;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.gallardo.miarmacore.MiarmaCore;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class ConfigWrapper {
	private YamlDocument config;
	
	public ConfigWrapper() {
	}
	
	public YamlDocument getConfig() {
		return config;
	}
	
	public void onEnable() {
		try {
            this.config = YamlDocument.create(new File(MiarmaCore.PLUGIN.getDataFolder(), "config.yml"),
                    MiarmaCore.PLUGIN.getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), 
					DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version"))
					.build());
        } catch (IOException e) {
            LOGGER.severe("Error loading config.yml file!");
        }
	}
	
	public void reload() {
		try {
			config.reload();
		} catch (IOException e) {
			LOGGER.severe("Error reloading config.yml file!");
		}
	}
	
	public void save() {
		try {
			config.save();
		} catch (IOException e) {
			LOGGER.severe("Error saving config.yml file!");
		}
	}
	
	public String getString(String route) {
		return config.getString(route);
	}
	
	public Boolean getBoolean(String route) {
		return config.getBoolean(route);
	}
	
	public Integer getInt(String route) {
		return config.getInt(route);
	}
	
	public List<String> getList(String route){
		return config.getStringList(route);
	}
}
