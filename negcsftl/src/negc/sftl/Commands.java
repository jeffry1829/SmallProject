package negc.sftl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

public class Commands implements CommandExecutor
{
	Main instance;
	public Commands(Main instance)
	{
		this.instance = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args)
	{	
		if(lable.equals("negcsftl"))
		{
		if(args.length==0)
		{
			sender.sendMessage("Author:petjelinux");
			sender.sendMessage("/negcsftl reload");
		}
		if(args[0].equals("reload"))
		{
			try {
			instance.ItemsConfig.load(instance.Itemsyml);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {	} 
		catch (InvalidConfigurationException e) {}
		try {
			instance.ItemsConfig.save(instance.Itemsyml);
		} catch (IOException e) {}
	}
		}
		return false;
	}
}
