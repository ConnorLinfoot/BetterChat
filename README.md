BetterChat
=====================

[![Build Status](http://ci.connorlinfoot.com:8080/buildStatus/icon?job=BetterChat)](http://ci.connorlinfoot.com:8080/job/BetterChat/) 

BetterChat allows multiple custom chat channels as well as a staff chat.

### Latest Release
You can download the latest "stable" release of BetterChat on Spigot: http://www.spigotmc.org/resources/betterchat.4372/

### Requirements
* Bukkit/Spigot/BungeeCord 1.7 - 1.10
* Java 7 or newer

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
* ~~On-the-fly Channels~~
* ~~Custom commands per channel - /NAME \<message\>~~
* ~~BungeeCord support.~~
* Anti-Swear Filter
* Anti-Spam system
* Player Muting
* Player banning from channel(s)
* Chat mention notifications
* More per channel options
* Channel configuration GUI
* BountifulAPI support. (Maybe)
* BarAPI support. (Maybe)
* Older version support, such as 1.6 and 1.5. (Maybe)
* Many more planned features will be added.
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

### Changelog

#### Version 2.0 (In progress)
* Added support for BungeeCord.
* Added Player Mentions
* Added a swear filter - words and settings can be found in the config.yml
* Added some spam filter options


#### Version 1.1
* Custom Staff Chat Prefix.
* Chat color codes in staff chat and normal chat.
* Added player name to staff chat.
* Permission to access all channels - betterchat.all.
* Config option to set default channel.
* Custom chat prefix per channel.
* Custom channel commands - /\<channel\> \<message\>.
* Remember players channels option - If the player re-logs or the server restarts/reloads the player will be on the same channel.
* On-the-fly channels - Easily create and manage channels without even opening the config.
* Added a "fallback" command if the plugin fails to start.
* Added the command /whatchannel to find what channel a specific player is in.
* Added the /betterchat command as well as /betterchat reload

#### Version 1.0.1
* Fixed players been in "null" channel on reload.

#### Version 1.0
* Initial Release.
* Staff Chat.
* Custom Channels and /channel command.
