BetterChat
=====================

[![Build Status](http://ci.connorlinfoot.com:8080/buildStatus/icon?job=BetterChat)](http://ci.connorlinfoot.com:8080/job/BetterChat/)

BetterChat allows multiple custom chat channels as well as a staff chat.

This plugin is for Bukkit/Spigot. It will NOT work with BungeeCord.


### Latest Release
You can download the latest "stable" release of BetterChat on Spigot: http://www.spigotmc.org/resources/betterchat.4372/


### Requirements
* Bukkit/Spigot 1.7.5 - 1.8 (Not BungeeCord)
* Optional: BountifulAPI - http://www.spigotmc.org/resources/bountifulapi.1394/
* Optional: BarAPI - http://dev.bukkit.org/bukkit-plugins/bar-api/


### Features
* Staff Chat - A way for staff to talk between themselves privately.
* Custom Chat Channels - Allow players to switch between channels where only players in that channel can see messages.
* On-the-fly Channels - Use a command to create a channel and instantly start using it without any reboot or performance impact.


### TODO
* ~~Custom Chat Channels~~
* ~~Staff Chat~~
* ~~Permission to access ALL channels - Good for staff.~~
* ~~Add color code support~~
* ~~Add a option for default channel~~
* ~~Command to find what channel a specific player is in.~~
* On-the-fly Channels
* Custom commands per channel - /NAME \<message\>
* More per channel options
* Channel configuration GUI
* Many more planned features will be added.
* BountifulAPI support.
* BarAPI support.
* Older version support, such as 1.6 and 1.5. (Maybe)
* BungeeCord edition of this plugin.
* Drink beer.


### Commands
* /channel [channel name] - Switch what channel you are currently talking in.
* /channel create \<name\> - Create a new channel. (Admin Only)
* /channel delete \<name\> - Delete a channel. (Admin Only)
* /channel info \<name\> - View information on a channel. (Admin Only)
* /staffchat \<message\> - Send a message to all staff. Aliases are /sc.
* /whatchannel <player> - Find what channel a specific player is in.
* /betterchat reload - Reload the configuration.


### Permissions
* betterchat.channel.channelname - Replacing channelname with the name of the channel will give you the required permission to join a channel if it is enabled in the config to use permissions.
* betterchat.staff - Access to staff chat, including staff chat commands. Defaults to OP.
* betterchat.all - Access to join ALL channels, defaults to OP.

If you have any ideas or suggestions please post them as an issue!


### Changelogs

#### Version 1.1 (In Progress)
* Custom Staff Chat Prefix.
* Chat color codes in staff chat and normal chat.
* Added player name to staff chat.
* Permission to access all channels - betterchat.all.
* Config option to set default channel.
* Custom chat prefix per channel.
* Custom channel commands - /\<channel\> \<message\>.
* Remember players channels option - If the player re-logs or the server restarts/reloads the player will be on the same channel.
* Fixed players been in "null" channel on reload.
* On-the-fly channels - Easily create and manage channels without even opening the config.
* Added a "fallback" command if the plugin fails to start.
* Added the command /whatchannel to find what channel a specific player is in.
* Added the /betterchat command as well as /betterchat reload

#### Version 1.0
* Initial Release.
* Staff Chat.
* Custom Channels and /channel command.